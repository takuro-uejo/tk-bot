package com.example.tkbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/test")
    public String showTestPage() {
        System.out.println("GET /test.");
        return "test"; // templates/test.html を探して返す
    }
    @GetMapping("/crm")
    public String showInitialPage() {
        return "initial";
    }
}