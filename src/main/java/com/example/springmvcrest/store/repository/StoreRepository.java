package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByProviderId(Long id);

    @Query(value = "select * from\n" +
            "(SELECT *,\n" +
            "ST_Distance(ST_Transform(ST_SetSRID(ST_Point(:latitude, :longitude ),4326),2100) , ST_Transform(ST_SetSRID(ST_Point(store_address.latitude, store_address.longitude),4326),2100))/1000 as distance \n" +
            "FROM store,store_address \n" +
            "where store.id = store_address.store ) as p\n" +
            "where p.distance < :distance ORDER BY distance ASC\n", nativeQuery = true)
    List<Store> findStoreAround(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance);
}
