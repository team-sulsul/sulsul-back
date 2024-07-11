INSERT INTO member (username, password, nickname, role, use_yn)
VALUES
    ('test1@test.com', 'password', '안정은', 'USER', 'Y'),
    ('test2@test.com', 'password', '이태민', 'USER', 'Y'),
    ('test3@test.com', 'password', '이서윤', 'USER', 'Y'),
    ('test4@test.com', 'password', '홍석주', 'USER', 'Y');


INSERT INTO record (member_id, drunken_level, recorded_at)
VALUES
    (4, 'DRUNKEN_LEVEL1', '2024-03-01'),
    (4, 'DRUNKEN_LEVEL2', '2024-03-02'),
    (4, 'DRUNKEN_LEVEL3', '2024-03-03'),
    (4, 'DRUNKEN_LEVEL4', '2024-03-04'),
    (4, 'DRUNKEN_LEVEL5', '2024-03-05'),
    (4, 'DRUNKEN_LEVEL1', '2024-03-06'),
    (4, 'DRUNKEN_LEVEL2', '2024-03-07'),
    (4, 'DRUNKEN_LEVEL3', '2024-03-08'),
    (4, 'DRUNKEN_LEVEL4', '2024-03-09'),
    (4, 'DRUNKEN_LEVEL5', '2024-03-10'),
    (4, 'DRUNKEN_LEVEL1', '2024-03-11'),
    (4, 'DRUNKEN_LEVEL2', '2024-03-12'),
    (4, 'DRUNKEN_LEVEL3', '2024-03-13'),
    (3, 'DRUNKEN_LEVEL4', '2024-07-14'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-01'),
    (4, 'DRUNKEN_LEVEL1', '2024-07-02'),
    (4, 'DRUNKEN_LEVEL2', '2024-07-03'),
    (4, 'DRUNKEN_LEVEL3', '2024-07-04'),
    (4, 'DRUNKEN_LEVEL4', '2024-07-05'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-06'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-07'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-08'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-09'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-10'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-11'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-12'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-13'),
    (4, 'DRUNKEN_LEVEL5', '2024-07-14');


INSERT INTO record_beverage (record_id, beverage, drink)
VALUES
    (15, 'SOJU', 5),
    (16, 'SOJU', 10),
    (17, 'SOJU_BEER', 5),
    (18, 'BEER', 5);
