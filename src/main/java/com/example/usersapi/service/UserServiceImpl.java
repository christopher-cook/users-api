package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.exception.InvalidSignupException;
import com.example.usersapi.exception.LoginException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  @Qualifier("encoder")
  PasswordEncoder bCryptPasswordEncoder;

  @Autowired
  JwtUtil jwtUtil;

  Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public Iterable<User> listUsers() {
    return userRepository.findAll();
  }

  @Override
  public JwtResponse createUser(User user) throws InvalidSignupException {
    String username = user.getUsername();
    String email = user.getEmail();
    if (username == null || username == "") {
      logger.warn("users_api_error: Username cannot be blank");
      throw new InvalidSignupException("Username cannot be blank");
    } else if (email == null || email == "") {
      logger.warn("users_api_error: Email cannot be blank");
      throw new InvalidSignupException("Email cannot be blank");
    }

    User existingUsername = userRepository.findByUsername(username);
    User existingEmail = userRepository.findByEmail(email);

    if (existingUsername != null) {
      logger.warn("users_api_error: Username already exists-" + existingUsername.getUsername());
      throw new InvalidSignupException("User already exists");
    }
    if (existingEmail != null) {
      logger.warn("users_api_error: Email already exists-" + existingEmail.getEmail());
      throw new InvalidSignupException("Email already exists");
    }

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    User savedUser = null;

    try {
      savedUser = userRepository.save(user);
    } catch (Exception e){
      logger.warn("users_api_error: User cannot be created-" + user.getUsername());
      throw new InvalidSignupException("User cannot be created ");
    }
    if (savedUser != null) {
      String token = jwtUtil.generateToken(savedUser.getUsername());
      return new JwtResponse(token, savedUser.getUsername());
    }
    logger.warn("users_api_error: User cannot be created-" + user.getUsername());
    throw new InvalidSignupException("User cannot be created");
  }

  @Override
  public JwtResponse login(User user) throws LoginException {
    User foundUser = userRepository.login(user.getEmail());
    if (foundUser != null && bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
      String token = jwtUtil.generateToken(foundUser.getUsername());
      return new JwtResponse(token, foundUser.getUsername());
    }
    logger.warn("users_api_error: Email/Password invalid!" + user.getUsername());
    throw new LoginException("Email/Password invalid!");
  }

}
