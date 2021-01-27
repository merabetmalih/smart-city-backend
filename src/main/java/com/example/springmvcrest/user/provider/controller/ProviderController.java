package com.example.springmvcrest.user.provider.controller;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.provider.service.ProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/provider")
public class ProviderController {
    private final ProviderService providerService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserRegestrationDto registerUser(@ModelAttribute UserDto userDto) {
        return providerService.saveUser(userDto);
    }
}
