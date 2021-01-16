package com.example.springmvcrest.repositories;

import com.example.springmvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
