package com.sdms.service;

import com.sdms.entity.User;

public interface SessionService {

    User getCurrentUser();

    void setCurrentUser(User user);

    void removeCurrentUser();

    Object get(String key);

    void refreshNoHandleCount();

    Integer getNoHandleCount();
}
