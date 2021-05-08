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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        address.setDeleted(true);
        addressRepository.save(address);
        return new Response<>("deleted.");
    }

    public List<AddressDto> getUserAddress(Long userId){
        return addressRepository.findByUser_Id(userId).stream()
                .filter(address -> !address.getDeleted())
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }
}
