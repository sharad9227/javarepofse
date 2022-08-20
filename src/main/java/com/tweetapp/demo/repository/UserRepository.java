package com.tweetapp.demo.repository;


import com.tweetapp.demo.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements mongoOperations {


    private final MongoTemplate template;
    @Autowired
    public UserRepository(final MongoTemplate template)  {
        this.template = template;
    }



    public String altPairs(String str) {


        String append = "";
        for(int i=0;i<str.length()-1;i++)
        {

            append= append + str.charAt(i+1);i=i+1;
        }
        //System.out.println(append);

        return append;




    }

    @Override
    public List<UserEntity> findAll() {
        return template.findAll(UserEntity.class);
    }


    @Override
    public UserEntity saveUser(UserEntity user)
    {
       return template.save(user);
    }



    public UserEntity findByCriteria(Query query){
        return template.findOne(query,UserEntity.class);
    }






}
