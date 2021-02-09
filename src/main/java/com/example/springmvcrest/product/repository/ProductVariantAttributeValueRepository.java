package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.ProductVariantAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantAttributeValueRepository extends JpaRepository<ProductVariantAttributeValue,Long> {
}
