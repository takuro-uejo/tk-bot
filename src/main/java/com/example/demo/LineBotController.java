package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/webhook")
public class LineBotController {
    // リプライのリクエストを送るエンドポイント
    private static final String REPLY_END_POINT_URL = "https://api.line.me/v2/bot/message/reply";
    // LINE Developersから取得したチャネルアクセストークン（長期）
    private static final String CHANNEL_ACCESS_TOKEN = "YOUR_CHANNEL_ACCESS_TOKEN";

    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> payload) {
        // LINEからのリクエスト（JSON）を受け取ってログに出力
        System.out.println("LINE Webhook 受信: " + payload);
        // 仮の応答（LINEの要件：200 OKを返す）
        return ResponseEntity.ok("OK");
    }

    private void replyMessage(String replyToken, String message) {
        RestTemplate restTemplate = new RestTemplate();

        // HTTPヘッダ
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(CHANNEL_ACCESS_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 送り返すメッセージのオブジェクトを作成
        // いったんオウム返しなので受け取ったもの（message）をそのままセットするだけ
        Map<String, Object> messageObj = Map.of(
                "type", "text",
                "text", message);
        // HTTPボディ
        Map<String, Object> body = Map.of(
                "replyToken", replyToken,
                "messages", List.of(messageObj));
        // bodyとheaderを詰めたHTTPリクエストを作成
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            restTemplate.postForEntity(
                    REPLY_END_POINT_URL,
                    request,
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}