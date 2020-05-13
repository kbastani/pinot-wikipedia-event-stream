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

/**
 * This processor receives Wikimedia change events that describe recent changes as they are happening live on Wikipedia.
 *
 * @author Kenny Bastani
 */
@Component
public class RecentChangeProcessor {
    private static Logger logger = LoggerFactory.getLogger(RecentChangeProcessor.class);
    private static final String STREAM_HOST = "https://stream.wikimedia.org";
    private static final String STREAM_URI = "/v2/stream/recentchange";
    private FluxProcessor<RecentChange, RecentChange> categoryProcessor;
    private FluxSink<RecentChange> categorySink;
    private final Source messageBroker;
    private boolean running = false;
    private final Gson gson = new Gson();
    private final Wiki wiki = new Wiki.Builder().build();

    public RecentChangeProcessor(Source messageBroker) {
        this.messageBroker = messageBroker;

        // Initialize the join processor which will decorate recent change events with their joined category
        this.categoryProcessor = DirectProcessor.<RecentChange>create().serialize();
        this.categorySink = categoryProcessor.sink();
    }

    /**
     * Starts the recent change processor and begins to process and emit events that decorate recent changes
     * happening on Wikipedia with the page's joined categories. This allows us to create multiple change events
     * that are focused on category changes. The goal here is to understand how categories on Wikipedia are changing
     * in real-time using Apache Pinot and Kafka.
     */
    public void start() {
        if (!running) {
            running = true;

            // Fetch the reactive change stream that is ready for subscription
            Flux<RecentChange> changeStream = getRecentChangeStream();

            // Before initiating the subscription, specify how errors should be handled and retry with backoff
            changeStream.onErrorResume(throwable -> getRecentChangeStream())
                    .retryBackoff(Integer.MAX_VALUE, Duration.ofMillis(300))
                    .subscribe();

            // Subscriptions are non-blocking, so here we start a second subscription that is listening for joins
            // that are being emitted from the first subscription, before sinking them into Kafka
            categoryProcessor.subscribe(this::processEvent);
        } else {
            throw new RuntimeException("The wiki stream replicator is already running...");
        }
    }

    /**
     * Fetches a reactive stream of recent changes that are happening live on Wikipedia. Logs errors if there is an
     * issue connecting to the Wikimedia change stream API. Filters out all changes that are not applicable to
     * being joined with categories, and also filters to only edits and english version of Wikipedia. Finally, it
     * joins multiple categories for each change and emits new events to be processed by a different reactive stream.
     *
     * @return a Flux of {@link RecentChange}
     */
    private Flux<RecentChange> getRecentChangeStream() {
        // Get the reactive SSE stream for recent Wikipedia changes
        Flux<ServerSentEvent<String>> eventStream = getWikiStreamClient();

        return eventStream.map(event -> gson.fromJson(event.data(), RecentChange.class))
                .doOnError(throwable -> logger.error("Error receiving SSE", throwable))
                .filter(this::applyFilter)
                .doOnNext(this::joinCategories);
    }

    /**
     * Filters out Wikipedia changes that aren't english and excludes any changes that are not edits to articles.
     *
     * @param recentChange is the recent change event from Wikipedia
     * @return a boolean value indicating whether or not to filter out the recent change
     */
    private boolean applyFilter(RecentChange recentChange) {
        return recentChange.getWiki().equals("enwiki") &&
                recentChange.getType().equals("edit");
    }

    /**
     * This method decorates a {@link RecentChange} event from Wikipedia with categories that are fetched from
     * Wikipedia's API. The article's categories are fetched using the edited page's title. Since there are many
     * categories for a single change, the original change event is cloned and decorated with a category. Finally,
     * the new decorated change event is emitted to a new subscriber that will persist it to Kafka.
     *
     * @param recentChange is the {@link RecentChange} that should be joined with its categories
     */
    private void joinCategories(RecentChange recentChange) {
        wiki.getCategoriesOnPage(recentChange.getTitle()).stream().map(category -> {
            RecentChange clonedChange = gson.fromJson(gson.toJson(recentChange), RecentChange.class);
            clonedChange.setCategory(category);
            return clonedChange;
        }).parallel().forEach(categorySink::next);
    }

    /**
     * Processes a {@link RecentChange} event that has been joined together with a category and persists it to a
     * Kafka topic. This Kafka topic will be subscribed to by Apache Pinot, which will ingest the rows into a
     * real-time table that can be queried using SQL.
     *
     * @param recentChange is the {@link RecentChange} from Wikipedia joined with a category.
     */
    private void processEvent(RecentChange recentChange) {
        try {
            logger.info("Processing wiki change event: " + recentChange.toString());
            messageBroker.output().send(MessageBuilder.withPayload(recentChange).build());
        } catch (Exception ex) {
            logger.error("Error processing wiki change event", ex);
            throw new RuntimeException(ex);
        }

        if (!running)
            throw new RuntimeException("The wiki stream replicator was stopped");
    }

    /**
     * Fetches a reactive stream that processes server-sent events from the Wikimedia event platform.
     *
     * @return a Flux of {@link ServerSentEvent<String>} that will be converted to {@link RecentChange}
     */
    private Flux<ServerSentEvent<String>> getWikiStreamClient() {
        WebClient client = WebClient.create(STREAM_HOST);

        // This is a work around for type erasure in the JDK and explicitly types the generic for runtime
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<>() {
        };

        // Fetches a reactive stream that processes server-sent events from the Wikimedia event platform
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
