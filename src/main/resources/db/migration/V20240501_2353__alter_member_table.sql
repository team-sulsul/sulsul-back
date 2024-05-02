-- member 테이블에 email -> nickname으로 칼럼명 변경
-- OAuth 용도

ALTER TABLE member CHANGE COLUMN email nickname VARCHAR(255);

ALTER TABLE member ADD COLUMN useYn CHAR(1);
