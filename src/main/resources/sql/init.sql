INSERT INTO beverage (name, capacity, category)
VALUES
    ('참이슬', 500, 'SOJU'),
    ('진로', 500, 'SOJU'),
    ('카스', 800, 'BEER'),
    ('테라', 800, 'BEER');


INSERT INTO member (username, password)
VALUES
    ('member1', '1234'),
    ('member2', '1234'),
    ('member3', '1234'),
    ('member4', '1234');


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

INSERT INTO record_beverage (record_id, beverage_id, count)
VALUES
    (7, 1, 5),
    (7, 2, 10),
    (7, 3, 5),
    (7, 4, 5);
