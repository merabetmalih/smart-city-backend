package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository  extends JpaRepository<Attribute,Long> {
}
