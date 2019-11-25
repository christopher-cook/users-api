package com.example.usersapi.exception;

public class UserExistsException extends Exception{
    public UserExistsException(String msg) {
        super(msg);
    }
}
