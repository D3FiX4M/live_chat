package ru.d3fix4m.live_chat.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.d3fix4m.live_chat.domain.persistence.entity.Room;
import ru.d3fix4m.live_chat.domain.service.RoomService;
import ru.d3fix4m.live_chat.payload.CreateRoomRequest;

import java.util.UUID;
@CrossOrigin("*")
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @GetMapping
    public Flux<Room> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Mono<Room> create(@RequestBody CreateRoomRequest request) {
        return service.create(request.name());
    }
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable UUID id){
        return service.delete(id);
    }
}
