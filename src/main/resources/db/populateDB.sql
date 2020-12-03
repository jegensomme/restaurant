delete from order_dishes;
delete from dishes;
delete from dish_categories;
delete from user_roles;
delete from orders;
delete from tables;
delete from users;
delete from user_shifts;
--drop owned by user_waiter;
--drop owned by user_manager;
--drop user user_manager;
--drop user user_waiter;
alter sequence global_seq restart with 10000;

--create user user_waiter with role waiter password 'password';
--create user user_manager with role manager password 'password';

insert into users (key, name) values ('0000', 'Скоморохова Анна');   --10000
insert into users (key, name) values ('1111', 'Иванов Виктор');      --10001
insert into users (key, name) values ('2222', 'Сидельников Федер');  --10002
insert into users (key, name) values ('3333', 'Сидоров Костя');      --10003
insert into users (key, name) values ('4444', 'Хомутов Иван');       --10004
insert into users (key, name) values ('5555', 'Ефремов Никита');     --10005
insert into users (key, name) values ('6666', 'Красиков Алексей');   --10006
insert into users (key, name) values ('7777', 'Харитонович Иосиф');  --10007
insert into users (key, name) values ('8888', 'Соболев Степан');     --10008
insert into users (key, name) values ('9999', 'Лысиков Виктор');     --10009
insert into users (key, name) values ('1234', 'Варламов Михаил');    --10010

insert into user_roles (user_id, role) values (10000, 'MANAGER');
insert into user_roles (user_id, role) values (10001, 'WAITER');
insert into user_roles (user_id, role) values (10002, 'WAITER');
insert into user_roles (user_id, role) values (10003, 'WAITER');
insert into user_roles (user_id, role) values (10004, 'WAITER');
insert into user_roles (user_id, role) values (10005, 'WAITER');
insert into user_roles (user_id, role) values (10006, 'WAITER');
insert into user_roles (user_id, role) values (10007, 'WAITER');
insert into user_roles (user_id, role) values (10008, 'WAITER');
insert into user_roles (user_id, role) values (10009, 'WAITER');
insert into user_roles (user_id, role) values (10010, 'MANAGER');

insert into dish_categories (name, category_id) values ('Основное меню', null); --10011
insert into dish_categories (name, category_id) values ('Барное меню',   null); --10012

-- Основное меню --------------------------------------------------------------------------
insert into dish_categories (name, category_id) values ('Завтраки',        10011);  --10013
insert into dish_categories (name, category_id) values ('Закуски',         10011);  --10014
insert into dish_categories (name, category_id) values ('Горячее из мяса', 10011);  --10015
insert into dish_categories (name, category_id) values ('Горячее из рыбы', 10011);  --10016
insert into dish_categories (name, category_id) values ('Супы',            10011);  --10017
insert into dish_categories (name, category_id) values ('Салаты',          10011);  --10018
insert into dish_categories (name, category_id) values ('Десерты',         10011);  --10019

-- Завтраки -------------------------
insert into dishes (name, category_id, cost) values ('Блины',   10013, 200); --10020
insert into dishes (name, category_id, cost) values ('Сырники', 10013, 300); --10021
insert into dishes (name, category_id, cost) values ('Омлет',   10013, 150); --10022
------------------------------------

-- Закуски ------------------------
insert into dish_categories (name, category_id) values ('Брускетты', 10014); --10023
-- Брускетты ---
insert into dishes (name, category_id, cost) values ('Брускетта с лососем', 10023, 450); --10024
insert into dishes (name, category_id, cost) values ('Брускетта с крабом',  10023, 350); --10025
----------------
insert into dishes (name, category_id, cost) values ('Мясная нарезка', 10014, 600); --10026
insert into dishes (name, category_id, cost) values ('Сырная тарелка', 10014, 400); --10027
-----------------------------------

-- Горяее из мяса -----------------
insert into dishes (name, category_id, cost) values ('Котлеты по киевски', 10015, 300); --10028
insert into dishes (name, category_id, cost) values ('Бивштекс',           10015, 700); --10029
insert into dishes (name, category_id, cost) values ('Котлеты с пюре',     10015, 300); --10030
insert into dishes (name, category_id, cost) values ('Бевстроганов',       10015, 600); --10031
-----------------------------------

-- Горяее из рыбы -----------------
insert into dishes (name, category_id, cost) values ('Ладожский судак',          10016, 300); --10032
insert into dishes (name, category_id, cost) values ('Лосось в орзотто',         10016, 700); --10033
insert into dishes (name, category_id, cost) values ('Палтус с цветной капустой', 10016, 300); --10034
insert into dishes (name, category_id, cost) values ('Треска с икорным соусом',  10016, 600); --10035
-----------------------------------

-- Супы ---------------------------
insert into dishes (name, category_id, cost) values ('Тыквенный', 10017, 300); --10036
insert into dishes (name, category_id, cost) values ('Борщ',      10017, 250); --10037
insert into dishes (name, category_id, cost) values ('Том ям',    10017, 300); --10038
insert into dishes (name, category_id, cost) values ('Уха',       10017, 400); --10039
-----------------------------------

-- Салаты ---------------------------
insert into dishes (name, category_id, cost) values ('Цезарь',    10018, 400); --10040
insert into dishes (name, category_id, cost) values ('Оливье',    10018, 300); --10041
insert into dishes (name, category_id, cost) values ('Овощной',   10018, 200); --10042
insert into dishes (name, category_id, cost) values ('Греческий', 10018, 300); --10043
-----------------------------------

-- Десерты ---------------------------
insert into dishes (name, category_id, cost) values ('Наполеон',       10019, 200); --10044
insert into dishes (name, category_id, cost) values ('Морковный торт', 10019, 300); --10045
insert into dishes (name, category_id, cost) values ('Медовик',        10019, 250); --10046
insert into dishes (name, category_id, cost) values ('Сметанник',      10019, 180); --10047
-----------------------------------
----------------------------------------------------------------------------------------------

-- Барное меню -------------------------------------------------------------------------------
insert into dish_categories(name, category_id) values ('Чай',      10012); -- 10048
insert into dish_categories(name, category_id) values ('Кофе',     10012); -- 10049
insert into dish_categories(name, category_id) values ('Лимонады', 10012); -- 10050
insert into dish_categories(name, category_id) values ('Соки',     10012); -- 10051

-- Чай ----------------------------
insert into dishes (name, category_id, cost) values ('Ассам',    10048, 150); --10052
insert into dishes (name, category_id, cost) values ('Эрл грей', 10048, 200); --10053
insert into dishes (name, category_id, cost) values ('Сенча',    10048, 150); --10054
-----------------------------------

-- Кофе ---------------------------
insert into dishes (name, category_id, cost) values ('Капучино',  10049, 200); --10055
insert into dishes (name, category_id, cost) values ('Латте',     10049, 250); --10056
insert into dishes (name, category_id, cost) values ('Американо', 10049, 200); --10057
-----------------------------------

-- Лимонады -----------------------
insert into dishes (name, category_id, cost) values ('Спрайт',  10050, 100); --10058
insert into dishes (name, category_id, cost) values ('Кола',    10050, 150); --10059
insert into dishes (name, category_id, cost) values ('Миринда', 10050, 100); --10060
-----------------------------------

-- Соки -----------------------
insert into dishes (name, category_id, cost) values ('Яблочный',     10051, 100); --10061
insert into dishes (name, category_id, cost) values ('Апельсиновый', 10051, 150); --10062
insert into dishes (name, category_id, cost) values ('Морковный',     10051, 100); --10063
-----------------------------------
-----------------------------------------------------------------------------------------------

insert into tables(number, seats) values (11, 1); --10064
insert into tables(number, seats) values (12, 1); --10065
insert into tables(number, seats) values (13, 1); --10066
insert into tables(number, seats) values (14, 1); --10067
insert into tables(number, seats) values (21, 2); --10068
insert into tables(number, seats) values (22, 2); --10069
insert into tables(number, seats) values (23, 2); --10070
insert into tables(number, seats) values (24, 2); --10071
insert into tables(number, seats) values (31, 3); --10072
insert into tables(number, seats) values (32, 3); --10073
insert into tables(number, seats) values (33, 3); --10074
insert into tables(number, seats) values (34, 3); --10075
insert into tables(number, seats) values (41, 4); --10076
insert into tables(number, seats) values (42, 4); --10077
insert into tables(number, seats) values (43, 4); --10078
insert into tables(number, seats) values (44, 4); --10079

-------------------------------ОКТЯБРЬ-------------------------------
----------------------10 Октября---------------------------------------------
insert into user_shifts (user_id, date, start_time, end_time)   --10080
    values (10000, '2020-10-10', '09:00:00', '21:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10081
    values (10001, '2020-10-10', '11:00:00', '23:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10082
    values (10002, '2020-10-10', '11:00:00', '23:00:00');

------------Иванов Виктор---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10083
    values (10001, '2020-10-10 12:00:00', 10064, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10084
    values (10083, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10085
    values (10083, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10086
    values (10001, '2020-10-10 20:00:00', 10069, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10087
    values (10086, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10088
    values (10086, 10038, 1, 'comment');
----------------------------------------------------------------------------

------------Сидельников Федер---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10089
    values (10002, '2020-10-10 13:00:00', 10067, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10090
    values (10089, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10091
    values (10089, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10092
    values (10002, '2020-10-10 22:00:00', 10075, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10093
    values (10092, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10094
    values (10092, 10038, 1, 'comment');
----------------------------------------------------------------
-----------------------------------------------------------------------------

----------------------20 Октября---------------------------------------------
insert into user_shifts (user_id, date, start_time, end_time)   --10095
    values (10000, '2020-10-20', '09:00:00', '21:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10096
    values (10001, '2020-10-20', '11:00:00', '23:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10097
    values (10002, '2020-10-20', '11:00:00', '23:00:00');

------------Иванов Виктор---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10098
    values (10001, '2020-10-20 12:00:00', 10064, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10099
    values (10098, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10100
    values (10098, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10101
    values (10001, '2020-10-20 20:00:00', 10069, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10102
    values (10101, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10103
    values (10101, 10038, 1, 'comment');
--------------------------------------------------------------------

------------Сидельников Федер-----------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10104
    values (10002, '2020-10-20 13:00:00', 10067, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10105
    values (10104, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10106
    values (10104, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10107
    values (10002, '2020-10-20 22:00:00', 10075, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10108
    values (10107, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10109
    values (10107, 10038, 1, 'comment');
----------------------------------------------------------------
------------------------------------------------------------------------
-----------------------------------------------------------------------------

-------------------------------НОЯБРЬ----------------------------------------
----------------------10 Ноября---------------------------------------------
insert into user_shifts (user_id, date, start_time, end_time)   --10110
    values (10000, '2020-11-10', '09:00:00', '21:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10111
    values (10001, '2020-11-10', '11:00:00', '23:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10112
    values (10002, '2020-11-10', '11:00:00', '23:00:00');

------------Иванов Виктор---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10113
    values (10001, '2020-11-10 12:00:00', 10064, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10114
    values (10113, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10115
    values (10113, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10116
    values (10001, '2020-11-10 20:00:00', 10069, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10117
    values (10116, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10118
    values (10116, 10038, 1, 'comment');
----------------------------------------------------------------------------

------------Сидельников Федер---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10119
    values (10002, '2020-11-10 13:00:00', 10067, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10120
    values (10119, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10121
    values (10119, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10122
    values (10002, '2020-11-10 22:00:00', 10075, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10123
    values (10122, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10124
    values (10122, 10038, 1, 'comment');
----------------------------------------------------------------
-----------------------------------------------------------------------------

----------------------20 Ноября---------------------------------------------
insert into user_shifts (user_id, date, start_time, end_time)   --10125
    values (10000, '2020-11-20', '09:00:00', '21:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10126
    values (10001, '2020-11-20', '11:00:00', '23:00:00');
insert into user_shifts (user_id, date, start_time, end_time)   --10127
    values (10002, '2020-11-20', '11:00:00', '23:00:00');

------------Иванов Виктор---------------------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10128
    values (10001, '2020-11-20 12:00:00', 10064, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10129
    values (10128, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10130
    values (10128, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10131
    values (10001, '2020-11-20 20:00:00', 10069, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10132
    values (10131, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10133
    values (10131, 10038, 1, 'comment');
--------------------------------------------------------------------

------------Сидельников Федер-----------------------------------
insert into orders (user_id, date_time, table_id, check_amount, status) --10134
    values (10002, '2020-11-20 13:00:00', 10067, 750, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10135
    values (10134, 10028, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10136
    values (10134, 10024, 1, 'comment');

insert into orders (user_id, date_time, table_id, check_amount, status) --10137
    values (10002, '2020-11-20 22:00:00', 10075, 600, 'CLOSED');

insert into order_dishes (order_id, dish_id, amount, comment)   --10138
    values (10137, 10030, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)   --10139
    values (10137, 10038, 1, 'comment');
----------------------------------------------------------------
------------------------------------------------------------------------
-----------------------------------------------------------------------------
