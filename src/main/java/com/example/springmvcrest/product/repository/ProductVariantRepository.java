package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant , Long> {
}
