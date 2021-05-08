package com.example.springmvcrest.order.service;

import com.example.springmvcrest.bill.api.BillTotalDto;
import com.example.springmvcrest.bill.doamin.Bill;
import com.example.springmvcrest.bill.service.BillService;
import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.order.api.dto.OrderCreationDto;
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
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.OrderException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.springmvcrest.order.domain.OrderType.DELIVERY;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductVariantRepository orderProductVariantRepository;
    private final CartService cartService;
    private final OrderMapper orderMapper;
    private final NotificationService notificationService;
    private final BillService billService;

    public List<OrderDto> getOrderByUserId(Long id){
        return orderRepository.findByUser_Id(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrderByProviderId(Long id,String dateFilter,String amountFilter){
        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);
        return orderRepository.findByStore_Provider_Id(id,sort)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getTodayOrdersByProviderId(Long id,String dateFilter,String amountFilter){
        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);
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

    public List<OrderDto> getBetweenOrdersByCreatAtByProviderId(Long id,String startDate, String endDate,String dateFilter,String amountFilter){
        if(!DateUtil.isValidDate(startDate) || !DateUtil.isValidDate(endDate)){
            throw new DateException("error.date.invalid");
        }
        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);
        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);
        return orderRepository.findByStore_Provider_IdAndCreateAtBetween(id,startOfDate,endOfDate,sort)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    private Sort.Direction getSortDirection (String sortingOrder){
        if(sortingOrder.equals("ASC")){
            return Sort.Direction.ASC;
        }
        else {
            if (sortingOrder.equals("DESC")){
                return Sort.Direction.DESC;
            }else {
                throw new OrderException("error.sort.invalid");
            }
        }
    }

    private Sort sortOrdersByProperties(String dateFilter, String amountFilter){
        return Sort.by(Arrays.asList(
                new Sort.Order(getSortDirection(dateFilter),"createAt"),
                new Sort.Order(getSortDirection(amountFilter),"bill.total"))
        );
    }

    @Transactional
    public Response<String> createOrder(OrderCreationDto orderCreationDto){
        List<CartProductVariant> cartProductVariants = orderCreationDto.getCartProductVariantIds().stream()
                .map(cartService::findCartProductVariantById)
                .collect(Collectors.toList());
        System.out.println(orderMapper.toModel(orderCreationDto).getAddress().getId());
        Optional.of(orderCreationDto)
                .map(orderMapper::toModel)
                .map(this::setCreatAt)
                .map(order->checkValidOrder(order,cartProductVariants))
                .map(orderRepository::save)
                .map(order -> setOrderProductVariantByStore(order, cartProductVariants))
                .map(orderRepository::save)
                .map(this::sendStoreNotification);

        deleteCartProductVariant(cartProductVariants);
        return new Response<>("created.");
    }

    private Order checkValidOrder(Order order,List<CartProductVariant> cartProductVariants){
        if (cartProductVariants.isEmpty() || order.getOrderType()==null ){
            throw new OrderException("error.order.invalid");
        }

        //todo check if delivry addrese is set
        if(order.getOrderType().equals(DELIVERY)){
            if(!order.getStore().getPolicies().getDelivery()){
                throw new OrderException("error.order.invalid");
            }
        }
        return order;
    }

    private void deleteCartProductVariant(List<CartProductVariant> cartProductVariants){
        cartProductVariants.stream()
                .map(cartService::deleteCartProductVariant)
                .collect(Collectors.toList());
    }

    private List<CartProductVariant>  getProductByStore(Order order){
        return order.getUser().getCart().getCartProductVariants().stream()
                .filter(cartProductVariant -> cartProductVariant.getCartProductVariant().getProduct().getCustomCategory().getStore().getId() == order.getStore().getId())
                .collect(Collectors.toList());
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
        order.setBill(setOrderBill(order,cartProductVariants));
        return order;
    }

    private Bill setOrderBill(Order order, List<CartProductVariant> cartProductVariants){
        Bill bill=Bill.builder()
                .total(orderTotal(cartProductVariants))
                .order(order)
                .store(order.getStore())
                .createdAt(LocalDateTime.now())
                .alreadyPaid(
                        billService.getTotalToPay(
                                BillTotalDto.builder()
                                        .total(orderTotal(cartProductVariants))
                                        .orderType(order.getOrderType())
                                        .policyId(order.getStore().getPolicies().getId())
                                        .build()
                        ).getTotal()
                )
                .build();
        return billService.saveBill(bill);
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
