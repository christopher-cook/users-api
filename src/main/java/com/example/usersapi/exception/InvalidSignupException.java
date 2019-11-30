package com.example.usersapi.exception;

public class InvalidSignupException extends  Exception{
  public InvalidSignupException(String msg) {
    super(msg);
  }
}