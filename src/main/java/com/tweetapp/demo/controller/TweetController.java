package com.tweetapp.demo.controller;


import com.tweetapp.demo.dto.ResponseObject;
import com.tweetapp.demo.entity.TweetEntity;
import com.tweetapp.demo.entity.UserEntity;
import com.tweetapp.demo.kafka.Producer.ProducerTweet;
import com.tweetapp.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/tweets")
public class TweetController {

    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private ProducerTweet kafkaSender;


    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> registerUserNew(@RequestBody UserEntity requestObject) throws Exception {
        return userService.registerUser(requestObject);
    }


    //get all users:: admin page request
    @CrossOrigin
    @RequestMapping(value = "/users/getall/{userId}", method = RequestMethod.GET)
    public List<TweetEntity> getAllByUserId(@PathVariable String userId) throws Exception {
        return userService.getUsersInfo(userId);

    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> loginUser(@RequestBody UserEntity requestObject) throws Exception {
        return userService.loginUser(requestObject.getEmail(), requestObject.getPassword());
    }

    @CrossOrigin
    @RequestMapping(path = "/{username}/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveTweet(@PathVariable String username, @RequestBody String message) throws Exception {
        TweetEntity requestObject = new TweetEntity();

        requestObject.setMessage(message);
        requestObject.setLoginId(username);
        // create kafka sending logic here
        this.kafkaSender.sendMessage(requestObject);

        // return userService.postTweet(requestObject);
    }

    @CrossOrigin
    @PutMapping("/{username}/update/{id}")
    @ResponseBody
    public ResponseEntity<ResponseObject> updateTweet(@PathVariable String username, @PathVariable String id, @RequestBody String message) throws Exception {
        TweetEntity requestObject = new TweetEntity();
        requestObject.setMessage(message);
        requestObject.setLoginId(username);
        requestObject.setId(id);
        return userService.updateTweet(requestObject);
    }

    @PutMapping("/{username}/like/{id}")
    @ResponseBody
    public ResponseEntity<ResponseObject> likeTweet(@PathVariable String username, @PathVariable String id) throws Exception {
        TweetEntity requestObject = new TweetEntity();
        requestObject.setLoginId(username);
        requestObject.setId(id);
        return userService.likeTweet(requestObject);
    }

    @CrossOrigin
    @RequestMapping(path = "/{username}/delete/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> deleteTweet(@PathVariable String username, @RequestBody String message) throws Exception {
        TweetEntity requestObject = new TweetEntity();
        requestObject.setMessage(message);
        return userService.deleteTweet(requestObject);
    }


    @CrossOrigin
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<TweetEntity> getAllTweets() throws Exception {
        return userService.getTweets();
    }


}

