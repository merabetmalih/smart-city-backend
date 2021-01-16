package com.example.springmvcrest.repositories;

import com.example.springmvcrest.domain.Category;
import com.example.springmvcrest.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository

public interface ProductRepository extends JpaRepository<Product,Long> {
    //List<Product> findAllByCategoriesInaAndParentProductNull(Set<Category> category);
    List<Product> findByParentProductNullAndCategoriesIn(Set<Category> category);

}
