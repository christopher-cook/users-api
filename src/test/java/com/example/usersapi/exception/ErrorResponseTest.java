package com.example.usersapi.exception;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


@SpringBootTest
public class ErrorResponseTest {

    private ErrorResponse errorResponse;

  @Before
    public void init() {
      try {
          throw new IllegalArgumentException("msg");    //when we throw a error, "msg"
      } catch (Exception e) {                           //catch it
          errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
      }
  }

  @Test
  public void constructWithStatusMsg_ErrorResponse_Success() {

      errorResponse = null;
      try {
          throw new IllegalArgumentException("test");
      } catch (Exception e) {
          errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
      }
  }

    @Test
    public void getHttpStatus_Success() {
      assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
    }

    @Test
    public void setHttpStatus_Success() {
      errorResponse.setHttpStatus(HttpStatus.ACCEPTED);
    }

    @Test
    public void getErrorMessage_Success() {
      assertNotNull(errorResponse.getMessage());//check not null first
        assertEquals("msg", errorResponse.getMessage());
  }

  @Test
    public void setErrorMessage_Success() {
      assertNotEquals("test", errorResponse.getMessage());  //check throws is unique/diff to start
      errorResponse.setMessage("test");                     //set to new throw message
      assertEquals("test", errorResponse.getMessage());
  }

  @Test
    public void getTimestamp_Success() throws ParseException {
      Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(errorResponse.getTimestamp());  //get timestamp -> saved as date
      assertNotNull(date);          //check date exists
  }

  @Test
    public void setTimestamp_Success() {
      try{
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(errorResponse.getTimestamp());
      } catch (ParseException e) {
            e.getStackTrace();
      }
      errorResponse.setTimestamp("fakeTime");
      assertEquals("fakeTime", errorResponse.getTimestamp());
  }
}
