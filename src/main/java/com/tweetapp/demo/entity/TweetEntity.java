package com.tweetapp.demo.entity;

import com.sun.tools.javac.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TweetEntity {
    @Id
    private String id;

    @Indexed
    private String loginId;

    private String message;

    private LocalDateTime tweetTime;

    private int likeCount;

    private String firstName;

    private String lastName;





}
