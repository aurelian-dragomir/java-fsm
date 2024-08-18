package com.dragomir.fsm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {
    @Value("${service1-topic}")
    private String service1Topic;

    @Value("${service2-topic}")
    private String service2Topic;

    @Value("${parallelism}")
    private int parallelism;

    @Bean
    public KafkaAdmin.NewTopics createTopicsBean() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(service1Topic)
                        .partitions(parallelism)
                        .build(),
                TopicBuilder.name(service2Topic)
                        .partitions(parallelism)
                        .build());
    }
}
