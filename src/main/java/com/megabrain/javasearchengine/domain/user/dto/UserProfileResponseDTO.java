package com.megabrain.javasearchengine.domain.user.dto;

import com.megabrain.javasearchengine.domain.user.model.User;
import com.megabrain.javasearchengine.domain.user.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDTO {
    private Long id;
    private String username;
    private UserRole role;

    public UserProfileResponseDTO() {}

    private UserProfileResponseDTO(Long id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static UserProfileResponseDTO of(User user) {
        return new UserProfileResponseDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
