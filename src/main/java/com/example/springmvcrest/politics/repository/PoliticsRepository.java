package com.example.springmvcrest.politics.repository;

import com.example.springmvcrest.politics.domain.Politics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PoliticsRepository extends JpaRepository<Politics,Long> {

    Optional<Politics> findByStoreId(Long id);


    Optional<Politics> findByStore_Provider_Id(Long id);
}
