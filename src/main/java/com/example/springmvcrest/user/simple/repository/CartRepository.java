package com.example.springmvcrest.user.simple.repository;

import com.example.springmvcrest.user.simple.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
