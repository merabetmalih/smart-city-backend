package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
