package com.example.springmvcrest.politics.repository;

import com.example.springmvcrest.politics.domain.TaxRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRangeRepository extends JpaRepository<TaxRange,Long> {
}
