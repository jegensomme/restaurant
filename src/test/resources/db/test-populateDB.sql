delete from order_dishes;
delete from dish_modifiers;
delete from modifiers;
delete from dishes;
delete from dish_categories;
delete from user_roles;
delete from orders;
delete from tables;
delete from users;
delete from user_shifts;
drop owned by user_waiter;
drop owned by user_manager;
drop user user_waiter;
drop user user_manager;
alter sequence global_seq restart with 10000;

create user user_waiter with role waiter password 'password';
create user user_manager with role manager password 'password';

insert into users (key, name) values (1111, 'Waiter1');       --10000 Waiter1
insert into users (key, name) values (2222, 'Manager');       --10001 Manager
insert into users (key, name) values (3333, 'Waiter2');       --10002 Waiter2

insert into user_roles (user_id, role) values (10000, 'WAITER');      --Waiter1 WAITER
insert into user_roles (user_id, role) values (10001, 'MANAGER');     --Manager Manager
insert into user_roles (user_id, role) values (10002, 'WAITER');      --Waiter2 WAITER

insert into dish_categories (name, category_id) values ('DishCategory1', null);       --10003 DishCategory1
insert into dish_categories (name, category_id) values ('DishCategory2', null);       --10004 DishCategory2

insert into dishes (category_id, name, cost) VALUES (10003, 'Dish1', 2000);       --10005 Dish1 DishCategory1
insert into dishes (category_id, name, cost) VALUES (10003, 'Dish2', 3000);       --10006 Dish2 DishCategory1

insert into dishes (category_id, name, cost) VALUES (10004, 'Dish3', 2000);       --10007 Dish3 DishCategory2
insert into dishes (category_id, name, cost) VALUES (10004, 'Dish4', 3000);       --10008 Dish4 DishCategory2

insert into tables (number, seats) values (1, 1);       --10009 Table1
insert into tables (number, seats) values (2, 2);       --10010 Table2
insert into tables (number, seats) values (3, 3);       --10011 Table3
insert into tables (number, seats) values (4, 4);       --10012 Table4

insert into orders (user_id, date_time, table_id, status, discount)       --10013 Order1 Waiter1 Table1
values (10000, '2020-10-10 10:00:00', 10009, 'PROCESSING', 0);
insert into orders (user_id, date_time, table_id, status, discount)       --10014 Order2 Waiter2 Table2
values (10002, '2020-10-10 12:00:00', 10010, 'CLOSED', 0);

insert into orders (user_id, date_time, table_id, status, discount)       --10015 Order3 Waiter1 Table3
values (10000, '2020-10-11 10:00:00', 10011, 'CLOSED', 0);
insert into orders (user_id, date_time, table_id, status, discount)       --10016 Order4 Waiter2 Table4
values (10002, '2020-10-11 12:00:00', 10012, 'PROCESSING', 0);

insert into order_dishes (order_id, dish_id, amount, comment)       --10017 Order1 Dish1
values (10013, 10005, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)       --10018 Order1 Dish2
values (10013, 10006, 1, 'comment');

insert into order_dishes (order_id, dish_id, amount, comment)       --10019 Order2 Dish3
values (10014, 10007, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)       --10020 Order2 Dish4
values (10014, 10008, 1, 'comment');

insert into order_dishes (order_id, dish_id, amount, comment)       --10021 Order3 Dish1
values (10015, 10005, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)       --10022 Order3 Dish2
values (10015, 10006, 1, 'comment');

insert into order_dishes (order_id, dish_id, amount, comment)       --10023 Order4 Dish3
values (10016, 10007, 1, 'comment');
insert into order_dishes (order_id, dish_id, amount, comment)       --10024 Order4 Dish4
values (10016, 10008, 1, 'comment');

insert into modifiers (name, min_value, max_value) values ('DishModifier1', 1, 10);       --10025 DishModifier1
insert into modifiers (name, min_value, max_value) values ('DishModifier2', 1, 10);       --10026 DishModifier2

insert into dish_modifiers (dish_id, modifier_id) values (10005, 10025);      -- Dish1 DishModifier1
insert into dish_modifiers (dish_id, modifier_id) values (10006, 10026);      -- Dish2 DishModifier2
insert into dish_modifiers (dish_id, modifier_id) values (10007, 10025);      -- Dish3 DishModifier1
insert into dish_modifiers (dish_id, modifier_id) values (10008, 10026);      -- Dish4 DishModifier2

insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100027 UserShift1 Waiter1
values (10000, '2020-10-10 09:00:00', '2020-10-10 21:00:00');
insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100028 UserShift2 Manager
values (10001, '2020-10-10 11:00:00', '2020-10-10 23:00:00');
insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100029 UserShift3 Waiter2
values (10002, '2020-10-10 11:00:00', '2020-10-10 23:00:00');

insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100030 UserShift4 Waiter1
values (10000, '2020-10-11 11:00:00', null);
insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100031 UserShift5 Manager
values (10001, '2020-10-11 11:00:00', '2020-11-10 23:00:00');
insert into user_shifts (user_id, start_date_time, end_date_time)       -- 100032 UserShift6 Waiter2
values (10002, '2020-10-11 09:00:00', '2020-11-10 21:00:00');
