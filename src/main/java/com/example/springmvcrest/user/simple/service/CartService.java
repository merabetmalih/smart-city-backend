package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.repository.ProductVariantRepository;
import com.example.springmvcrest.product.service.ProductVariantService;
import com.example.springmvcrest.store.service.exception.CustomCategoryNotFoundExeption;
import com.example.springmvcrest.user.simple.domain.Cart;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import com.example.springmvcrest.user.simple.domain.CartProductVariantId;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.CartProductVariantRepository;
import com.example.springmvcrest.user.simple.repository.CartRepository;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final SimpleUserService simpleUserService;
    private final ProductVariantService productVariantService;
    private final CartRepository cartRepository;
    private final CartProductVariantRepository cartProductVariantRepository;

    public Response<String> addProductCart(Long userId, Long variantId, Integer quantity){
        Optional.of(userId)
                .map(simpleUserService::findById)
                .map(this::setUserCart)
                .map(cart -> createCart(cart, variantId, quantity))
                .map(cartRepository::save);
        return new Response<>("created.");
    }

    public Response<String> deleteProductCart(Long userId, Long variantId){
        CartProductVariant cartProductVariant = Optional.of(userId)
                .map(simpleUserService::findById)
                .map(this::getCartId)
                .map(cartId -> getCartProductVariant(cartId, variantId))
                .orElseThrow(CustomCategoryNotFoundExeption::new);

        cartProductVariantRepository.delete(cartProductVariant);
        return new Response<>("deleted.");
    }

    private CartProductVariant getCartProductVariant(Long cartId,Long variantId){
        return cartProductVariantRepository.findById(
                CartProductVariantId.builder()
                        .cartId(cartId)
                        .cartProductVariantId(variantId)
                        .build()
        ).orElse(null);
    }

    private Long getCartId(SimpleUser user){
        return Optional.of(user)
                .filter(user1 ->user1.getCart()!=null)
                .map(user1 -> user1.getCart().getId())
                .orElse(null);
    }

    private Cart setUserCart(SimpleUser user){
        return  Optional.of(user)
                .filter(user1 ->user1.getCart()==null)
                .map(this::initCart)
                .map(cartRepository::save)
                .orElse(user.getCart());
    }

    private Cart initCart(SimpleUser user){
        return Cart.builder()
                .simpleUser(user)
                .cartProductVariants(new HashSet<>())
                .build();
    }

    private Cart createCart(Cart cart,Long variantId,Integer quantity){
        CartProductVariant cartProductVariant=CartProductVariant.builder()
                .id(
                        CartProductVariantId.builder()
                                .cartId(cart.getId())
                                .cartProductVariantId(variantId)
                                .build()
                )
                .cart(cart)
                .cartProductVariant(productVariantService.findById(variantId))
                .unit(quantity)
                .build();
        cart.getCartProductVariants().add(cartProductVariantRepository.save(cartProductVariant));
        return cart;
    }
}
