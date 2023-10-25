CREATE TABLE IF NOT EXISTS message
(
    id          uuid DEFAULT gen_random_uuid(),
    room_id     uuid NOT NULL,
    message     text NOT NULL,
    sender_name text NOT NULL,
    sended_at    time NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS room
(
    id        uuid DEFAULT gen_random_uuid(),
    name      varchar(128) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE         NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_ROOM FOREIGN KEY (room_id) REFERENCES room (id);