package ru.d3fix4m.live_chat.domain.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Message {
    @Id
    private UUID id;

    private String message;

    private String senderName;
}
