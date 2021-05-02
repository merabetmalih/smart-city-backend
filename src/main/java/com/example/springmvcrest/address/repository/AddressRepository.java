package com.example.springmvcrest.address.repository;

import com.example.springmvcrest.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByUser_Id(Long id);
}
