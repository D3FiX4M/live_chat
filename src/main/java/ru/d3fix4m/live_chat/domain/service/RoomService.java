package ru.d3fix4m.live_chat.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.d3fix4m.live_chat.domain.persistence.entity.Room;
import ru.d3fix4m.live_chat.domain.persistence.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;

    public Mono<Room> create(String name) {
        return repository.save(
                new Room(
                        null,
                        name,
                        LocalDateTime.now()
                )
        );
    }

    public Flux<Room> getAll() {
        return repository.findAll();
    }

    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}
