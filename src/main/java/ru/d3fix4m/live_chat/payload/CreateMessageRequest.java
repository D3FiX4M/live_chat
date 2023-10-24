package ru.d3fix4m.live_chat.payload;

public record CreateMessageRequest(
        String message,
        String senderName
) {
}
