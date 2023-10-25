package ru.d3fix4m.live_chat.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.d3fix4m.live_chat.domain.persistence.entity.Message;
import ru.d3fix4m.live_chat.domain.persistence.repository.MessageRepository;
import ru.d3fix4m.live_chat.domain.persistence.repository.RoomRepository;
import ru.d3fix4m.live_chat.payload.CreateMessageRequest;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;
    private final RoomRepository roomRepository;

    public Flux<Message> getAll() {
        return repository
                .findAll();
    }

    public Mono<Message> create(CreateMessageRequest request) {

        return roomRepository.existsById(request.roomId())
                .flatMap(exist -> {
                    if (exist) {
                        return repository.save(
                                new Message(
                                        null,
                                        request.roomId(),
                                        request.message(),
                                        request.senderName(),
                                        LocalTime.now()
                                )
                        );
                    } else {
                        return Mono.error(new RuntimeException("NOT EXIST"));
                    }
                });
    }
}
