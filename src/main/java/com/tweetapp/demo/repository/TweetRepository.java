package com.tweetapp.demo.repository;

import com.tweetapp.demo.entity.TweetEntity;
import com.tweetapp.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TweetRepository implements mongoOperations {

    private final MongoTemplate template;

    @Autowired
    public TweetRepository(final MongoTemplate template) {
        this.template = template;
    }


    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public UserEntity saveUser(UserEntity entity) {
        return null;
    }


    public TweetEntity saveTweet(TweetEntity tweetMessage) {

        return template.save(tweetMessage);
    }

    public TweetEntity findByCriteria(Query query){
        return template.findOne(query,TweetEntity.class);
    }


    public List<TweetEntity> fetchAll() {

        return template.findAll(TweetEntity.class);

    }

}




