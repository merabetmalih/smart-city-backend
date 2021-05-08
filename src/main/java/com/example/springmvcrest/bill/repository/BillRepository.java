package com.example.springmvcrest.bill.repository;

import com.example.springmvcrest.bill.doamin.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Long> {
}
