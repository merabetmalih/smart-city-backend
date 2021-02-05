package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.DefaultCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory,Long> {

    Optional<DefaultCategory> findByName(String name);
}
