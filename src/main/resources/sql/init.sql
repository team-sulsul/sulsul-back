INSERT INTO member (username, password)
VALUES
    ('안정은', '1234'),
    ('이태민', '1234'),
    ('이서윤', '1234'),
    ('홍석주', '1234');


INSERT INTO record (member_id, hangover_level, recorded_at)
VALUES
    (1, 'SOBER', '2024-03-01'),
    (1, 'SOBER', '2024-03-02'),
    (1, 'SOBER', '2024-03-03'),
    (1, 'SOBER', '2024-03-04'),
    (1, 'SOBER', '2024-03-05'),
    (1, 'TIPSY', '2024-03-06'),
    (1, 'SOBER', '2024-03-07'),
    (1, 'TIPSY', '2024-03-08'),
    (1, 'SOBER', '2024-03-09'),
    (1, 'TIPSY', '2024-03-10'),
    (1, 'DRUNK', '2024-03-11'),
    (1, 'DRUNK', '2024-03-12'),
    (1, 'WASTED', '2024-03-13'),
    (1, 'SOBER', '2024-03-14');

INSERT INTO record_beverage (record_id, beverage, drink)
VALUES
    (1, 'SOJU', 5),
    (1, 'SOJU', 10),
    (1, 'SOJU_BEER', 5),
    (1, 'BEER', 5);
