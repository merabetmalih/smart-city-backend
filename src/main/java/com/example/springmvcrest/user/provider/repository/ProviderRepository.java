package com.example.springmvcrest.user.provider.repository;

import com.example.springmvcrest.user.provider.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider,Long> {
    Optional<Provider> findByEmail(String email);

}
