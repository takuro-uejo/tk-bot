package com.example.tkbot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DeliveryContext {
        @JsonProperty("redelivery")
        private boolean redelivery;

        public boolean isRedelivery() {
            return redelivery;
        }

        public void setRedelivery(boolean redelivery) {
            this.redelivery = redelivery;
        }
    }

    @Getter
    @Setter
    public static class Source {
        private String type;
        private String userId;
    }
}