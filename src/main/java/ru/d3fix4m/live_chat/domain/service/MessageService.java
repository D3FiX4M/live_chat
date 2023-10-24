package ru.d3fix4m.live_chat.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.d3fix4m.live_chat.domain.persistence.entity.Message;
import ru.d3fix4m.live_chat.payload.CreateMessageRequest;
import ru.d3fix4m.live_chat.domain.persistence.repository.MessageRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    public Flux<Message> get() {
        return repository
                .findAll();
    }

    public Mono<Message> create(CreateMessageRequest request) {
        return repository.save(
                new Message(
                        null,
                        request.message(),
                        request.senderName()
                )
        );
    }
}
