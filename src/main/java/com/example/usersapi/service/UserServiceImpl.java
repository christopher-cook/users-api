package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.exception.LoginException;
import com.example.usersapi.exception.UserExistsException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserRoleService userRoleService;

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  JwtUtil jwtUtil;

  @Override
  public Iterable<User> listUsers() {
    return userRepository.findAll();
  }

  @Override
  public JwtResponse createUser(User user) throws UserExistsException {
//  TODO:  uncomment this to add user roles later and in the user entity

//    UserRole userRole = userRoleService.getRole(user.getUserRole().getName());;
//    user.setUserRole(userRole);
    user.setPassword(encoder().encode(user.getPassword()));
    User savedUser = null;
    try {
      savedUser = userRepository.save(user);
    } catch (Exception e){
      throw new UserExistsException("User already exists");
    }
    if (savedUser != null) {
      String token = jwtUtil.generateToken(savedUser.getUsername());
      return new JwtResponse(token, savedUser.getUsername());

    }
    throw new UserExistsException("User already exists");
  }

  @Override
  public JwtResponse login(User user) throws LoginException {
    User foundUser = userRepository.login(user.getEmail());
    if (foundUser != null && encoder().matches(user.getPassword(), foundUser.getPassword())) {
      String token = jwtUtil.generateToken(foundUser.getUsername());
      return new JwtResponse(token, foundUser.getUsername());
    }
    throw new LoginException("Email/Password invalid!");
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

}
