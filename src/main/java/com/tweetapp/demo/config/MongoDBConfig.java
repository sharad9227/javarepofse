package com.tweetapp.demo.config;


import com.tweetapp.demo.repository.TweetRepository;
import com.tweetapp.demo.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {UserRepository.class, TweetRepository.class})
@Configuration
public class MongoDBConfig {


}
