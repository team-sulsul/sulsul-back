CREATE TABLE beverage
(
    id         BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name       VARCHAR(255)          NOT NULL,
    capacity   INT                   NOT NULL,
    category   VARCHAR(255)          NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME
);
