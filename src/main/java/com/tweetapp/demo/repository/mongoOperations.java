package com.tweetapp.demo.repository;

import com.tweetapp.demo.entity.UserEntity;

import java.util.List;

public interface mongoOperations {

    List<UserEntity> findAll();
    UserEntity saveUser(UserEntity entity);


//    List<Person> getAllPerson();
//    List<Person> getAllPersonPaginated(
//            int pageNumber, int pageSize);
//    Person findOneByName(String name);
//    List<Person> findByName(String name);
//    List<Person> findByBirthDateAfter(Date date);
//    List<Person> findByAgeRange(int lowerBound, int upperBound);
//    List<Person> findByFavoriteBooks(String favoriteBook);
//    void updateMultiplePersonAge();
//    Person updateOnePerson(Person person);
//    void deletePerson(Person person);
}
