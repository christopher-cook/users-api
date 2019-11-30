package com.example.usersapi.service;

import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class UserProfileServiceTest {

  @InjectMocks
  private UserProfileServiceImpl userProfileService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserProfileRepository userProfileRepository;

  private User user;
  private UserProfile userProfile;

  @Before
  public void initData() {
    user = new User();
    user.setId(1L);
    user.setPassword("test");
    user.setUsername("test");
    user.setEmail("test@test.com");

    userProfile = new UserProfile();
    userProfile.setId(1L);
    userProfile.setAddress("test");
    userProfile.setAdditionalEmail("test@test.com");
    userProfile.setMobile("111-111-111");
    user.setUserProfile(userProfile);
  }


  @Test
  public void getUserProfile_UserProfile_Success() {

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    UserProfile foundUserProfile = userProfileService.getProfile(1L);

    assertEquals(userProfile, foundUserProfile);
  }

  @Test
  public void createUserProfile_UserProfile_Success() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userProfileRepository.save(any())).thenReturn(userProfile);

    UserProfile createdUserProfile = userProfileService.createProfile(1L, userProfile);
    assertEquals(userProfile, createdUserProfile);
  }

  @Test
  public void createUserProfileWithExistingProfile_UserProfile_Success() {
    user.setUserProfile(null);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userProfileRepository.save(any())).thenReturn(userProfile);

    UserProfile createdUserProfile = userProfileService.createProfile(1L, userProfile);
    assertEquals(userProfile, createdUserProfile);
  }
}
