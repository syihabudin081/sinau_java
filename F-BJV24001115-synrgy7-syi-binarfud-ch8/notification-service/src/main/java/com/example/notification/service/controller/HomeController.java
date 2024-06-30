package com.example.notification.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/notification")
    public String home() {
        return "Welcome to notification";
    }

    @RequestMapping("/")
    public String api() {
        return "Welcome to notification API";
    }
}
