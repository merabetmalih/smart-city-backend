package com.example.springmvcrest.flashDeal.repository;

import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlashDealRepository extends JpaRepository<FlashDeal,Long> {

    List<FlashDeal> findByStore_Provider_Id(Long id);
    List<FlashDeal> findByStore_Provider_IdAndCreateAtBetween(Long id,LocalDateTime start, LocalDateTime end);
}
