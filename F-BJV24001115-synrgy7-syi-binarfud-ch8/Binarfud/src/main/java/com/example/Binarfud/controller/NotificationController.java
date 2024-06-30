package com.example.Binarfud.controller;

import com.example.Binarfud.model.NotificationRequest;
import com.example.Binarfud.model.NotificationResponse;
import com.example.Binarfud.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class NotificationController {
    @Autowired
    private FCMService fcmService;
    private NotificationRequest scheduledRequest;

    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @PostMapping("/scheduleNotification")
    public ResponseEntity scheduleNotification(@RequestBody NotificationRequest request) {
        this.scheduledRequest = request;
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been scheduled."), HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 12 * * ?") // every day at 12 PM
    public void sendScheduledNotification() throws ExecutionException, InterruptedException {
        if (scheduledRequest != null) {
            fcmService.sendMessageToToken(scheduledRequest);
        }
    }

}