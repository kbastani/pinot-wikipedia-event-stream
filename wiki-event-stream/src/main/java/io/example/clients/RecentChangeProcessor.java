package io.example.clients;

import com.google.gson.Gson;
import io.example.schema.recentchange.RecentChange;
import org.fastily.jwiki.core.Wiki;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.time.Duration;

@Component
public class RecentChangeProcessor {
    private static final String STREAM_HOST = "https://stream.wikimedia.org";
    private static final String STREAM_URI = "/v2/stream/recentchange";
    private final Gson gson = new Gson();
    private final Source messageBroker;
    private static Logger logger = LoggerFactory.getLogger(RecentChangeProcessor.class);
    private boolean running;
    private final Wiki wiki = new Wiki.Builder().build();

    public RecentChangeProcessor(Source messageBroker) {
        this.messageBroker = messageBroker;
    }

    public void start() {
        if (!running) {
            running = true;
            FluxProcessor<RecentChange, RecentChange> processor;
            FluxSink<RecentChange> sink;
            processor = DirectProcessor.<RecentChange>create().serialize();
            sink = processor.sink();
            Flux<RecentChange> changeStream = getRecentChangeStream(sink);

            changeStream.onErrorResume(throwable -> getRecentChangeStream(sink))
                    .retryBackoff(Integer.MAX_VALUE, Duration.ofMillis(300))
                    .subscribe();

            processor.subscribe(this::processEvent);
        } else {
            throw new RuntimeException("The wiki stream replicator is already running...");
        }
    }

    private Flux<RecentChange> getRecentChangeStream(FluxSink<RecentChange> changes) {
        Flux<ServerSentEvent<String>> eventStream = getWikiStreamClient();
        return eventStream.map(event -> gson.fromJson(event.data(), RecentChange.class))
                .doOnError(throwable -> logger.error("Error receiving SSE", throwable))
                .filter(r -> r.getWiki().equals("enwiki") && r.getType().equals("edit"))
                .doOnNext(recentChange -> {
                    wiki.getCategoriesOnPage(recentChange.getTitle())
                            .stream()
                            .map(category -> {
                                System.out.println(category);
                                RecentChange clonedChange = gson.fromJson(gson.toJson(recentChange), RecentChange.class);
                                clonedChange.setCategory(category);
                                return clonedChange;
                            }).forEach(changes::next);
                });
    }

    private void processEvent(RecentChange recentChange) {
        try {
            logger.info("Processing wiki change event: " + recentChange.toString());
            messageBroker.output().send(MessageBuilder.withPayload(recentChange).build());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        logger.trace(recentChange.toString());

        if (!running)
            throw new RuntimeException("The wiki stream replicator was stopped");
    }

    private Flux<ServerSentEvent<String>> getWikiStreamClient() {
        WebClient client = WebClient.create(STREAM_HOST);
        ParameterizedTypeReference<ServerSentEvent<String>> type =
                new ParameterizedTypeReference<>() {
                };

        return client.get()
                .uri(STREAM_URI)
                .retrieve()
                .bodyToFlux(type);
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

}
