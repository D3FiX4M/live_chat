
CREATE TABLE IF NOT EXISTS message
(
    id          uuid DEFAULT gen_random_uuid(),
    message     text NOT NULL,
    sender_name text NOT NULL,
    PRIMARY KEY (id)
);