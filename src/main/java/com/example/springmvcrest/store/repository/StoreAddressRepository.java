package com.example.springmvcrest.store.repository;

import com.example.springmvcrest.store.domain.StoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAddressRepository extends JpaRepository<StoreAddress,Long> {
}
