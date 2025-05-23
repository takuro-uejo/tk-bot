package com.example.tkbot.controller;

import com.example.tkbot.model.DebugPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

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
            LocalDate today;
            String dayOfWeek;
            // 昨日or今日or明日の判定
            if (message.equals("昨日")) {
                today = LocalDate.now().minusDays(1);
                dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPAN);
            } else if(message.equals("今日")){
                today = LocalDate.now();
                dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPAN);
            } else if(message.equals("明日")){
                today = LocalDate.now().plusDays(1);
                dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPAN);
            } else {
                dayOfWeek = "そんなものはない";
            }
            return ResponseEntity.ok("DEBUG: 受け取ったメッセージ → " + dayOfWeek);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DEBUG: JSONパースに失敗しました: " + e.getMessage());
            return ResponseEntity
            .badRequest()
            .body("DEBUG: JSONパースに失敗しました: " + e.getMessage());
        }
    }
}