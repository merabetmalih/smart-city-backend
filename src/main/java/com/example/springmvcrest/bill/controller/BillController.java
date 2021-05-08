package com.example.springmvcrest.bill.controller;

import com.example.springmvcrest.bill.api.BillTotalDto;
import com.example.springmvcrest.bill.service.BillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;


    @PostMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public BillTotalDto getTotalToPay(@RequestBody BillTotalDto billTotalDto) {
        return billService.getTotalToPay(billTotalDto);
    }
}
