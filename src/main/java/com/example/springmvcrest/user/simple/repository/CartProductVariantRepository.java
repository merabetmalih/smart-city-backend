package com.example.springmvcrest.user.simple.repository;

import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import com.example.springmvcrest.user.simple.domain.CartProductVariantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductVariantRepository extends JpaRepository<CartProductVariant, CartProductVariantId> {


}
