package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class LineBotController {
    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody String payload) {
        // LINEからのリクエスト（JSON）を受け取ってログに出力
        System.out.println("LINE Webhook 受信: " + payload);
        // 仮の応答（LINEの要件：200 OKを返す）
        return ResponseEntity.ok("OK");
    }
}