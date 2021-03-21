package com.springboot.kafkatest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaConfiguration {
    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        log.info("create kafka errorHandler");
        return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }
}
