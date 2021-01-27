package com.example.springmvcrest.user.simple.controller;


import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.service.ProductSearchService;
import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping(value = "/image/{id}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable String id) throws IOException {
        final InputStream in = getClass().getResourceAsStream("/static/" + id + ".jpg");
        return IOUtils.toByteArray(in);
    }

    @RequestMapping("/product/new")
    public ModelAndView newProduct() {
        ModelAndView modelAndView = new ModelAndView("product/productform");
        modelAndView.addObject("product", new ProductDTO());
        return modelAndView;
    }

}
