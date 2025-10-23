TRUNCATE shop_test.public.address RESTART IDENTITY CASCADE;

insert into shop_test.public.address(city, street, house, created, updated)
values ('Москва', 'Тестовая ул.', '123', NOW(), NOW())