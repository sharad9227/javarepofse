package com.tweetapp.demo.kafka.Consumer;

import com.tweetapp.demo.entity.TweetEntity;
import com.tweetapp.demo.kafka.Producer.ProducerTweet;
import com.tweetapp.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsumerTweet {
    private final Logger logger = LoggerFactory.getLogger(ProducerTweet.class);
    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload String message, @Headers MessageHeaders messageHeaders) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        // tweetEntity.setLoginId(username);
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setMessage(message);
        tweetEntity.setLoginId((String) messageHeaders.get("kafka_receivedMessageKey"));
        //saving in database
        userService.postTweet(tweetEntity);
    }
}