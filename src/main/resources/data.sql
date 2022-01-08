INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('restaurant_1'),
       ('restaurant_2'),
       ('restaurant_3');

INSERT INTO MENU_ITEM (name, price, menu_date, restaurant_id)
VALUES ('item_1 restaurant_1', 1000, DATEADD(DAY, -1, CURRENT_DATE), 1),
       ('item_2 restaurant_1', 1200, DATEADD(DAY, -1, CURRENT_DATE), 1),
       ('item_3 restaurant_1', 300, CURRENT_DATE, 1),
       ('item_4 restaurant_1', 1300, CURRENT_DATE, 1),
       ('item_1 restaurant_2', 1000, DATEADD(DAY, -1, CURRENT_DATE), 2),
       ('item_2 restaurant_2', 1200, DATEADD(DAY, -1, CURRENT_DATE), 2),
       ('item_3 restaurant_2', 1500, CURRENT_DATE, 2),
       ('item_4 restaurant_2', 200, CURRENT_DATE, 2),
       ('item_1 restaurant_3', 1000, DATEADD(DAY, -1, CURRENT_DATE), 3),
       ('item_2 restaurant_3', 1600, DATEADD(DAY, -1, CURRENT_DATE), 3);

INSERT INTO USER_VOTE (user_id, restaurant_id, vote_date)
VALUES (1, 1, DATEADD(DAY, -1, CURRENT_DATE)),
       (1, 2, CURRENT_DATE()),
       (2, 1, DATEADD(DAY, -1, CURRENT_DATE));