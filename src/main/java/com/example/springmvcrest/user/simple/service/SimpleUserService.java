package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.api.mapper.UserMapper;
import com.example.springmvcrest.user.api.mapper.UserRegestrationMapper;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.SimpleUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService {
    private final SimpleUserRepository simpleUserRepository;
    private final UserMapper userMapper;
    private final UserRegestrationMapper userRegestrationMapper;

    public Optional<SimpleUser> findSimpleUserByEmail(String email) {
        return simpleUserRepository.findByEmail(email);
    }

    public List<SimpleUser> findAllSimpleUsers() {
        return simpleUserRepository.findAll();
    }

    public Boolean isPresentSimpleUserByEmail(String email)
    {
        return simpleUserRepository.findByEmail(email).isPresent();
    }

    public UserRegestrationDto saveUser(UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toModel)
                .filter(user -> !isPresentSimpleUserByEmail(user.getEmail()))
                .map(simpleUserRepository::save)
                .map(userMapper::ToDto)
                .map(userRegestrationMapper::ToRegestrationDto)
                .orElse(null);
    }
}
