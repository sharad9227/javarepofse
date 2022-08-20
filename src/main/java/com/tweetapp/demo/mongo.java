package com.tweetapp.demo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class mongo {
    public MongoCollection<Document> getCollection() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://sharad:sharad@cluster0.svfli.mongodb.net/test?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient =  MongoClients.create(settings);
     //   MongoDatabase database = mongoClient.getDatabase("test");

        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = mongoDatabase.getCollection("Users");

      //  System.out.println(collection.find());
//        List<Object> Response =new ArrayList<Object>();
//        for (Document doc:collection.find()) {
//            Response.add(doc);
//            System.out.println("size deta" +  doc);
//        }
        //System.out.println("size deta" +Response.size());
        return collection;
    }

}
