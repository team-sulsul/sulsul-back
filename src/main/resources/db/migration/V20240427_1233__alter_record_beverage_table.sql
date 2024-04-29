-- record_id index 추가

ALTER TABLE record_beverage
    ADD INDEX idx__record_id (record_id);

