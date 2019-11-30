package com.example.usersapi.controller;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  /**Main entry point for server-side Spring MVC test support.**/
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtUtil jwtUtil;


  @Test
  public void listUsers_Returns200_Success () throws Exception {
    User testUser = new User();
    testUser.setUsername("batman");
    List<User> users = new ArrayList<>();
    users.add(testUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/list")
        .header("userId", "1");

    when(userService.listUsers()).thenReturn(users);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"username\": \"batman\" }]"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void login_Returns200_Success() throws Exception {

    JwtResponse jwtResponse = new JwtResponse("123456", "test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserInJson("test@test.com", "test","test"));

    when(userService.login(any())).thenReturn(jwtResponse);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"token\":\"123456\",\"username\":\"test\"}"))
        .andReturn();


    System.out.println(result.getResponse().getContentAsString());
  }


  @Test
  public void signup_Returns200_Success() throws Exception {
    JwtResponse jwtResponse = new JwtResponse("123456", "test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserInJson("test@test.com", "test","test"));

    when(userService.createUser(any())).thenReturn(jwtResponse);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"token\":\"123456\",\"username\":\"test\"}"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  private static String createUserInJson (String email, String username, String password) {
    return "{ \"email\": \"" + email + "\", " +
        "\"username\":\"" + username + "\"," +
        "\"password\": \"" + password + "\"}";
  }

}