package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Long> {
}
