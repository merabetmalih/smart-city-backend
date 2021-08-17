package com.example.springmvcrest.offer.controller;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.api.dto.OfferDto;
import com.example.springmvcrest.offer.domain.OfferState;
import com.example.springmvcrest.offer.service.OfferService;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.utils.Response;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/offer")
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String> createOffer(@RequestBody OfferCreationDto offerCreationDto){
        return offerService.createOffer(offerCreationDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Response<String> deleteOffer(@PathVariable Long id){
        return offerService.deleteOffer(id);
    }

    @PutMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> updateOffer(@RequestBody OfferCreationDto offerCreationDto){
        return offerService.updateOffer(offerCreationDto);
    }

    @GetMapping("/current-provider-offers")
    @ResponseStatus(HttpStatus.OK)
    public Results<OfferDto> getOrdersByProviderId(@RequestParam("id") Long id,
                                                   @RequestParam(value = "status", required = false) OfferState status){
        return new Results<>(offerService.getOffersByProviderId(id,status));
    }

    @GetMapping("/search-offer")
    @ResponseStatus(HttpStatus.OK)
    public Results<ProductDTO> searchOfferByPosition(@RequestParam(name = "latitude") Double latitude,
                                                     @RequestParam(name = "longitude") Double longitude){
        return new Results<>(offerService.searchOfferByPosition(latitude,longitude));
    }
}
