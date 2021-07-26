package com.example.springmvcrest.product.repository;

import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoriesIn(Set<Category> category);

    List<Product> findAllByCustomCategory_Id(Long id);

    List<Product> findAllByCustomCategory_Store_Provider_Id(Long id);

    List<Product> findAllByCustomCategory_StoreId(Long id);

    @Query(value = "SELECT *\n" +
            "FROM\n" +
            "  (SELECT custom_category.id AS custom_category_id\n" +
            "   FROM\n" +
            "     (SELECT *\n" +
            "      FROM\n" +
            "        (SELECT *,\n" +
            "                ST_Distance(ST_Transform(ST_SetSRID(ST_Point(:latitude, :longitude), 4326), 2100), ST_Transform(ST_SetSRID(ST_Point(store_address.latitude, store_address.longitude), 4326), 2100))/1000 AS distance\n" +
            "         FROM store,\n" +
            "              store_address\n" +
            "         WHERE store.id = store_address.store ) AS stores_distance\n" +
            "      WHERE stores_distance.distance < :distance\n" +
            "      ORDER BY distance ASC) AS stores_match_distance,\n" +
            "        custom_category\n" +
            "   WHERE stores_match_distance.store = custom_category.store) AS custom_category_match,\n" +
            "     product\n" +
            "WHERE product.custom_category = custom_category_match.custom_category_id\n" +
            "  AND product.deleted = FALSE",

            countQuery = "SELECT count(*)\n" +
                    "FROM\n" +
                    "  (SELECT custom_category.id AS custom_category_id\n" +
                    "   FROM\n" +
                    "     (SELECT *\n" +
                    "      FROM\n" +
                    "        (SELECT *,\n" +
                    "                ST_Distance(ST_Transform(ST_SetSRID(ST_Point(:latitude, :longitude), 4326), 2100), ST_Transform(ST_SetSRID(ST_Point(store_address.latitude, store_address.longitude), 4326), 2100))/1000 AS distance\n" +
                    "         FROM store,\n" +
                    "              store_address\n" +
                    "         WHERE store.id = store_address.store ) AS stores_distance\n" +
                    "      WHERE stores_distance.distance < :distance\n" +
                    "      ORDER BY distance ASC) AS stores_match_distance,\n" +
                    "        custom_category\n" +
                    "   WHERE stores_match_distance.store = custom_category.store) AS custom_category_match,\n" +
                    "     product\n" +
                    "WHERE product.custom_category = custom_category_match.custom_category_id\n" +
                    "  AND product.deleted = FALSE",
            nativeQuery = true)
    Page<Product> findProductAround(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance, Pageable pageable);
}
