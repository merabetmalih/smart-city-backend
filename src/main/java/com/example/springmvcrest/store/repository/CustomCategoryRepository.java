package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory,Long> {


    List<CustomCategory> findByStore_Provider_Id(Long id);
    List<CustomCategory> findByStore_Id(Long id);
}
