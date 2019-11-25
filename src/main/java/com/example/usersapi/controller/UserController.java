package com.example.usersapi.controller;

import com.example.usersapi.exception.LoginException;
import com.example.usersapi.exception.UserExistsException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

//  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/list")
  public Iterable<User> listUsers(@RequestHeader("userId") String userId) {
    System.out.println(userId);
    return userService.listUsers();
  }

  @PostMapping("/signup")
  public ResponseEntity<?> createUser(@RequestBody User user) throws UserExistsException {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) throws LoginException {
    return ResponseEntity.ok(userService.login(user));
  }
}
