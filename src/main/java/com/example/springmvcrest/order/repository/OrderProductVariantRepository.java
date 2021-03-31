package com.example.springmvcrest.order.repository;

import com.example.springmvcrest.order.domain.OrderProductVariant;
import com.example.springmvcrest.order.domain.OrderProductVariantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductVariantRepository extends JpaRepository<OrderProductVariant, OrderProductVariantId> {
}
