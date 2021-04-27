package com.example.springmvcrest.policy.repository;

import com.example.springmvcrest.policy.domain.TaxRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRangeRepository extends JpaRepository<TaxRange,Long> {
}
