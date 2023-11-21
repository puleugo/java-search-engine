package com.megabrain.javasearchengine.domain.user.controller;

import com.megabrain.javasearchengine.domain.user.dto.UserLoginRequestDTO;
import com.megabrain.javasearchengine.domain.user.dto.UserProfileResponseDTO;
import com.megabrain.javasearchengine.domain.user.dto.UserRegisterRequestDTO;
import com.megabrain.javasearchengine.domain.user.dao.UserCookieManager;
import com.megabrain.javasearchengine.domain.user.service.UserService;
import com.megabrain.javasearchengine.global.annotation.LoginGuards;
import com.megabrain.javasearchengine.global.annotation.LoginUsername;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;

@Controller()
@RequestMapping("/users")
public class UserController {
    final private UserService userService;
    final private UserCookieManager userCookieManager;

    public UserController(UserService userService, UserCookieManager userCookieManager) {
        this.userService = userService;
        this.userCookieManager = userCookieManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
        @RequestBody UserRegisterRequestDTO userRegisterRequestDTO
    ) {
        UserProfileResponseDTO user = userService.registerUser(userRegisterRequestDTO);

        Cookie createdCookie = userService.addUserCookie(user.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, String.format("%s=%s; Path=/", createdCookie.getName(), createdCookie.getValue()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        UserProfileResponseDTO user = userService.loginUser(userLoginRequestDTO);
        Cookie cookie = userService.addUserCookie(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, String.format("%s=%s; Path=/", cookie.getName(), cookie.getValue()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @LoginGuards
    public ResponseEntity<String> logoutUser(@LoginUsername String username) {
        userCookieManager.removeUserSession(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile")
    @LoginGuards
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(
        @LoginUsername String username
    ) {
        UserProfileResponseDTO userProfileResponseDTO = userService.getUserProfile(username);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.OK);
    }
}
