package com.springboot.kafkatest;

import com.springboot.kafkatest.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class KafkatestApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkatestApplication.class, args);
	}

	@KafkaListener(topics = "testKafkaSend1", id = "testKafkaSend1")
	public void listen1(Test1 test1) {
		log.info("Received: {} for thread id: {}", test1, Thread.currentThread().getId());
	}

	@KafkaListener(topics = "testKafkaSend2")
	public void listen2(Test2 test2) {
		log.info("Received: " + test2);
	}

	@KafkaListener(topics = "testKafkaSend3", id = "testKafkaSend3", concurrency = "${listen.concurrency:3}")
	public void listen3(Test3 test3) {
		log.info("Received: {} for thread id: {}", test3, Thread.currentThread().getId());
	}

	@KafkaListener(topics = "testKafkaSend4", containerFactory = "test4KafkaListenerContainerFactory")
	public void listen4(Test4 test4) {
		log.info("Received: {} for thread id: {}", test4, Thread.currentThread().getId());
	}

	@KafkaListener(topics = "testKafkaSend5", containerFactory = "test5KafkaListenerContainerFactory")
	public void listen5(Test5 test5) {
		log.info("Received: {} for thread id: {}", test5, Thread.currentThread().getId());
	}
}
