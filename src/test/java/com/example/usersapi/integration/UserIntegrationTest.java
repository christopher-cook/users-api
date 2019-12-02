package com.example.usersapi.integration;

import com.example.usersapi.model.User;
import com.example.usersapi.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

  private User createUser() {

    User user = new User();
    user.setUsername("test");
    user.setEmail("test@test.com");
    user.setPassword("test");

    return user;
  }

  @Autowired
  UserRepository userRepository;

  @Test
  public void signup_User_Success() {
    User user = userRepository.findByUsername("test");
    if (user != null) {
      userRepository.delete(user);
    }
    user = createUser();
    user = userRepository.save(user);
    User foundUser = userRepository.findByUsername(user.getUsername());

    assertNotNull(user);
    assertNotNull(foundUser);
    assertEquals(user.getId(), foundUser.getId());

    userRepository.delete(user);
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void signup_DuplicateUsername_Exception() {
    User user = createUser();
    userRepository.save(user);
    user.setId(null);
    userRepository.save(user);
  }

}
