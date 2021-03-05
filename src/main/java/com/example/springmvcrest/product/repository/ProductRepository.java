package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoriesIn(Set<Category> category);

    List<Product> findAllByCustomCategory_Id(Long id);

    List<Product> findAllByCustomCategory_Store_Provider_Id(Long id);

}
