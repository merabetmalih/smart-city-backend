package com.example.springmvcrest.order.controller;

import com.example.springmvcrest.order.service.OrderService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("create/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> addProductToCart(@PathVariable("id") long userId ) {
         return orderService.createOrder(userId);
    }
}
