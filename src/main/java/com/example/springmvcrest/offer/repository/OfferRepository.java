package com.example.springmvcrest.offer.repository;

import com.example.springmvcrest.offer.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer,Long> {
}
