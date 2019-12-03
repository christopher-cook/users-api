package com.example.usersapi.service;

import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

  @Autowired
  UserRoleRepository userRoleRepository;

  @Override
  public UserRole createRole(UserRole role) {
    return userRoleRepository.save(role);
  }

  @Override
  public Iterable<UserRole> getAllUserRoles() {
    return userRoleRepository.findAll();
  }
}
