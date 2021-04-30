package com.example.springmvcrest.address.repository;

import com.example.springmvcrest.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
