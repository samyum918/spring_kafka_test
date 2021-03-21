package com.springboot.kafkatest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.kafkatest.dto.Test1;
import com.springboot.kafkatest.dto.Test2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping(path = "/send1")
    public ObjectNode send1() {
        ObjectNode objectNode = objectMapper.createObjectNode();

        Test1 test1 = new Test1("abc", 123);
        this.template.send("testKafkaSend1", test1);

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }

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
}
