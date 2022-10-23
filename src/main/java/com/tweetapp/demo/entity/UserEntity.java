package com.tweetapp.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document

public class UserEntity {

    @Id
    @Indexed
    private String loginId;

    private String firstName;

    private String lastName;
    @Indexed
    private String email;

    private String password;

    private String confirmPassword;

    private BigInteger contactNumber;

    private Date registrationDate;

    @Transient
    private Collection<Role> roles = new ArrayList<>();


}
