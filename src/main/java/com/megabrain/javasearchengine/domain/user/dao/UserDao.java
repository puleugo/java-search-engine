package com.megabrain.javasearchengine.domain.user.dao;

import com.megabrain.javasearchengine.domain.user.model.User;
import com.megabrain.javasearchengine.domain.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT user.role FROM User user WHERE user.username = :username")
    Optional<UserRole> findUserRoleByUsername(String username);
}
