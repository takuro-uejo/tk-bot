package com.example.tkbot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LineWebhookRequest {
    private String destination;
    private List<Event> events;

    @Getter
    @Setter
    public static class Event {
        private String type;
        private Message message;
        private String webhookEventId;
        private DeliveryContext deliveryContext;
        private long timestamp;
        private Source source;
        private String replyToken;
        private String mode;
    }

    @Getter
    @Setter
    public static class Message {
        private String type;
        private String id;
        private String quoteToken;
        private String text;
    }

    @Getter
    @Setter
    public static class DeliveryContext {
        private boolean isRedelivery;
    }

    @Getter
    @Setter
    public static class Source {
        private String type;
        private String userId;
    }
}