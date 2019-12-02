package com.example.usersapi.feature;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
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

import static org.junit.Assert.assertEquals;
@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProfileFeatureTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  private User createUser() {

    User user = new User();
    user.setUsername("test");
    user.setEmail("test@test.com");
    user.setPassword("test");

    return user;
  }

  private UserProfile createProfile() {
    UserProfile testUserProfile = new UserProfile();
    testUserProfile.setAdditionalEmail("test@test.com");
    testUserProfile.setAddress("test");
    testUserProfile.setMobile("test");
    return testUserProfile;
  }

  @Test
  public void a_createProfile_Profile_Success() throws Exception {
    User user = createUser();
    user = userRepository.save(user);

    User foundUser = userRepository.findByUsername(user.getUsername());

    UserProfile profile = createProfile();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/profile")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserProfileInJson(profile.getAdditionalEmail(), profile.getMobile(), profile.getAddress()))
        .header("userId", String.valueOf(foundUser.getId()));

    MvcResult createdProfile = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"mobile\": \"test\" }"))
        .andReturn();


  }

  @Test
  public void b_getProfile_Profile_Success() throws Exception {
    User user = createUser();
    User foundUser = userRepository.findByUsername(user.getUsername());

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/profile")
        .header("userId", String.valueOf(foundUser.getId()));

    MvcResult token = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"mobile\": \"test\" }"))
        .andReturn();

  }

  private static String createUserProfileInJson (String additionalEmail, String mobile, String address) {
    return "{ \"additionalEmail\": \"" + additionalEmail + "\", " +
        "\"mobile\":\"" + mobile + "\"," +
        "\"address\": \"" + address + "\"}";
  }
}
