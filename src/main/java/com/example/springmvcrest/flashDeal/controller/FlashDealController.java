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
    public Response<String> createFlashDeal(@RequestBody FlashDealCreationDto flashDealCreationDto ) {
        return flashDealService.createFlashDeal(flashDealCreationDto);
    }

    @GetMapping("/current-provider-flash/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<FlashDealDto> getRecentFlashDealsStore(@PathVariable(value = "id") Long id ) {
        return new Results<>(flashDealService.getRecentFlashDealsStore(id));
    }

    @GetMapping("/current-provider-search-flash")
    @ResponseStatus(HttpStatus.OK)
    public Results<FlashDealDto> searchFlashDealsStore(@RequestParam(name = "id") Long id,
                                                       @RequestParam(name = "startDate") String startDate,
                                                       @RequestParam(name = "endDate")String endDate) {
        return new Results<>(flashDealService.searchFlashDealsStore(id,startDate,endDate));
    }

    @GetMapping("/search-flash")
    @ResponseStatus(HttpStatus.OK)
    public Results<FlashDealDto> searchFlashByPosition(@RequestParam(name = "latitude") Double latitude,
                                                       @RequestParam(name = "longitude") Double longitude,
                                                       @RequestParam(value = "date") String date) {
        return new Results<>(flashDealService.searchFlashByPosition(latitude,longitude,date));
    }
}
