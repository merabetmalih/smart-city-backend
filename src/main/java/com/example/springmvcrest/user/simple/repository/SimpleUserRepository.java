package com.example.springmvcrest.user.simple.repository;

import com.example.springmvcrest.user.simple.domain.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimpleUserRepository  extends JpaRepository<SimpleUser,Long> {
    Optional<SimpleUser> findByEmail(String email);
}
