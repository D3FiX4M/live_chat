package ru.d3fix4m.live_chat.domain.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.d3fix4m.live_chat.domain.persistence.entity.Message;

import java.util.UUID;

@Repository
public interface MessageRepository extends ReactiveCrudRepository<Message, UUID> {

}
