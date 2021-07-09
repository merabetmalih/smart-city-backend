package com.example.springmvcrest.bootstrap;

import com.example.springmvcrest.offer.repository.OfferRepository;
import com.example.springmvcrest.product.repository.ProductRepository;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
/*
@Component
@AllArgsConstructor
public class initData implements ApplicationListener<ContextRefreshedEvent> {
    private final StoreRepository storeRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
*/