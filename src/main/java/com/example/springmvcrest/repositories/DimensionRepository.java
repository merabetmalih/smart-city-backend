package com.example.springmvcrest.repositories;

import com.example.springmvcrest.domain.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimensionRepository extends JpaRepository<Dimension,Long> {
}
