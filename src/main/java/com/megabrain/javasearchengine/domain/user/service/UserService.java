package com.megabrain.javasearchengine.domain.user.service;

import com.megabrain.javasearchengine.domain.user.dao.UserCookieManager;
import com.megabrain.javasearchengine.domain.user.dao.UserDao;
import com.megabrain.javasearchengine.domain.user.dto.UserLoginRequestDTO;
import com.megabrain.javasearchengine.domain.user.dto.UserProfileResponseDTO;
import com.megabrain.javasearchengine.domain.user.dto.UserRegisterRequestDTO;
import com.megabrain.javasearchengine.domain.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserCookieManager userCookieManager;

    public UserService(UserDao userDao, UserCookieManager userCookieManager) {
        this.userDao = userDao;
        this.userCookieManager = userCookieManager;
    }

    public UserProfileResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        User user = User.of(userRegisterRequestDTO);
        userDao.save(user);
        return UserProfileResponseDTO.of(user);
    }

    public UserProfileResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        User user = userDao.findByUsername(userLoginRequestDTO.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return UserProfileResponseDTO.of(user);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UserProfileResponseDTO getUserProfile(String username) {
        User user = userDao.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return UserProfileResponseDTO.of(user);
    }

    public Cookie addUserCookie(String username) {
        Cookie createdCookie = new Cookie("user", username);
        userCookieManager.addUserCookie(createdCookie);
        return createdCookie;
    }
}
