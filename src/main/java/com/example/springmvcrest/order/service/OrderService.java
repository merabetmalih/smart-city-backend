package com.example.springmvcrest.order.service;

import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.order.domain.OrderProductVariant;
import com.example.springmvcrest.order.domain.OrderProductVariantId;
import com.example.springmvcrest.order.repository.OrderProductVariantRepository;
import com.example.springmvcrest.order.repository.OrderRepository;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.simple.domain.Cart;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import com.example.springmvcrest.user.simple.service.CartService;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    OrderRepository orderRepository;
    OrderProductVariantRepository orderProductVariantRepository;
    CartService cartService;
    SimpleUserService simpleUserService;

    @Transactional
    public Response<String> createOrder(Long userId){
        Map<Store, List<CartProductVariant>> cartByProvider = getCartByProvider(cartService.findCartByUserId(userId));
        List<Order> orders = cartByProvider.keySet()
                .stream()
                .map(this::setStoreOrder)
                .map(order -> setOrderUser(order, userId))
                .map(this::setCreatAt)
                .map(orderRepository::save)
                .map(order -> setOrderProductVariantByStore(order, cartByProvider.get(order.getStore())))
                .map(orderRepository::save)
                .collect(Collectors.toList());
        return new Response<>("created.");
    }

    private Order setOrderProductVariantByStore(Order order, List<CartProductVariant> cartProductVariants){
        Set<OrderProductVariant> orderProductVariants = cartProductVariants.stream()
                .map(variant -> initOrderProductVariant(order, variant))
                .map(orderProductVariantRepository::save)
                .collect(Collectors.toSet());
        order.setCartProductVariants(orderProductVariants);
        return order;
    }

    private OrderProductVariant initOrderProductVariant(Order order,CartProductVariant cartProductVariant){
        return OrderProductVariant.builder()
                .quantity(cartProductVariant.getUnit())
                .order(order)
                .orderProductVariant(cartProductVariant.getCartProductVariant())
                .id(OrderProductVariantId.builder()
                        .orderProductVariantId(cartProductVariant.getCartProductVariant().getId())
                        .orderId(order.getId()).build()
                ).build();
    }

    private Order setCreatAt(Order order){
        order.setCreateAt(LocalDateTime.now());
        return order;
    }
    private Order setStoreOrder(Store store){
        return Order.builder()
                .store(store)
                .build();
    }
    private Order setOrderUser(Order order, Long userId){
         order.setUser(simpleUserService.findById(userId));
         return order;
    }

    private Map<Store,List<CartProductVariant>>getCartByProvider(Cart cart){
        Map<Store,List<CartProductVariant>> map=new HashMap<>();
        for (CartProductVariant cartProductVariant :cart.getCartProductVariants()) {
            Store store=cartProductVariant.getCartProductVariant().getProduct().getCustomCategory().getStore();
            if(map.containsKey(store)){
                map.get(store).add(cartProductVariant);
            }else {
                map.put(store,new ArrayList<>(Collections.singletonList(cartProductVariant)));
            }
        }
        return map;
    }
}
