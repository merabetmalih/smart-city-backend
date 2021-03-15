package com.example.springmvcrest.user.simple.repository;

import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SimpleUserRepository  extends JpaRepository<SimpleUser,Long> {
    Optional<SimpleUser> findByEmail(String email);


}
