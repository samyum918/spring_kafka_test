package com.springboot.kafkatest;

import com.springboot.kafkatest.dto.Test1;
import com.springboot.kafkatest.dto.Test2;
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

	@KafkaListener(topics = "testKafkaSend1")
	public void listen1(Test1 test1) {
		log.info("Received: " + test1);
	}

	@KafkaListener(topics = "testKafkaSend2")
	public void listen2(Test2 test2) {
		log.info("Received: " + test2);
	}
}
