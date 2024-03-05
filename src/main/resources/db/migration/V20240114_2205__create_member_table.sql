CREATE TABLE member
(
    id              BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email           VARCHAR(255) NOT NULL,
    nickname        VARCHAR(255) NOT NULL,
    o_auth_provider ENUM('KAKAO') NOT NULL, -- Enum 값에 따라 추가
    refresh_token   VARCHAR(255),
    access_token    VARCHAR(255),
    created_at      DATETIME,
    updated_at      DATETIME,
    CONSTRAINT idx_username UNIQUE (nickname)
);
