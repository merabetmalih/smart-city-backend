package com.example.springmvcrest.user.simple.controller;

import com.example.springmvcrest.user.simple.api.dto.CartDto;
import com.example.springmvcrest.user.simple.service.CartService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> addProductToCart(@RequestParam(name = "userId") Long userId,
                                             @RequestParam(name = "variantId") Long variantId,
                                             @RequestParam(name = "quantity")Integer quantity ) {
        return  cartService.addProductCart(userId,variantId,quantity);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public Response<String> deleteProductFromCart(@RequestParam(name = "userId") Long userId,
                                                  @RequestParam(name = "variantId") Long variantId) {
        return  cartService.deleteProductCart(userId,variantId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto deleteProductFromCart(@PathVariable Long userId) {
        return  cartService.getUserCart(userId);
    }
}
