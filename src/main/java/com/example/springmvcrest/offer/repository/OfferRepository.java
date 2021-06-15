package com.example.springmvcrest.offer.repository;

import com.example.springmvcrest.offer.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findByStore_Provider_Id(Long id);
}
