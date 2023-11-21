package com.megabrain.javasearchengine.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String username;
    private String password;

    public UserLoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
