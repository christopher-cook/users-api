package com.example.usersapi.controller;

import com.example.usersapi.model.UserProfile;
import com.example.usersapi.service.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserProfileService userProfileService;

  @Test
  public void getUserProfile_UserProfile_Success() throws Exception {
    UserProfile testUserProfile = new UserProfile();
    testUserProfile.setAdditionalEmail("test@test.com");
    testUserProfile.setAddress("test");
    testUserProfile.setMobile("test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/profile")
        .header("userId", "1");

    when(userProfileService.getProfile(anyLong())).thenReturn(testUserProfile);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"mobile\": \"test\" }"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void createUserProfile_UserProfile_Success() throws Exception {
    UserProfile testUserProfile = new UserProfile();
    testUserProfile.setAdditionalEmail("test@test.com");
    testUserProfile.setAddress("test");
    testUserProfile.setMobile("test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/profile")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createUserProfileInJson("test@test.com", "test","test"))
        .header("userId", "1");

    when(userProfileService.createProfile(anyLong(), any())).thenReturn(testUserProfile);

    MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().json("{\"mobile\": \"test\" }"))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  private static String createUserProfileInJson (String email, String mobile, String address) {
    return "{ \"email\": \"" + email + "\", " +
        "\"mobile\":\"" + mobile + "\"," +
        "\"address\": \"" + address + "\"}";
  }
}
