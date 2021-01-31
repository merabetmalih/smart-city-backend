package com.example.springmvcrest.user.provider.service;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.api.mapper.ProviderMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.provider.domain.Provider;
import com.example.springmvcrest.user.provider.repository.ProviderRepository;
import com.example.springmvcrest.user.provider.service.exception.ProviderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;
    private final UserRegestrationMapper userRegestrationMapper;


    public Provider findById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(ProviderNotFoundException::new);
    }
    public Optional<Provider> findByIdOptional(Long id) {
        return providerRepository.findById(id);
    }

    public Optional<Provider> findProviderByEmail(String email) {
        return providerRepository.findByEmail(email);
    }

    public List<Provider> findAllProviders() {
        return providerRepository.findAll();
    }

    public Boolean isPresentProviderByEmail(String email)
    {
        return providerRepository.findByEmail(email).isPresent();
    }

    public UserRegestrationDto saveUser(UserDto userDto) {
        return Optional.of(userDto)
                .map(providerMapper::toModel)
                .filter(user -> !isPresentProviderByEmail(user.getEmail()))
                .map(providerRepository::save)
                .map(providerMapper::ToDto)
                .map(userRegestrationMapper::ToRegestrationDto)
                .orElse(null);
    }
}
