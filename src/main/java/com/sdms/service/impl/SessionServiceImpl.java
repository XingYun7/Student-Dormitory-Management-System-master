package com.sdms.service.impl;

import com.sdms.entity.User;
import com.sdms.service.RoomRequestService;
import com.sdms.service.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService {

    private final String CURRENT_USER = "currentUser";

    private final String NO_HANDLE_COUNT = "noHandleCount";

    @Resource
    private HttpSession session;

    @Resource
    private RoomRequestService roomRequestService;

    @Override
    public User getCurrentUser() {
        return (User) this.get(CURRENT_USER);
    }

    @Override
    public void setCurrentUser(User user) {
        session.setAttribute(CURRENT_USER, user);
    }

    @Override
    public void removeCurrentUser() {
        session.removeAttribute(CURRENT_USER);
    }

    @Override
    public Object get(String key) {
        return session.getAttribute(key);
    }

    @Override
    public void refreshNoHandleCount() {
        if (session.getAttribute(NO_HANDLE_COUNT) != null) {
            session.removeAttribute(NO_HANDLE_COUNT);
        }
        session.setAttribute(NO_HANDLE_COUNT, roomRequestService.getNoHandleCount());
    }

    @Override
    public Integer getNoHandleCount() {
        return (Integer) this.get(NO_HANDLE_COUNT);
    }
}
