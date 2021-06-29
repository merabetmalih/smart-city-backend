package com.example.springmvcrest.sse.controller;

import com.example.springmvcrest.sse.events.OrderChangeEvent;
import com.example.springmvcrest.sse.service.EventPublisherService;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sse/flux")
public class OrderChangeEventListenerController extends GenericEventListenerController<OrderChangeEvent> {

    public OrderChangeEventListenerController(EventPublisherService eventPublisherService) {
        super(eventPublisherService);
    }

    @GetMapping("/fire-order-change-event")
    @Override
    public void fireEvent() {
        eventPublisherService.orderChangePublishEvent();
    }

    @GetMapping(path = "/order-change-event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Override
    public Flux<OrderChangeEvent> getEventListener() {
        return super.getEventListener();
    }


    @Override
    public void onApplicationEvent(OrderChangeEvent orderChangeEvent) {
        subscribableChannel.send(new GenericMessage<>(orderChangeEvent));
    }
}
