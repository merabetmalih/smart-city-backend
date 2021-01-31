package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.CategoryStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryStoreRepository extends JpaRepository<CategoryStore,Long> {

    Optional<CategoryStore> findByName(String name);
}
