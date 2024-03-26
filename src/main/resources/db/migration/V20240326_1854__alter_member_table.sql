-- member 테이블에 email, role, refresh_token 컬럼 추가
-- OAuth 용도

ALTER TABLE member ADD COLUMN email varchar(255);

ALTER TABLE member ADD COLUMN role enum('USER', 'ADMIN');

ALTER TABLE member ADD COLUMN refresh_token varchar(255);