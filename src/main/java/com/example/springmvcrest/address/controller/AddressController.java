package com.example.springmvcrest.address.controller;

import com.example.springmvcrest.address.api.AddressDto;
import com.example.springmvcrest.address.service.AddressService;
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
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> addAddressToUser(@RequestBody AddressDto addressDto ) {
        return addressService.createAddress(addressDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> deleteAddressToUser(@PathVariable(name = "id") Long addressId) {
        return  addressService.deleteAddress(addressId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Results<AddressDto> getUserAddress(@PathVariable(name = "id") Long userId) {
        return  new Results<>(addressService.getUserAddress(userId));
    }
}
