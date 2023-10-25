package ru.d3fix4m.live_chat.domain.persistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.d3fix4m.live_chat.domain.persistence.entity.Room;

import java.util.UUID;

@Repository
public interface RoomRepository extends ReactiveCrudRepository<Room, UUID> {

    @Query("SELECT c.id from room c")
    Flux<UUID> findAllId();
}
