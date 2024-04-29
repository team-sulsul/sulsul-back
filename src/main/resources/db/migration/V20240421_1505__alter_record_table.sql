-- 유니크 키 제약 추가

alter table record
    add constraint unique_member_record
        unique (member_id, recorded_at);

