package com.megabrain.javasearchengine.domain.user.model;

import com.megabrain.javasearchengine.domain.user.dto.UserRegisterRequestDTO;
import com.megabrain.javasearchengine.global.constants.ServicePolicy;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {}

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User of(UserRegisterRequestDTO dto) {
        return new User(dto.getUsername(), dto.getPassword(), ServicePolicy.DEFAULT_USER_ROLE);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
