package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/test")
    public String showTestPage() {
        return "test";  // templates/test.html を探して返す
    }
    @GetMapping("/crm")
    public String showInitialPage() {
        return "initial";
    }
}