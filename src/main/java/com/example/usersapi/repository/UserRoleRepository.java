package com.example.usersapi.repository;

import com.example.usersapi.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
  public UserRole findByName(String name);
}
