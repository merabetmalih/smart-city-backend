package com.example.springmvcrest.order.service;

import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.api.mapper.OrderMapper;
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
    private final OrderRepository orderRepository;
    private final OrderProductVariantRepository orderProductVariantRepository;
    private final CartService cartService;
    private final SimpleUserService simpleUserService;
    private final OrderMapper orderMapper;
    private final NotificationService notificationService;


    public List<OrderDto> getOrderByUserId(Long id){
        return orderRepository.findByUser_Id(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrderByProviderId(Long id){
        return orderRepository.findByStore_Provider_Id(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Response<String> createOrder(Long userId){
        Cart cart=cartService.findCartByUserId(userId);
        Map<Store, List<CartProductVariant>> cartByProvider = getCartByProvider(cart);
        List<Order> orders = cartByProvider.keySet()
                .stream()
                .map(this::setStoreOrder)
                .map(order -> setOrderUser(order, userId))
                .map(this::setCreatAt)
                .map(orderRepository::save)
                .map(order -> setOrderProductVariantByStore(order, cartByProvider.get(order.getStore())))
                .map(orderRepository::save)
                .map(this::sendStoreNotification)
                .collect(Collectors.toList());
        cartService.deleteCart(cart);
        return new Response<>("created.");
    }

    private Order sendStoreNotification(Order order){
        notificationService.sendNotification(
                Notification.builder()
                        .title("New order")
                        .message("New order arrived, check it!")
                        .topic(order.getStore().getProvider().getEmail().replace("@",""))
                        .build()
        );
        return order;
    }

    private Order setOrderProductVariantByStore(Order order, List<CartProductVariant> cartProductVariants){
        Set<OrderProductVariant> orderProductVariants = cartProductVariants.stream()
                .map(variant -> initOrderProductVariant(order, variant))
                .map(orderProductVariantRepository::save)
                .collect(Collectors.toSet());
        order.setOrderProductVariants(orderProductVariants);
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
