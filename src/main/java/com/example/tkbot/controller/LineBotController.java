package com.example.tkbot.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tkbot.model.LineWebhookRequest;

@RestController
@RequestMapping("/webhook")
public class LineBotController {
    // リプライのリクエストを送るエンドポイント
    private static final String REPLY_END_POINT_URL = "https://api.line.me/v2/bot/message/reply";
    // モデルをパースするやつ
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody String payload) {
        // LINEからのリクエスト（JSON）を受け取ってログに出力
        System.out.println("LINE Webhook 受信: " + payload);
        try {
            // ここでpayloadをLineWebhookRequestにパースする
            LineWebhookRequest request = objectMapper.readValue(payload, LineWebhookRequest.class);
            // イベントが存在しない場合には問答無用で200返す（Developer ConsoleからのVerifyがエラーになると気持ち悪いので）
            if (request.getEvents() == null || request.getEvents().isEmpty()) {
                return ResponseEntity.ok("No events (probably LINE verify request)");
            }
            String replyToken = "";
            String receivedText = "";
            for (LineWebhookRequest.Event event : request.getEvents()) {
                replyToken = event.getReplyToken();
                receivedText = event.getMessage().getText();
                System.out.println("受信メッセージ: " + receivedText);
            }
            // ここでリプライする
            return replyMessage(replyToken, receivedText);
        } catch (Exception e) {
            // 多分パースエラーのときここ
            // リクエスト失敗なので400で（仮に）"Invalid payload"ってメッセージ返しておく
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid payload: " + e.getMessage());
        }
    }

    // tokenとmessageをもらってリプライするメソッド
    private ResponseEntity<String> replyMessage(String replyToken, String message) {
        String channelAccessToken = System.getenv("LINE_CHANNEL_ACCESS_TOKEN");
        RestTemplate restTemplate = new RestTemplate();
        // HTTPヘッダ
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(channelAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 送り返すメッセージのオブジェクトを作成
        // TODO: いったんオウム返しなので受け取ったもの（message）をそのままセットするだけ
        // Map<String, Object> messageObj = Map.of(
        //         "type", "text",
        //         "text", message + "\n\nJavaのtk-botより");

        Map<String, String>garbageDays =new HashMap<>();
        garbageDays.put("月曜日", "ええやんええやん");
        garbageDays.put("火曜日", "ええやんええやん");
        garbageDays.put("水曜日", "燃えるゴミ");
        garbageDays.put("木曜日", "カン、ビン、ダンボール");
        garbageDays.put("金曜日", "ペットボトル、電池");
        garbageDays.put("土曜日", "燃えるゴミ");
        garbageDays.put("日曜日", "ええやんええやん");

        // 変数を初期化
        LocalDate today;
        String dayOfWeek;
        String garbageDay;

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

        if (dayOfWeek.equals("そんなものはない")) {
            garbageDay = "正気か!?";
        } else {
            garbageDay = garbageDays.get(dayOfWeek);
        }

        Map<String, Object> messageObj = Map.of(
            "type", "text",
            "text", dayOfWeek + "\n" + garbageDay
        );


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
            // postForEntityまで完全に成功したら200でOK返す
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            System.err.println("LINE返信エラー: " + e.getMessage());
            // postForEntityが失敗したら500エラーを返す
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send reply: " + e.getMessage());
        }
    }
}