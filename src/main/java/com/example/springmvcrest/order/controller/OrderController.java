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

    @GetMapping("current-provider-orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByProviderId(@PathVariable("id") long id ) {
        return new Results<>(orderService.getOrderByProviderId(id));
    }

    @GetMapping("current-provider-today-orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getTodayOrdersByProviderId(@PathVariable("id") long id ) {
        return new Results<>(orderService.getTodayOrdersByProviderId(id));
    }

    @GetMapping("current-provider-filter-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> filterOrdersByCreatAtByProviderId(@RequestParam(name = "id") Long id,
                                                               @RequestParam(name = "startDate") String startDate,
                                                               @RequestParam(name = "endDate")String endDate) {
        return new Results<>(orderService.filterOrdersByCreatAtByProviderId(id,startDate,endDate));
    }

    @GetMapping("current-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByUserId(@PathVariable("id") long id ) {
        return new Results<>(orderService.getOrderByUserId(id));
    }

}
