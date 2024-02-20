CREATE TABLE record_beverage
(
    id          BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    record_id   BIGINT                NOT NULL,
    beverage_id BIGINT                NOT NULL,
    count       INT                   NOT NULL,
    created_at  DATETIME DEFAULT NOW(),
    updated_at  DATETIME
);
