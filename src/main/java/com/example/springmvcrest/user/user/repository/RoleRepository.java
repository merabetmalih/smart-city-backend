package com.example.springmvcrest.user.user.repository;

import com.example.springmvcrest.user.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
