package com.example.usersapi.integration;


import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserRepository;
import com.example.usersapi.service.UserProfileServiceImpl;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProfileIntegrationTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserProfileServiceImpl userProfileService;


  private User createUser() {
    User user = new User();
    user.setUsername("test");
    user.setEmail("test@test.com");
    user.setPassword("test");
    return user;
  }

  private UserProfile createProfile() {
    UserProfile testProfile = new UserProfile();
    testProfile.setMobile("111-111-111");
    testProfile.setAddress("test");
    testProfile.setAdditionalEmail("test@test.com");
    return testProfile;
  }

  @Test
  public void a_createUserProfile() {
    User user = createUser();
    user = userRepository.save(user);
    User foundUser = userRepository.findByUsername(user.getUsername());
    UserProfile testProfile = createProfile();

    UserProfile userProfile = userProfileService.createProfile(foundUser.getId(),testProfile);

    assertEquals(testProfile.getAddress(), userProfile.getAddress());
  }

  @Test
  public void b_createUserProfile() {
    User user = createUser();
    User foundUser = userRepository.findByUsername(user.getUsername());
    UserProfile testProfile = createProfile();
    UserProfile userProfile = userProfileService.getProfile(foundUser.getId());
    assertEquals(testProfile.getAddress(), userProfile.getAddress());
  }

}
