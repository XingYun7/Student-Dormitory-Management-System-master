package com.sdms.service.impl;

import com.sdms.dao.RoleDao;
import com.sdms.entity.Role;
import com.sdms.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> listAllRoles() {
        return roleDao.findAll();
    }
}
