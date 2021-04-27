package com.example.springmvcrest.policy.repository;

import com.example.springmvcrest.policy.domain.Policies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliciesRepository extends JpaRepository<Policies,Long> {

    Optional<Policies> findByStoreId(Long id);


    Optional<Policies> findByStore_Provider_Id(Long id);
}
