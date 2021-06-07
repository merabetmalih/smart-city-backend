package com.example.springmvcrest.order.controller;

import com.example.springmvcrest.order.api.dto.OrderCreationDto;
import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.domain.OrderStep;
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


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> addProductToCart(@RequestBody OrderCreationDto orderCreationDto ) {
         return orderService.createOrder(orderCreationDto);
    }

    @GetMapping("/current-provider-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByProviderId(
            @RequestParam("id") long id,
            @RequestParam(name = "date",defaultValue = "DESC",required = false) String dateFilter,
            @RequestParam(name = "amount",defaultValue = "ASC",required = false) String amountFilter,
            @RequestParam(name = "step") OrderStep step) {
        return new Results<>(orderService.getOrderByProviderId(id,dateFilter,amountFilter,step));
    }

    @GetMapping("/current-provider-today-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getTodayOrdersByProviderId(
            @RequestParam("id") long id,
            @RequestParam(name = "date",defaultValue = "DESC",required = false) String dateFilter,
            @RequestParam(name = "amount",defaultValue = "ASC",required = false) String amountFilter,
            @RequestParam(name = "step") OrderStep step) {
        return new Results<>(orderService.getTodayOrdersByProviderId(id,dateFilter,amountFilter,step));
    }

    @GetMapping("/current-provider-dates-orders")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> filterOrdersByCreatAtByProviderId(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate")String endDate,
            @RequestParam(name = "date",defaultValue = "DESC",required = false) String dateFilter,
            @RequestParam(name = "amount",defaultValue = "ASC",required = false) String amountFilter,
            @RequestParam(name = "step") OrderStep step) {
        return new Results<>(orderService.getBetweenOrdersByCreatAtByProviderId(id,startDate,endDate,dateFilter,amountFilter,step));
    }

    @GetMapping("/current-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<OrderDto> getOrderByUserId(@PathVariable("id") long id ) {
        return new Results<>(orderService.getOrderByUserId(id));
    }

    @PutMapping("/current-store/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> acceptedOrderByStore(@PathVariable("id") Long id) {
        return orderService.acceptOrderByStore(id);
    }

    @PutMapping("/current-store/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> rejectOrderByStore(@PathVariable("id") Long id) {
        return orderService.rejectOrderByStore(id);
    }

    @PutMapping("/current-store/{id}/ready")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> readyOrderByStore(@PathVariable("id") Long id) {
        return orderService.readyOrderByStore(id);
    }

    @PutMapping("/current-store/{id}/delivered")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> deliveredOrderByStore(@PathVariable("id") Long id,
                                                  @RequestParam(name = "comment") String comment,
                                                  @RequestParam(name = "date") String date) {
        return orderService.deliveredOrderByStore(id,comment,date);
    }

    @PutMapping("/current-store/{id}/pickedUp")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> pickedUpOrderByStore(@PathVariable("id") Long id,
                                                 @RequestParam(name = "comment") String comment,
                                                 @RequestParam(name = "date") String date) {
        return orderService.pickedUpOrderByStore(id,comment,date);
    }

    @PutMapping("/current-user/{id}/delivered")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> deliveredOrderByStoreConfirmed(@PathVariable("id") Long id) {
        return orderService.deliveredOrderByStoreConfirmed(id);
    }

    @PutMapping("/current-user/{id}/pickedUp")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> pickedUpOrderByStoreConfirmed(@PathVariable("id") Long id) {
        return orderService.pickedUpOrderByStoreConfirmed(id);
    }

  /*  @PutMapping("/current-user/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> cancelOrderByUser(@PathVariable("id") Long id) {
        return orderService.cancelOrderByUser(id);
    }*/
}
