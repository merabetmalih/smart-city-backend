package com.example.springmvcrest.order.controller;

import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.service.OrderService;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("current-provider-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByProviderId(
            @RequestParam("id") long id,
            @RequestParam(name = "createAt",defaultValue = "ASC",required = false) String createAtFilter,
            @RequestParam(name = "total",defaultValue = "ASC",required = false) String totalFilter) {
        return new Results<>(orderService.getOrderByProviderId(id,createAtFilter,totalFilter));
    }

    @GetMapping("current-provider-today-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getTodayOrdersByProviderId(
            @RequestParam("id") long id,
            @RequestParam(name = "createAt",defaultValue = "ASC",required = false) String createAtFilter,
            @RequestParam(name = "total",defaultValue = "ASC",required = false) String totalFilter) {
        return new Results<>(orderService.getTodayOrdersByProviderId(id,createAtFilter,totalFilter));
    }

    @GetMapping("current-provider-dates-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> filterOrdersByCreatAtByProviderId(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate")String endDate,
            @RequestParam(name = "createAt",defaultValue = "ASC",required = false) String createAtFilter,
            @RequestParam(name = "total",defaultValue = "ASC",required = false) String totalFilter) {
        return new Results<>(orderService.getBetweenOrdersByCreatAtByProviderId(id,startDate,endDate,createAtFilter,totalFilter));
    }

    @GetMapping("current-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByUserId(@PathVariable("id") long id ) {
        return new Results<>(orderService.getOrderByUserId(id));
    }

}
