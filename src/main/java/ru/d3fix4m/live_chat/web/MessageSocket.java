package ru.d3fix4m.live_chat.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import ru.d3fix4m.live_chat.config.CacheProperty;
import ru.d3fix4m.live_chat.domain.persistence.entity.Message;
import ru.d3fix4m.live_chat.domain.service.MessageService;
import ru.d3fix4m.live_chat.payload.CreateMessageRequest;


@Slf4j
@Controller
public class MessageSocket {
    private final MessageService service;
    private final Sinks.Many<Message> cachedMessageSink;

    public MessageSocket(MessageService service, CacheProperty cacheProperty) {
        this.service = service;
        this.cachedMessageSink = Sinks
                .many()
                .replay()
                .limit(cacheProperty.getCapacity());

    }

    @MessageMapping("${spring.rsocket.chat-mapping.send}")
    @Transactional
    public Mono<Void> send(CreateMessageRequest request) {
        return service.create(request)
                .doOnSuccess(cachedMessageSink::tryEmitNext)
                .then();
    }

    @MessageMapping("${spring.rsocket.chat-mapping.receive}")
    public Flux<Message> get() {
        return cachedMessageSink.asFlux();
    }
}
