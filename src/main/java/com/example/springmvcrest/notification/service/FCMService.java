package com.example.springmvcrest.notification.service;

import com.example.springmvcrest.notification.configuration.NotificationParameter;
import com.example.springmvcrest.notification.domain.Notification;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {
    public void sendMessageWithoutData(Notification notification)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithoutData(notification);
        String response = sendAndGetResponse(message);
        log.info("Sent message without data. Topic: " + notification.getTopic() + ", " + response);
    }

    private Message getPreconfiguredMessageWithoutData(Notification notification) {
        return getPreconfiguredMessageBuilder(notification).setTopic(notification.getTopic())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(Notification notification) {
        AndroidConfig androidConfig = getAndroidConfig(notification.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(notification.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        com.google.firebase.messaging.Notification.builder().setTitle(notification.getTitle()).setBody( notification.getMessage()).build());
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private Message getPreconfiguredMessageToToken(Notification request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    public void sendMessageToToken(Notification request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        log.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }
}
