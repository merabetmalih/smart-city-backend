package com.example.springmvcrest.flashDeal.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/sse-server")
public class ServerController {

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        ServerSentEvent<String> e = ServerSentEvent.<String>builder()
                .id("e")
                .event("periodic-event")
                .data("SSE - " + LocalTime.now()
                        .toString())
                .build();

        return Flux.just(e);


        /*return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now()
                                .toString())
                        .build());*/
    }

    @GetMapping("/fire")
    public void fire(){
        streamEvents();
    }

    @GetMapping("/stream-sse-mvc")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("SSE MVC - " + LocalTime.now().toString())
                            .id(String.valueOf(i))
                            .name("sse event - mvc");
                    emitter.send(event);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

}



