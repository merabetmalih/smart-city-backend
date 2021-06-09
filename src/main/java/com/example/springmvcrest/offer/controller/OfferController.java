package com.example.springmvcrest.offer.controller;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.service.OfferService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/Offer")
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String> createOffer(@ModelAttribute OfferCreationDto offerCreationDto){
        return offerService.createOffer(offerCreationDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Response<String> deleteOffer(@PathVariable Long id){
        return offerService.deleteOffer(id);
    }

    @PutMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> updateOffer(@ModelAttribute OfferCreationDto offerCreationDto){
        return offerService.updateOffer(offerCreationDto);
    }
}
