package com.example.jwt.service;

import com.example.jwt.dto.UserDto;
import com.example.jwt.model.Role;
import com.example.jwt.model.User;

import java.util.List;

public interface UserService {

    String signin(String username, String password);
    User createUser(UserDto userDto);
    User updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer userId);
    List<Role> getUserRoles(Integer id);
    void grantRole(Integer userId, Integer roleId);
    void revokeRole(Integer userId, Integer roleId);
    User getById(Integer userId);
}
