CREATE TABLE record
(
    id             BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    member_id      BIGINT                NOT NULL,
    hangover_level VARCHAR(255)          NOT NULL,
    recorded_at    DATE,
    created_at     DATETIME DEFAULT NOW(),
    updated_at     DATETIME
);
