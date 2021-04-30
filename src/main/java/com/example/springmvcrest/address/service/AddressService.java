package com.example.springmvcrest.address.service;

import com.example.springmvcrest.address.api.AddressDto;
import com.example.springmvcrest.address.api.AddressMapper;
import com.example.springmvcrest.address.domain.Address;
import com.example.springmvcrest.address.repository.AddressRepository;

import com.example.springmvcrest.utils.Errorhandler.AddressException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public Response<String> createAddress(AddressDto addressDto){
         Optional.of(addressDto)
                .map(addressMapper::toModel)
                .map(addressRepository::save)
                .orElseThrow(() -> new AddressException("error.address.creation"));
        return new Response<>("created.");
    }

    @Transactional
    public Response<String> deleteAddress(Long addressId){
        Address address=addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("error.address.notFound"));
        addressRepository.delete(address);
        return new Response<>("deleted.");
    }
}
