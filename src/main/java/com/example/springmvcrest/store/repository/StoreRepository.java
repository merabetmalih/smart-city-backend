package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByProviderId(Long id);

}
