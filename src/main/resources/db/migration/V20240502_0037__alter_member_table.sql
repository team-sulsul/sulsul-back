-- use_yn 형 변환
-- member 테이블에 useYn 칼럼 삭제

ALTER TABLE member MODIFY COLUMN use_yn VARCHAR(255);

ALTER TABLE member DROP COLUMN useYn;

