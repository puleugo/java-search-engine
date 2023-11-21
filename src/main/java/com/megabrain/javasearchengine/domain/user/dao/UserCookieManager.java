package com.megabrain.javasearchengine.domain.user.dao;

import com.megabrain.javasearchengine.domain.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserCookieManager {
    private final UserDao userDao;
    private final ArrayList<String> loggedInSessions = new ArrayList<>();

    public UserCookieManager(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUserCookie(Cookie createdCookie) {
        loggedInSessions.add(createdCookie.getValue());
    }

    public void removeUserSession(String username) {
        loggedInSessions.remove(username);
    }

    public boolean hasValidateUserCookie(Cookie[] cookies) {
        Optional<String> sessionId = getUserSessionFromCookies(cookies);
        if (!sessionId.isPresent()) {
            return false;
        }
        String cookieValue = sessionId.get();
        boolean isUserCookieValid = isUserCookieValid(cookieValue);
        return isUserCookieValid;
    }

    private boolean isUserCookieValid(String sessionId) {
        return loggedInSessions.contains(sessionId);
    }

    private Optional<String> getUserSessionFromCookies(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                return Optional.ofNullable(cookie.getValue());
            }
        }
        return Optional.empty();
    }

    public User getLoginUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = getUserSessionFromCookies(cookies).orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        return userDao.findByUsername(sessionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
    }

    public String getLoginUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = getUserSessionFromCookies(cookies).orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        return sessionId;
    }
}
