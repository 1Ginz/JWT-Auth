package com.example.jwt.rest;

import com.example.jwt.dto.LoginDto;
import com.example.jwt.dto.UserDto;
import com.example.jwt.model.Role;
import com.example.jwt.model.User;
import com.example.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/manager-user-service")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/as/users/signin")
    public ResponseEntity<String> signin(@RequestBody LoginDto userDto) {
        String token = userService.signin(userDto.getUsername(),userDto.getPassword());
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }

    @PostMapping("/as/users/signup")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/as/user/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        User updated = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/as/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/as/user/roles/{userId}")

    public ResponseEntity<List<Role>> getUserRoles(@PathVariable Integer userId) {
        List<Role> userRoles = userService.getUserRoles(userId);
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @PostMapping("/as/user/grant-role/{userId}/{roleId}")
    public ResponseEntity<String> grantRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        userService.grantRole(userId, roleId);
        return new ResponseEntity<>("Role granted successfully", HttpStatus.OK);
    }

    @PostMapping("/as/user/revoke-role/{userId}/{roleId}")
    public ResponseEntity<String> revokeRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        userService.revokeRole(userId, roleId);
        return new ResponseEntity<>("Role revoked successfully", HttpStatus.OK);
    }
    @GetMapping("/as/user/getById/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User infoUser = userService.getById(id);
        return new ResponseEntity<>(infoUser,HttpStatus.OK);
    }
}
