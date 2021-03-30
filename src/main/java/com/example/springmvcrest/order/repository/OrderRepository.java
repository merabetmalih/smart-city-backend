package com.example.springmvcrest.order.repository;

import com.example.springmvcrest.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
