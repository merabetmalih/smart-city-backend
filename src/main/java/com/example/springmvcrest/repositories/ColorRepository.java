package com.example.springmvcrest.repositories;

import com.example.springmvcrest.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color,Long> {
}
