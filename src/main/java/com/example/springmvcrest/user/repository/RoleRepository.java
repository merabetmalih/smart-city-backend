package com.example.springmvcrest.user.repository;

import com.example.springmvcrest.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
