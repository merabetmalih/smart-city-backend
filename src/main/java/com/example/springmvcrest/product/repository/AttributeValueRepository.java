package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValue,Long> {
}
