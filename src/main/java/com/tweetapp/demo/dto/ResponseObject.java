package com.tweetapp.demo.dto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseObject {
    public HttpStatus status;
    // Response Message for Success / Error
    private String message;
    private HashMap<String, Object> responseObj;
    private HashMap<String, String> loginId;
    private String errorMessage;

    public HashMap<String, String> getLoginId() {
        return loginId;
    }

    public void setLoginId(HashMap<String, String> loginId) {
        this.loginId = loginId;
    }

    public HashMap<String, Object> getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(HashMap<String, Object> responseObj) {
        this.responseObj = responseObj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
