package com.example.springmvcrest.controllers;


import com.example.springmvcrest.api.model.ProductDTO;
import com.example.springmvcrest.domain.Product;
import com.example.springmvcrest.domain.User;
import com.example.springmvcrest.domain.dto.UserDto;
import com.example.springmvcrest.domain.dto.UserLoginDto;
import com.example.springmvcrest.domain.dto.UserRegestrationDto;

import com.example.springmvcrest.services.ProductSearchService;

import com.example.springmvcrest.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public static final String BASE_URL = "/api/v1/user";

    private final UserService userService;

    private final ProductSearchService searchService;


    @GetMapping
    List<User> getAllUsers() {
        return userService.findAllUsers();
    }


    @GetMapping("/product")
    public Results searchProduct(@RequestParam(name = "search", required = false) String query) {

        if (query != null) {
            List<Product> result = searchService.search(query);
            Predicate<Product> byParentProductNull = product -> product.getParentProduct() == null;
            result = result.stream().filter(byParentProductNull).collect(Collectors.toList());
            return new Results(result);
        }
        return new Results(searchService.findAllProduct());
    }

    /*@GetMapping("/product")
    public Results AllProduct() {
        return new Results(searchService.findAllProduct());
    }*/
    //@Transactional(readOnly=true)
    @GetMapping("/categories/{id}")
    public Results userCategoriesProductList(@PathVariable Long id) {

        return new Results(userService.userCategoriesList(id));
    }


    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserRegestrationDto registerUser(@ModelAttribute UserDto userDto) {

        System.out.println("register");

        if (userDto.getPassWord1().equals(userDto.getPassWord2())) {

            User userNew = userService.saveUser(userDto);
            return new UserRegestrationDto(
                    userNew.getId(),
                    userNew.getEmail(),
                    userNew.getUserName(),
                    "successfully registered new user",
                    "token");
        }
        return null;


    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public UserLoginDto UserLoginDto(@ModelAttribute User user) {
        User userFound = userService.findByEmailAndPassWord(user.getEmail(), user.getPassWord1());

        if (userFound != null) {
            if (userFound.getId() != null) {
                //System.out.println("Customer user: "+user.toString());
                //System.out.println("Customer userFound: "+userFound.toString());
                return new UserLoginDto(
                        userFound.getId(),
                        userFound.getEmail(),
                        "successfully login");
            }
        }

        return null;
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

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createNewProduct(@ModelAttribute ProductDTO productDTO) {
        return new ResponseEntity<ProductDTO>(userService.createNewProduct(productDTO),
                HttpStatus.CREATED);
    }

}
