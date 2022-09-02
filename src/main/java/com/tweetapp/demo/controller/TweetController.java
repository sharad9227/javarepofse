package com.tweetapp.demo.controller;

import com.tweetapp.demo.TweetAppApplication;
import com.tweetapp.demo.dto.ResponseObject;
import com.tweetapp.demo.entity.TweetEntity;
import com.tweetapp.demo.entity.UserEntity;
import com.tweetapp.demo.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1.0/tweets")
public class TweetController {

    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    private UserService userService;


    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> registerUserNew(@RequestBody UserEntity requestObject) throws Exception {

        return userService.registerUser(requestObject);


    }


    //get all users:: admin page request
    @CrossOrigin
    @RequestMapping(value = "/users/getall", method = RequestMethod.GET)
    public void getAll(@PathVariable Integer userId) throws Exception{
        System.out.println("here");
       // return userService.getUsersInfo(userId);

    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> loginUser(@RequestBody UserEntity requestObject) throws Exception {
        return userService.loginUser(requestObject.getEmail(),requestObject.getPassword());
    }

    @CrossOrigin
    @RequestMapping(path = "/{username}/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> saveTweet(@PathVariable String username,@RequestBody TweetEntity message) throws Exception {
        TweetEntity requestObject=new TweetEntity();

        requestObject.setMessage(message.getMessage());
        requestObject.setLoginId(username);
        return userService.postTweet(requestObject);
    }

    @CrossOrigin
    @RequestMapping(path = "/{username}/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> updateTweet(@PathVariable String username, @PathVariable String id, @RequestBody  String message) throws Exception {
        TweetEntity requestObject=new TweetEntity();
        requestObject.setMessage(message);
        requestObject.setLoginId(username);
        requestObject.setId(id);
        return userService.updateTweet(requestObject);
    }
    @CrossOrigin
    @RequestMapping(path = "/{username}/delete/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> deleteTweet(@PathVariable String username,@RequestBody  String message) throws Exception {
        TweetEntity requestObject=new TweetEntity();
        requestObject.setMessage(message);
        return userService.postTweet(requestObject);
    }


    @CrossOrigin
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<TweetEntity> getAllTweets() throws Exception {

        return userService.getTweets();
    }










}

