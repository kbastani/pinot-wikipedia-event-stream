package io.example.clients;

import com.google.gson.Gson;
import io.example.schema.recentchange.RecentChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Optional;

@Component
public class RecentChangeProcessor {
    private static final String STREAM_HOST = "https://stream.wikimedia.org";
    private static final String STREAM_URI = "/v2/stream/recentchange";
    private final Gson gson = new Gson();
    private final Source messageBroker;
    private static Logger logger = LoggerFactory.getLogger(RecentChangeProcessor.class);
    private boolean running;

    public RecentChangeProcessor(Source messageBroker) {
        this.messageBroker = messageBroker;
    }

    public void start() {
        if (!running) {
            running = true;
            Flux<RecentChange> changeStream = getRecentChangeStream();

            changeStream.onErrorResume(throwable -> getRecentChangeStream())
                    .retryBackoff(Integer.MAX_VALUE, Duration.ofMillis(300))
                    .subscribe();
        } else {
            throw new RuntimeException("The wiki stream replicator is already running...");
        }
    }

    private Flux<RecentChange> getRecentChangeStream() {
        Flux<ServerSentEvent<String>> eventStream = getWikiStreamClient();
        return eventStream.map(event -> gson.fromJson(event.data(), RecentChange.class))
                .filter(recentChange -> !Optional.of(recentChange.getTitle()).orElse("").contains(":"))
                .doOnNext(this::processEvent)
                .doOnError(throwable -> logger.error("Error receiving SSE", throwable));
    }

    private void processEvent(RecentChange recentChange) {
        try {
            logger.info("Processing wiki change event: " + recentChange.getTitle());
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
