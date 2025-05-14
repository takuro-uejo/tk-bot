package com.example.tkbot.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class LineWebhookRequest {
    private List<Event> events;

    // Getter & Setter
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }

    @Getter
    @Setter
    public static class Event {
        private String replyToken;
        private String type;
        private Message message;
    }

    @Getter
    @Setter
    public static class Message {
        private String type;
        private String text;
    }
}
