
package com.example.usersapi.service;

import com.example.usersapi.exception.InvalidSignupException;
import com.example.usersapi.exception.LoginException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
//import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService //extends UserDetailsService
{
  public Iterable<User> listUsers();

  public JwtResponse createUser(User user) throws InvalidSignupException;
  public JwtResponse login(User user) throws LoginException;
}
