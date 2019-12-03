package com.example.usersapi.controller;

import com.example.usersapi.model.UserRole;
import com.example.usersapi.service.UserRoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRoleController {

  @Autowired
  UserRoleService userRoleService;

  @GetMapping("/user_roles")
  public Iterable<UserRole> getUserRoles(@RequestHeader("userId") String userId) {
    return userRoleService.getAllUserRoles();
  }
}
