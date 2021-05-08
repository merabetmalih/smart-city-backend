package com.example.springmvcrest.bill.api;

import com.example.springmvcrest.bill.doamin.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillDto toDto(Bill bill);
}
