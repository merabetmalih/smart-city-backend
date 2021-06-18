package com.example.springmvcrest.order.service;

import com.example.springmvcrest.bill.api.BillTotalDto;
import com.example.springmvcrest.bill.doamin.Bill;
import com.example.springmvcrest.bill.service.BillService;
import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.order.api.dto.OrderCreationDto;
import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.api.mapper.OrderMapper;
import com.example.springmvcrest.order.domain.*;
import com.example.springmvcrest.order.repository.OrderProductVariantRepository;
import com.example.springmvcrest.order.repository.OrderRepository;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.service.ProductVariantService;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.simple.domain.Cart;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import com.example.springmvcrest.user.simple.service.CartService;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.OrderException;
import com.example.springmvcrest.utils.Response;
import com.google.firebase.database.annotations.NotNull;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.springmvcrest.order.domain.OrderStep.*;
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
    private final ProductVariantService productVariantService;

    public List<OrderDto> getInProgressOrdersByUserId(Long id){
        return orderRepository.findByUser_Id(id).stream()
                .filter(order -> !order.getOrderState().isRejected() && !order.getOrderState().isReceived())
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getFinalizedOrdersByUserId(Long id){
        return orderRepository.findByUser_Id(id).stream()
                .filter(order -> order.getOrderState().isRejected() || order.getOrderState().isReceived())
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    private static Function<Order, Boolean> NewOrderQualifier= order -> {
        return order.getOrderState().isNewOrder();
    };

    private static Function<Order, Boolean> AcceptOrderQualifier= order -> {
        return order.getOrderState().isAccepted() &&
                !order.getOrderState().isReady()&&
                !order.getOrderState().isDelivered()&&
                !order.getOrderState().isPickedUp() &&
                !order.getOrderState().isReceived();
    };

    private static Function<Order, Boolean> ReadyOrderQualifier= order -> {
        return order.getOrderState().isAccepted() &&
                order.getOrderState().isReady() &&
                !order.getOrderState().isDelivered()&&
                !order.getOrderState().isPickedUp() &&
                !order.getOrderState().isReceived();
    };

    private static Function<Order, Boolean> ConfirmationOrderQualifier= order -> {
        return (order.getOrderState().isPickedUp() || order.getOrderState().isDelivered())&&
                order.getOrderState().isAccepted() &&
                order.getOrderState().isReady()&&
                order.getOrderState().isReceived();
    };

    private static Supplier<List<Pair<OrderStep,Function<Order, Boolean>>>> GetSteps= ()->{
        List<Pair<OrderStep,Function<Order, Boolean>>> rules=new ArrayList<>();
        rules.add(new Pair<>(NEW_ORDER,NewOrderQualifier));
        rules.add(new Pair<>(ACCEPT_ORDER,AcceptOrderQualifier));
        rules.add(new Pair<>(READY_ORDER,ReadyOrderQualifier));
        rules.add(new Pair<>(CONFIRMATION_ORDER,ConfirmationOrderQualifier));
        return rules;
    };

    private static Function<OrderStep,Function<List<Pair<OrderStep,Function<Order, Boolean>>>,//two inputs
            //output
             Pair<OrderStep, Function<Order, Boolean>>>> GetStepQualifier = orderStep ->(steps ->{
        return steps.stream()
                        .filter(step -> step.getKey().equals(orderStep))
                        .findFirst()
                        .orElseThrow(()-> new OrderException("error.order.step.notFound"));
    });

    public List<OrderDto> getOrderByProviderId(Long id,String dateFilter,String amountFilter,OrderStep step){
        Pair<OrderStep, Function<Order, Boolean>> stepQualifier = GetStepQualifier.apply(step).apply(GetSteps.get());

        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);

        return orderRepository.findByStore_Provider_Id(id,sort)
                .stream()
                .filter(order -> stepQualifier.getValue().apply(order))
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getTodayOrdersByProviderId(Long id,String dateFilter,String amountFilter,OrderStep step){
        Pair<OrderStep, Function<Order, Boolean>> stepQualifier = GetStepQualifier.apply(step).apply(GetSteps.get());

        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfDate = today
                .toLocalDate().atTime(LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = today
                .toLocalDate().atTime(LocalTime.MAX);
        return orderRepository.findByStore_Provider_IdAndCreateAtBetween(id,startOfDate,endOfDate,sort)
                .stream()
                .filter(order -> stepQualifier.getValue().apply(order))
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getBetweenOrdersByCreatAtByProviderId(Long id,String startDate, String endDate,String dateFilter,String amountFilter,OrderStep step){
        if(!DateUtil.isValidDate(startDate) || !DateUtil.isValidDate(endDate)){
            throw new DateException("error.date.invalid");
        }
        Pair<OrderStep, Function<Order, Boolean>> stepQualifier = GetStepQualifier.apply(step).apply(GetSteps.get());

        Sort sort= sortOrdersByProperties(dateFilter,amountFilter);
        LocalDateTime startOfDate = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);
        return orderRepository.findByStore_Provider_IdAndCreateAtBetween(id,startOfDate,endOfDate,sort)
                .stream()
                .filter(order -> stepQualifier.getValue().apply(order))
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
                new Sort.Order(getSortDirection(amountFilter),"bill.total")
                )
        );
    }

    @Transactional
    public Response<String> createOrder(OrderCreationDto orderCreationDto){
        List<CartProductVariant> cartProductVariants = orderCreationDto.getCartProductVariantIds().stream()
                .map(cartService::findCartProductVariantById)
                .collect(Collectors.toList());
        Optional.of(orderCreationDto)
                .map(orderMapper::toModel)
                .map(this::setCreatAt)
                .map(order->checkValidOrder(order,cartProductVariants))
                .map(this::setOrderState)
                .map(orderRepository::save)
                .map(order -> setOrderProductVariantByStore(order, cartProductVariants))
                .map(orderRepository::save)
                .map(this::sendStoreNotification);

        deleteCartProductVariant(cartProductVariants);
        return new Response<>("created.");
    }

    @NotNull
    private Order setOrderState(Order order) {
        order.setOrderState(OrderState.builder().newOrder(true).build());
        return order;
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
                        .topic("provider-"+order.getStore().getProvider().getEmail().replace("@",""))
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
                .map(cartProductVariant -> getVariantPrice(cartProductVariant.getCartProductVariant()) * cartProductVariant.getUnit())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private Double getVariantPrice(ProductVariant productVariant){
        Double price=0.0;
        Offer offer=productVariantService.getVariantOffer(productVariant.getOffers());
        if(offer!=null){
            switch (offer.getType()){
                case FIXED:{
                    price= productVariant.getPrice()-offer.getNewPrice();
                }

                case PERCENTAGE:{
                    price= productVariant.getPrice()-(productVariant.getPrice()*offer.getPercentage()/100);
                }
            }
        }else {
            price= productVariant.getPrice();
        }
        return price;
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


    public Order findOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()-> new OrderException("error.order.notFound"));
    }

    private Order sendUserNotificationAcceptedOrder(Order order){
        notificationService.sendNotification(
                Notification.builder()
                        .title("Accepted order")
                        .message("Your order is accepted by the store.")
                        .topic("user-"+order.getUser().getEmail().replace("@",""))
                        .build()
        );
        return order;
    }

    public Response<String> acceptOrderByStore(Long id){
        Optional.of(findOrderById(id))
                .map(this::setAccepted)
                .map(this::setNewOrder)
                .map(orderRepository::save)
                .map(this::sendUserNotificationAcceptedOrder);
        return new Response<>("created.");
    }

    public Response<String> rejectOrderByStore(Long id){
        Optional.of(findOrderById(id))
                .map(this::setRejected)
                .map(this::setNewOrder)
                .map(orderRepository::save);
        return new Response<>("created.");
    }

    private Order sendUserNotificationReadyOrder(Order order){
        notificationService.sendNotification(
                Notification.builder()
                        .title("Ready order")
                        .message("Your order is ready to picked up.")
                        .topic("user-"+order.getUser().getEmail().replace("@",""))
                        .build()
        );
        return order;
    }

    public Response<String> readyOrderByStore(Long id){
        Optional.of(findOrderById(id))
                .map(this::setReady)
                .map(orderRepository::save)
                .filter(order -> order.getOrderType().equals(OrderType.SELFPICKUP))
                .map(this::sendUserNotificationReadyOrder);
        return new Response<>("created.");
    }

    public Response<String> deliveredOrderByStore(Long id,String comment,String date){
        Optional.of(findOrderById(id))
                .map(this::setDelivered)
                .map(order -> setComment(order,comment,date))
                .map(orderRepository::save);
        return new Response<>("created.");
    }

    public Response<String> pickedUpOrderByStore(Long id,String comment,String date){
        Optional.of(findOrderById(id))
                .map(this::setPickedUp)
                .map(order -> setComment(order,comment,date))
                .map(orderRepository::save);
        return new Response<>("created.");
    }

    public Response<String> receivedOrderByUser(Long id){
        Optional.of(findOrderById(id))
                .map(this::setReceived)
                .map(orderRepository::save);
        return new Response<>("created.");
    }

    /*public Response<String> cancelOrderByUser(Long id){
        Optional.of(findOrderById(id))
                .map(this::setCanceled)
                .map(orderRepository::save);
        return new Response<>("created.");
    }*/

    @NotNull
    private Order setComment(Order order,String comment,String date){
        if(!DateUtil.isValidDateTime(date)){
            throw new DateException("error.date.invalid");
        }
        order.setProviderDate(DateUtil.parseDateTime(date));
        order.setProviderComment(comment);
        return order;
    }

    @NotNull
    private Order setAccepted(Order order) {
        order.getOrderState().setAccepted(true);
        return order;
    }

    @NotNull
    private Order setRejected(Order order) {
        order.getOrderState().setRejected(true);
        return order;
    }

    @NotNull
    private Order setReady(Order order) {
        order.getOrderState().setReady(true);
        return order;
    }

    @NotNull
    private Order setDelivered(Order order) {
        order.getOrderState().setDelivered(true);
        return order;
    }

    @NotNull
    private Order setPickedUp(Order order) {
        order.getOrderState().setPickedUp(true);
        return order;
    }

    @NotNull
    private Order setReceived(Order order) {
        order.getOrderState().setReceived(true);
        return order;
    }

    @NotNull
    private Order setNewOrder(Order order) {
        order.getOrderState().setNewOrder(false);
        return order;
    }

   /* @NotNull
    private Order setCanceled(Order order) {
        order.getOrderState().setCanceled(true);
        return order;
    }

    @NotNull
    private Order setArchived(Order order) {
        order.getOrderState().setArchived(true);
        return order;
    }

    @NotNull
    private Order setArchivedProblem(Order order) {
        order.getOrderState().setArchivedProblem(true);
        return order;
    }*/
}
