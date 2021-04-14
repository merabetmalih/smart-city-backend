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
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public List<OrderDto> getOrderByProviderId(Long id,String createAtFilter,String totalFilter){
        Sort sort=sortOrdersByPropertiesList(createAtFilter,totalFilter);
        return orderRepository.findByStore_Provider_Id(id,sort)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getTodayOrdersByProviderId(Long id,String createAtFilter,String totalFilter){
        Sort sort=sortOrdersByPropertiesList(createAtFilter,totalFilter);
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime startOfDate = today
                .toLocalDate().atTime(LocalTime.MIDNIGHT);

        LocalDateTime endOfDate = today
                .toLocalDate().atTime(LocalTime.MAX);

        return orderRepository.findByStore_Provider_IdAndCreateAtBetween(id,startOfDate,endOfDate,sort)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getBetweenOrdersByCreatAtByProviderId(Long id,String startDate, String endDate,String createAtFilter,String totalFilter){
        if(!DateUtil.isValidDate(startDate) || !DateUtil.isValidDate(endDate)){
            throw new DateException("error.date.invalid");
        }
        Sort sort=sortOrdersByPropertiesList(createAtFilter,totalFilter);
        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);
        return orderRepository.findByStore_Provider_IdAndCreateAtBetween(id,startOfDate,endOfDate,sort)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    private Sort.Direction getSortDirection (String order){
        if(order.equals("ASC")){
            return Sort.Direction.ASC;
        }
        else {
            if (order.equals("DESC")){
                return Sort.Direction.DESC;
            }else {
                throw new DateException("error.sort.invalid");
            }
        }
    }

    private Sort sortOrdersByPropertiesList(String createAtFilter,String totalFilter){
        return Sort.by(Arrays.asList(
                new Sort.Order(getSortDirection(createAtFilter),"createAt"),
                new Sort.Order(getSortDirection(totalFilter),"total"))
        );
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
        order.setTotal(orderTotal(cartProductVariants));
        return order;
    }

    private Double orderTotal(List<CartProductVariant> cartProductVariants){
        return cartProductVariants.stream()
                .map(cartProductVariant-> cartProductVariant.getUnit()*cartProductVariant.getCartProductVariant().getPrice())
        .mapToDouble(Double::doubleValue).sum();
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
