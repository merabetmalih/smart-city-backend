package com.example.springmvcrest.sse.controller;

import com.example.springmvcrest.sse.events.Event;
import com.example.springmvcrest.sse.service.EventPublisherService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


@AllArgsConstructor
public abstract class GenericEventListenerController<T extends Event> implements ApplicationListener<T> {
    public final EventPublisherService eventPublisherService;
    public final SubscribableChannel subscribableChannel = MessageChannels.publishSubscribe().get();

    abstract public void fireEvent();

    public Flux<T> getEventListener(){
        return Flux.create(sink -> {
            MessageHandler handler = message -> sink.next(((T) message.getPayload()));
            sink.onCancel(() -> subscribableChannel.unsubscribe(handler));
            subscribableChannel.subscribe(handler);
        }, FluxSink.OverflowStrategy.LATEST);
    }
}

