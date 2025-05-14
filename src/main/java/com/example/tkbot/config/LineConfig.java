package com.example.tkbot.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class LineConfig {

    private final String channelAccessToken;

    public LineConfig() {
        Dotenv dotenv = Dotenv.load(); // .env を読み込む
        this.channelAccessToken = dotenv.get("LINE_CHANNEL_ACCESS_TOKEN");
    }

    public String getChannelAccessToken() {
        return channelAccessToken;
    }
}