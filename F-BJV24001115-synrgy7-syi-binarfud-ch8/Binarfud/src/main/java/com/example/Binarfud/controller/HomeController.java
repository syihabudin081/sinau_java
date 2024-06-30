package com.example.Binarfud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HomeController {

    @RequestMapping("/binarfud")
    public String home() {
        return "Welcome to Binarfud";
    }

    @RequestMapping("/binarfud/api")
    public String api() {
        return "Welcome to Binarfud API";
    }

}
