package io.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class WikiEventStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiEventStreamApplication.class, args);
    }

    /**
     * Configures a {@link Source} channel binding for emitting wiki stream events to Kafka.
     */
    @Configuration
    @EnableBinding(Source.class)
    static class StreamConfig {
    }
}
