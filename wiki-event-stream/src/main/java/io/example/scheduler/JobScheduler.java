package io.example.scheduler;

import io.example.clients.RecentChangeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class JobScheduler {
    private static Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    private final RecentChangeProcessor recentChangeProcessor;

    public JobScheduler(RecentChangeProcessor recentChangeProcessor) {
        this.recentChangeProcessor = recentChangeProcessor;
    }

    @Scheduled(initialDelay = 1000 * 5, fixedDelay=1000)
    public void start() {
        if(!recentChangeProcessor.isRunning()) {
            logger.info("Starting the recent change wiki event processor...");
            recentChangeProcessor.start();
        }
    }
}
