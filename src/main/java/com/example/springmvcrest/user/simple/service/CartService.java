package com.example.springmvcrest.user.simple.service;

import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.service.ProductVariantService;
import com.example.springmvcrest.user.simple.api.dto.CartDto;
import com.example.springmvcrest.user.simple.api.mapper.CartMapper;
import com.example.springmvcrest.user.simple.domain.Cart;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import com.example.springmvcrest.user.simple.domain.CartProductVariantId;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.repository.CartProductVariantRepository;
import com.example.springmvcrest.user.simple.repository.CartRepository;
import com.example.springmvcrest.utils.Errorhandler.CartException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final SimpleUserService simpleUserService;
    private final ProductVariantService productVariantService;
    private final CartRepository cartRepository;
    private final CartProductVariantRepository cartProductVariantRepository;
    private final CartMapper cartMapper;


    @Transactional
    public Response<String> addProductCart(Long userId, Long variantId, Integer quantity){
        Optional.of(userId)
                .map(simpleUserService::findById)
                .map(this::setUserCart)
                .map(cart -> createCart(cart, variantId, quantity))
                .map(cartRepository::save);
        return new Response<>("created.");
    }

    @Transactional
    public Response<String> deleteProductCart(Long userId, Long variantId){
        CartProductVariant cartProductVariant = Optional.of(userId)
                .map(simpleUserService::findById)
                .map(this::getCartId)
                .map(cartId -> getCartProductVariant(cartId, variantId))
                .get();
        cartProductVariantRepository.delete(cartProductVariant);
        return new Response<>("deleted.");
    }

    public CartDto getUserCart(Long userId){
        return Optional.of(userId)
                .map(simpleUserService::findById)
                .map(SimpleUser::getCart)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new CartException("error.cart.userCart"));
    }

    private CartProductVariant getCartProductVariant(Long cartId,Long variantId){
        return cartProductVariantRepository.findById(
                CartProductVariantId.builder()
                        .cartId(cartId)
                        .cartProductVariantId(variantId)
                        .build()
        ).orElseThrow(() -> new CartException("error.cartProductVariant.notfound"));
    }

    private Long getCartId(SimpleUser user){
        return Optional.of(user)
                .filter(user1 ->user1.getCart()!=null)
                .map(user1 -> user1.getCart().getId())
                .get();
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
                .cartProductVariant(
                        checkQuantity(
                                productVariantService.findById(variantId),
                                quantity)
                        )
                .unit(quantity)
                .build();
        cart.getCartProductVariants().add(cartProductVariantRepository.save(cartProductVariant));
        return cart;
    }

    private ProductVariant checkQuantity(ProductVariant productVariant,Integer quantity){
        return Optional.of(productVariant)
                .filter(variant -> quantity <= variant.getUnit())
                .orElseThrow(() -> new CartException("error.cart.quantity not available"));
    }

    public Cart findCartByUserId(Long id){
        return cartRepository.findCartBySimpleUser_Id(id)
                .orElseThrow(() -> new CartException("error.cart.notfound"));
    }

    @Transactional
    public void deleteCart(Cart cart){
        cartRepository.delete(cart);
    }
}
