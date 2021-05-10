package com.example.springmvcrest.order.api.mapper;

import com.example.springmvcrest.address.api.AddressMapper;
import com.example.springmvcrest.bill.api.BillMapper;
import com.example.springmvcrest.order.api.dto.OrderCreationDto;
import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BillMapper.class,AddressMapper.class,OrderProductVariantMapper.class,SimpleUserService.class, StoreService.class})
public interface OrderMapper {

    @Mapping(source = "order.store", target = "storeName", qualifiedByName = "getStoreName")
    @Mapping(source = "order.store", target = "storeAddress", qualifiedByName = "getStoreAddress")
    OrderDto toDto(Order order);
    @Named("getStoreName")
    default String getStoreName(Store store) { return  store.getName(); }
    @Named("getStoreAddress")
    default String getStoreAddress(Store store) { return  store.getAddress(); }


    @Mapping(source = "orderCreationDto.userId", target = "user")
    @Mapping(source = "orderCreationDto.storeId", target = "store", qualifiedByName = "findStoreById")
    Order toModel(OrderCreationDto orderCreationDto);
}
