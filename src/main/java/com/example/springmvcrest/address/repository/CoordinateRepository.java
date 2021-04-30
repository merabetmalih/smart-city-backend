package com.example.springmvcrest.address.repository;

import com.example.springmvcrest.address.domain.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinateRepository extends JpaRepository<Coordinate,Long> {
}
