package com.example.jwt.rest;

import com.example.jwt.dto.RoleDto;
import com.example.jwt.model.Role;
import com.example.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager-user-service")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/a/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleDto role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/a/update/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer roleId, @RequestBody Role updatedRole) {
        Role updated = roleService.updateRole(roleId, updatedRole);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/a/delete/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/a/getAll")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
