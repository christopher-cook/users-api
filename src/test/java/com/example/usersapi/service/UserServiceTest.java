package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.exception.InvalidSignupException;
import com.example.usersapi.exception.LoginException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class UserServiceTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private BCryptPasswordEncoder encoder;

  private User user;

  @Before
  public void initData() {
    user = new User();
    user.setId(1L);
    user.setPassword("test");
    user.setUsername("test");
    user.setEmail("test@test.com");
  }

  @Test
  public void createUser_User_Success() throws InvalidSignupException {

    when(userRepository.save(any())).thenReturn(user);
    when(encoder.encode(anyString())).thenReturn("123456");
    when(jwtUtil.generateToken(any())).thenReturn("123456");

    JwtResponse jwtResponse = userService.createUser(user);

    assertEquals(jwtResponse.getToken(), "123456");
  }

  @Test
  public void loginUser_User_Success() throws LoginException {

    when(userRepository.login(anyString())).thenReturn(user);
    when(encoder.matches(any(), any())).thenReturn(true);
    when(jwtUtil.generateToken(any())).thenReturn("123456");

    JwtResponse jwtResponse = userService.login(user);

    assertEquals(jwtResponse.getToken(), "123456");
  }

}
