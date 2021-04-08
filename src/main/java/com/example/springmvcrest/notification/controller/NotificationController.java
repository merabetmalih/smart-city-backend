package com.example.springmvcrest.notification.controller;


import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/topic")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification);
        return new Response<>("Notification has been sent.");
    }

   /* @PostMapping("/token")
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> sendTokenNotification(@RequestBody Notification request) {
        fcmNotificationService.sendNotificationToToken(request);
        return new Response<>("Notification has been sent.");
    }*/
}