package com.example.springmvcrest.bill.controller;

import com.example.springmvcrest.bill.api.BillDto;
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


    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public BillDto getTotalToPay(
            @RequestParam(name = "id") Long policyId,
            @RequestParam(name = "total") Double total) {
        return billService.getTotalToPay(policyId,total);
    }
}
