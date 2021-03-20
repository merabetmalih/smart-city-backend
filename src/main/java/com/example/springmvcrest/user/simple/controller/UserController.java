package com.example.springmvcrest.user.simple.controller;


import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.service.ProductSearchService;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.simple.api.dto.SimpleUserDto;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
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
@RequestMapping("/api/user")
public class UserController {
    private final SimpleUserService simpleUserService;
    private final ProductSearchService searchService;

    @GetMapping
    List<SimpleUser> getAllUsers() {
        return simpleUserService.findAllSimpleUsers();
    }

   /* @GetMapping("/categories/{id}")
    public Results userCategoriesProductList(@PathVariable Long id) {
        return new Results(simpleUserService.userCategoriesList(id));
    }*/

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserRegestrationDto registerUser(@ModelAttribute UserDto userDto) {
        return simpleUserService.saveUser(userDto);
    }


    @PostMapping("/interestCenter")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<String>  setUserInterestCenter(@ModelAttribute SimpleUserDto simpleUserDto){
       return simpleUserService.setUserInterestCenter(simpleUserDto);
    }

    @GetMapping("/interestCenter/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Results<CategoryDto> getUserInterestCenter(@PathVariable Long id){
        return new Results<>(simpleUserService.getUserInterestCenter(id));
    }

}
