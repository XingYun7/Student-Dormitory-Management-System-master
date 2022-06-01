package com.sdms.service;

import com.sdms.entity.Role;

import java.util.List;

public interface RoleService extends BaseEntityService<Role> {
    List<Role> listAllRoles();
}