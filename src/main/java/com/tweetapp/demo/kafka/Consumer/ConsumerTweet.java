package com.tweetapp.demo.kafka.Consumer;

import com.tweetapp.demo.kafka.Producer.ProducerTweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsumerTweet {

    private final Logger logger = LoggerFactory.getLogger(ProducerTweet.class);

    @KafkaListener(topics = "test123", groupId = "test-consumer-group")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }
}