package com.tweetapp.demo.controller;

import com.tweetapp.demo.kafka.Producer.ProducerTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping(value = "/kafka")
    public class KafkaController {

        private final ProducerTweet producer;

        @Autowired
        KafkaController(ProducerTweet producer) {
            this.producer = producer;
        }

        @PostMapping(value = "/publish")
        public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
            this.producer.sendMessage(message);
        }
    }

