package ru.d3fix4m.live_chat.payload;

import java.util.UUID;

public record CreateMessageRequest(

        UUID roomId,
        String message,
        String senderName
) {
}
