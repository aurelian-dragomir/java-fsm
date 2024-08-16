package com.dragomir.fsm.example.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RetryScheduler {

    @Scheduled(fixedRate = 5000)
    public void retryPipeline() {
        System.out.println("hello");
    }

}
