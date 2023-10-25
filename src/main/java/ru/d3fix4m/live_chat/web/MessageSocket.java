package ru.d3fix4m.live_chat.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import ru.d3fix4m.live_chat.config.CacheProperty;
import ru.d3fix4m.live_chat.domain.persistence.entity.Message;
import ru.d3fix4m.live_chat.domain.persistence.repository.RoomRepository;
import ru.d3fix4m.live_chat.domain.service.MessageService;
import ru.d3fix4m.live_chat.payload.CreateMessageRequest;

import java.util.Map;
import java.util.UUID;


@Slf4j
@Controller
public class MessageSocket {
    private final MessageService service;
    private final CacheProperty cacheProperty;
    private final Map<UUID, Sinks.Many<Message>> cachedMessageSink;

    public MessageSocket(MessageService service, RoomRepository roomRepository, CacheProperty cacheProperty) {
        this.service = service;
        this.cacheProperty = cacheProperty;
        this.cachedMessageSink = roomRepository.findAllId()
                .flatMap(roomId -> {
                    Sinks.Many<Message> sink = Sinks
                            .many()
                            .replay()
                            .limit(cacheProperty.getCapacity());
                    return Mono.just(Pair.of(roomId, sink));
                })
                .collectMap(Pair::getFirst, Pair::getSecond)
                .block();

    }

    @MessageMapping("${spring.rsocket.chat-mapping.send}")
    @Transactional
    public Mono<Void> send(CreateMessageRequest request) {
        return service.create(request)
                .doOnSuccess(message -> {
                    cachedMessageSink.computeIfAbsent(
                            message.getRoomId(),
                            id -> Sinks.many().replay().limit(cacheProperty.getCapacity())
                    ).tryEmitNext(message);
                })
                .then();
    }

    @MessageMapping("${spring.rsocket.chat-mapping.receive}")
    public Flux<Message> get(UUID roomId) {

        log.info(String.valueOf(roomId));
        Sinks.Many<Message> messages = cachedMessageSink.computeIfAbsent(
                roomId,
                id -> Sinks.many().replay().limit(cacheProperty.getCapacity())
        );

        return messages.asFlux();
    }
}
