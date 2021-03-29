package com.springboot.kafkatest.controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.kafkatest.dto.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    //test sending an object
    @GetMapping(path = "/send1")
    public ObjectNode send1() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        for(int i=0; i<1000; i++) {
            Test1 test1 = new Test1("abc", i);
            this.template.send("testKafkaSend1", test1);
        }

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

    //test sending an object with a list
    @GetMapping(path = "/send2")
    public ObjectNode send2() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        Set<String> field2 = new HashSet<>();
        field2.add("data1");
        field2.add("data2");
        field2.add("data999");
        Test2 test2 = new Test2("test2", field2);
        this.template.send("testKafkaSend2", test2);

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

    //test consumer with concurrency=3 (multi-thread)
    @GetMapping(path = "/send3")
    public ObjectNode send3() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        //key must be passed
        for(int i=0; i<1000; i++) {
            Test3 test3 = new Test3("abc", i);
            this.template.send("testKafkaSend3", Integer.toString(i), test3);
        }

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

    //test consumer with test4KafkaListenerContainerFactory
    @GetMapping(path = "/send4")
    public ObjectNode send4() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        //key must be passed
        for(int i=0; i<100; i++) {
            Test4 test4 = new Test4("abc", i);
            this.template.send("testKafkaSend4", i, test4);
        }

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

    @GetMapping(path = "/send5")
    public ObjectNode send5() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<Integer, Object> test5ProducerFactory = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Integer, Object> test5KafkaTemplate = new KafkaTemplate<>(test5ProducerFactory);

        for(int i = 1; i <= 100; i++) {
            Test5 test5 = new Test5("abc", i);
            test5KafkaTemplate.send("testKafkaSend5", i, test5);
        }

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }
}
