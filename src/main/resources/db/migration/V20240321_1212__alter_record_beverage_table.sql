-- 필드명 변경

ALTER TABLE record_beverage CHANGE COLUMN beverage_id beverage varchar(255);
ALTER TABLE record_beverage RENAME COLUMN `count` TO drink;
