package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.User;

import java.util.Collection;

public interface UserService extends BaseEntityService<User> {

    User getUserByUsername(String username);

    User getUserById(long id);

    Page<User> fetchPage(PageRequest request);

    OperationResult<User> signIn(String username, String password);

    OperationResult<User> signOut();

    OperationResult<User> saveUser(User user);

    OperationResult<String> deleteUserByIds(Collection<Long> ids);

    OperationResult<User> updateUser(User user);
}