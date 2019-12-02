package com.example.usersapi.feature;

import com.example.usersapi.model.User;
import com.example.usersapi.repository.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpAndLoginTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  private User createUser() {
    User user = new User();
    user.setUsername("test1");
    user.setPassword("test1");
    user.setEmail("test1@test.com");
    return user;
  }

  @Test
  public void a_signup_User_Success() throws Exception {

    User user = createUser();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserInJson(user.getUsername(), user.getPassword(), user.getEmail()));

    MvcResult token = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andReturn();

    assertNotNull(token);
    System.out.println(token.getResponse().getContentAsString());
  }

  @Test
  public void b_login_User_Success() throws Exception {

    User user = createUser();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserInJson(user.getUsername(), user.getPassword(), user.getEmail()));

    MvcResult token = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andReturn();

    assertNotNull(token);
    System.out.println(token.getResponse().getContentAsString());
  }

  private static String createUserInJson (String name, String password, String email) {
    return "{ \"username\": \"" + name + "\", " +
        "\"password\":\"" + password + "\"," +
        "\"email\":\"" + email + "\"}";
  }
}