package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class PageController {
    @GetMapping("/test")
    public String showTestPage() {
        return "test"; // templates/test.html を探して返す
    }

    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String requestBody, @RequestHeader("X-Line-Signature") String signature) {
        // LINEbotからのリクエストはこのパスで拾う
        // 今はテスト的にOKを返す
        return "OK";
    }
}