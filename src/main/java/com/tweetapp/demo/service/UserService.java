package com.tweetapp.demo.service;

import com.mongodb.client.result.DeleteResult;
import com.tweetapp.demo.dto.ResponseObject;
import com.tweetapp.demo.entity.TweetEntity;
import com.tweetapp.demo.entity.UserEntity;
import com.tweetapp.demo.repository.TweetRepository;
import com.tweetapp.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;

    @Autowired
    ResponseObject responseObject;

    @Autowired
    TweetRepository tweetRepository;


    /* Service method for registering new user */
    public ResponseEntity<ResponseObject> registerUser(UserEntity user) throws Exception {
        try
        {
            ResponseObject responseObject = new ResponseObject();
            UserEntity userEntity = new UserEntity();
            ArrayList<String> array = new ArrayList<String>();
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            user.setRegistrationDate(date);
            //userEntity = this.repository.saveUser(user);


            ListIterator<UserEntity> crunchifyListIterator = this.repository.findAll().listIterator();
            while (crunchifyListIterator.hasNext())
            {
                array.add(crunchifyListIterator.next().getLoginId());
            }
            if (!array.contains(user.getLoginId()))
            {
                //success flag

                userEntity = this.repository.saveUser(user);
                responseObject.setStatus(HttpStatus.OK);
                responseObject.setMessage("User Registered Successfully. Please use your email id :" + userEntity.getEmail() + " to log into the application");
                log.info(responseObject.getMessage());
                return ResponseEntity.ok().body(responseObject);
            } else
            {
                //error flag :405 Method Not Allowed :Some processing error
                responseObject.setStatus(HttpStatus.FORBIDDEN);
                responseObject.setMessage("Error occurred in Registration.Please try later");
                return new ResponseEntity(responseObject, responseObject.getStatus());
            }


        } catch (Exception e)
        {

            //  responseObject.setResponseObj(null);
            Throwable cause = e;
            if (cause.getMessage().contains("duplicate key"))
            {
                responseObject.setErrorMessage("Duplicate entry.Email already exists");
                responseObject.setStatus(HttpStatus.CONFLICT);
                responseObject.setMessage("Exception occurred" + e.getMessage());
                //status - 409 conflict for duplicate email
                log.error(responseObject.getMessage());
                throw new ResponseStatusException(responseObject.getStatus(), responseObject.getErrorMessage(), cause);
            }

            // return ResponseEn
            //    return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);
        }

        return ResponseEntity.badRequest().body(responseObject);


    }


    /* Service method for checking if user is valid or not by matching supplied email and password */

    public ResponseEntity<ResponseObject> loginUser(String email, String password) throws Exception {
        //   HashMap<String, Object> userDetails = new HashMap<String, Object>();
        try
        {

            Query query = new Query();
            UserEntity userEntity = new UserEntity();
            query.addCriteria(Criteria.where("email").is(email));
            query.addCriteria(Criteria.where("password").is(password));
            userEntity = repository.findByCriteria(query);
            if (userEntity != null)
            {
                if (!StringUtils.isEmpty(userEntity.getLoginId()))
                {
                    //setting login information in login gesture table
                    responseObject.setStatus(HttpStatus.OK);
                    responseObject.setMessage("Active and Approved User");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("loginId", userEntity.getLoginId());
                    responseObject.setLoginId(map);
                }
                return ResponseEntity.ok().body(responseObject);
            } else
            {
                responseObject.setStatus(HttpStatus.UNAUTHORIZED);
                responseObject.setMessage("User name or password is incorrect.Please try again!");
                responseObject.setResponseObj(null);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception e)
        {
            //  responseObject.setErrorMessage("Duplicate entry.Email already exists");
            responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseObject.setMessage("Exception occurred" + e.getMessage());
            //status - 409 conflict for duplicate email
            log.error(responseObject.getMessage());

            return ResponseEntity.badRequest().body(responseObject);
        }

    }


    /* Service method for inserting tweets into the database */

    public ResponseEntity<ResponseObject> postTweet(TweetEntity tweetEntity) {
        try
        {
            if (!tweetEntity.getMessage().isEmpty())
            {
                TweetEntity tweet = new TweetEntity();
                //  tweetEntity.setLoginId(tweetEntity.getLoginId());
                //  tweetEntity.setMessage(tweetEntity.getMessage());
                tweetEntity.setTweetTime(LocalDateTime.now());
                tweet = this.tweetRepository.saveTweet(tweetEntity);
                if (tweet != null)
                {
                    responseObject.setStatus(HttpStatus.OK);
                    responseObject.setMessage("Tweet Saved Successfully");
                }
            }

            return ResponseEntity.ok().body(responseObject);

        } catch (Exception ex)
        {
            responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseObject.setMessage("Error in Saving Tweet" + ex.getMessage());
            return ResponseEntity.internalServerError().body(responseObject);

        }

    }


    public ResponseEntity<ResponseObject> updateTweet(TweetEntity tweetEntity) {
        TweetEntity tweet = new TweetEntity();
        Query query = new Query();
        try
        {

            if (!tweetEntity.getId().isEmpty())
            {
                //   Query query=new Query();
                query.addCriteria(Criteria.where("_id").is(tweetEntity.getId()));
                tweetEntity.setTweetTime(LocalDateTime.now());

                tweet = this.tweetRepository.findByCriteria(query);
                if (tweet != null)
                {
                    Update update = new Update();
                    update.set("message", tweetEntity.getMessage());
                    this.tweetRepository.updateByCriteria(query, update);
                    responseObject.setStatus(HttpStatus.OK);
                    responseObject.setMessage("Tweet Updated Successfully");
                }
            }

            return ResponseEntity.ok().body(responseObject);

        } catch (Exception ex)
        {
            responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseObject.setMessage("Error in Updating Tweet" + ex.getMessage());
            return ResponseEntity.internalServerError().body(responseObject);

        }
    }


    public List<TweetEntity> getTweets() {
        return this.tweetRepository.fetchAll();

    }
    /* Service method for increasing like count of tweets into the database */

    public ResponseEntity<ResponseObject> likeTweet(TweetEntity tweetEntity) {
        TweetEntity tweet = new TweetEntity();
        Query query = new Query();
        try
        {

            if (!tweetEntity.getId().isEmpty())
            {
                //   Query query=new Query();
                query.addCriteria(Criteria.where("_id").is(tweetEntity.getId()));
                tweet = this.tweetRepository.findByCriteria(query);

                Update update = new Update();
                update.set("likeCount", tweet.getLikeCount() + 1);
                this.tweetRepository.updateByCriteria(query, update);
                responseObject.setStatus(HttpStatus.OK);
                responseObject.setMessage("Tweet Updated Successfully");
            }


            return ResponseEntity.ok().body(responseObject);

        } catch (Exception ex)
        {
            responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseObject.setMessage("Error in Updating Tweet" + ex.getMessage());
            return ResponseEntity.internalServerError().body(responseObject);

        }


    }

    /* Service method for getting tweet of specific user */

    public List<TweetEntity> getUsersInfo(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("loginId").is(userId));
        List<TweetEntity> tweets = this.tweetRepository.fetchAll();
        return tweets.stream().filter(e -> e.getLoginId().equals(userId)).collect(Collectors.toList());
    }

    /* Service method for deleting tweet from the database */

    public ResponseEntity<ResponseObject> deleteTweet(TweetEntity tweetEntity) {
        //TweetEntity tweet = new TweetEntity();
        Query query = new Query();
        try
        {

            if (!tweetEntity.getId().isEmpty())
            {
                //   Query query=new Query();
                query.addCriteria(Criteria.where("_id").is(tweetEntity.getId()));
                DeleteResult result = this.tweetRepository.deleteById(query);
                //  tweet.setLikeCount(tweet.getLikeCount() + 1);
                if (result != null)
                {
                    responseObject.setStatus(HttpStatus.OK);
                    responseObject.setMessage("Tweet Updated Successfully");
                }
            }


            return ResponseEntity.ok().body(responseObject);

        } catch (Exception ex)
        {
            responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseObject.setMessage("Error in Updating Tweet" + ex.getMessage());
            return ResponseEntity.internalServerError().body(responseObject);

        }
    }
}
