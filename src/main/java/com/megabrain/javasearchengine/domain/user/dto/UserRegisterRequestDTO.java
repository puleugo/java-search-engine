package com.megabrain.javasearchengine.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDTO {
    private String username;
    private String password;
}
