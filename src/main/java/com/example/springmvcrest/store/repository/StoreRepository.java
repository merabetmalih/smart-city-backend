package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
