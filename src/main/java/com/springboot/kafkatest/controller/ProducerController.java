package com.springboot.kafkatest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.kafkatest.dto.Test1;
import com.springboot.kafkatest.dto.Test2;
import com.springboot.kafkatest.dto.Test3;
import com.springboot.kafkatest.dto.Test4;
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
            this.template.send("testKafkaSend4", Integer.toString(i), test4);
        }

        objectNode.put("status", "SUCCESS");
        return objectNode;
    }
}
