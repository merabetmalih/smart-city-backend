package com.example.springmvcrest.order.repository;

import com.example.springmvcrest.order.domain.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByStore_Provider_Id(Long id);
}
