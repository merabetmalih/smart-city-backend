package com.example.springmvcrest.flashDeal.controller;

import com.example.springmvcrest.flashDeal.api.dto.FlashDealCreationDto;
import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.flashDeal.service.FlashDealService;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/flashDeal")
public class FlashDealController {
    private final FlashDealService flashDealService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> addProductToCart(@RequestBody FlashDealCreationDto flashDealCreationDto ) {
        return flashDealService.createFlashDeal(flashDealCreationDto);
    }

    @GetMapping("/current-provider-flash/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<FlashDealDto> addProductToCart(@PathVariable(value = "id") Long id ) {
        return new Results<>(flashDealService.getFlashDealsStore(id));
    }
}
