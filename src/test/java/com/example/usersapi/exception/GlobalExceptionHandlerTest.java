package com.example.usersapi.exception;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GlobalExceptionHandlerTest {
  @InjectMocks
  GlobalExceptionHandler globalExceptionHandler;

  @Test
  public void handleException_ResponseEntity_Success() {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "test");

    errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    errorResponse.setMessage("test");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    errorResponse.setTimestamp(LocalDateTime.now().format(formatter));


    errorResponse.getHttpStatus();
    errorResponse.getMessage();
    errorResponse.getTimestamp();



    ResponseEntity<ErrorResponse> testResponse = new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ErrorResponse> result = globalExceptionHandler.handleLoginExceptions(new Exception("test"));
    assertEquals(testResponse.getStatusCode(), result.getStatusCode());

  }
}
