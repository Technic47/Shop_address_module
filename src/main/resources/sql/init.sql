TRUNCATE TABLE address;

INSERT INTO address(city, street, house, created, updated)
VALUES ('Москва', 'Тестовая ул.', '123', NOW(), NOW());
