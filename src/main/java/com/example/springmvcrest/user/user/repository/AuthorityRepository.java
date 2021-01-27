package com.example.springmvcrest.user.user.repository;

import com.example.springmvcrest.user.user.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
