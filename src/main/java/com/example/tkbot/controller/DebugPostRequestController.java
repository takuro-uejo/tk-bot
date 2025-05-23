package com.example.tkbot.controller;

import com.example.tkbot.model.DebugPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
public class DebugPostRequestController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<String> handleDebugPost(@RequestBody String payload) {
        try {
            DebugPostRequest request = objectMapper.readValue(payload, DebugPostRequest.class);
            String message = request.getTestmessage();
            System.out.println("DEBUG: 受け取ったメッセージ → " + message);
            return ResponseEntity.ok("DEBUG: 受け取ったメッセージ → " + message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DEBUG: JSONパースに失敗しました: " + e.getMessage());
            return ResponseEntity
            .badRequest()
            .body("DEBUG: JSONパースに失敗しました: " + e.getMessage());
        }
    }
}