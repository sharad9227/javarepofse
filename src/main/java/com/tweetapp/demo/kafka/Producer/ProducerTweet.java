package com.tweetapp.demo.kafka.Producer;


import com.tweetapp.demo.entity.TweetEntity;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class ProducerTweet {

    private static final Logger logger = LoggerFactory.getLogger(ProducerTweet.class);
    private NewTopic topic;
    private KafkaTemplate<String, TweetEntity> kafkaTemplate;

    public ProducerTweet(NewTopic topic, KafkaTemplate<String, TweetEntity> template) {
        this.topic = topic;
        this.kafkaTemplate = template;
    }

    public void sendMessage(TweetEntity message) {
        logger.info(String.format("#### -> Producing message -> %s", message.getMessage()));
        Message<String> messageBuilder = MessageBuilder.withPayload(message.getMessage())
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .setHeader(KafkaHeaders.MESSAGE_KEY, message.getLoginId())
                .setHeader("X-Custom-Header", "Sending Custom Header with Spring Kafka")
                .build();
        kafkaTemplate.send(messageBuilder);
    }
}

