package com.example.jwt.service;

import com.example.jwt.dto.RoleDto;
import com.example.jwt.model.Role;

import java.util.List;

public interface RoleService {
    public Role createRole(RoleDto roleDto);
    public Role updateRole(Integer roleId, Role updatedRole);
    public void deleteRole(Integer roleId);
    List<Role> getAllRoles();
}