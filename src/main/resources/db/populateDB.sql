drop function if exists populate_db();
create function populate_db() returns void as '
    begin
        delete from order_content;
        delete from dish_modifier;
        delete from modifiers;
        delete from dishes;
        delete from dish_categories;
        delete from user_roles;
        delete from orders;
        delete from shift_user_start;
        delete from shift_user_end;
        delete from users;
        delete from shifts;
        drop owned by user_waiter;
        drop owned by user_manager;
        drop user user_waiter;
        drop user user_manager;
        alter sequence user_id_seq  restart with 1;
        alter sequence order_id_seq restart with 1;
        alter sequence shift_id_seq restart with 1;
        alter sequence dish_id_seq  restart with 1;

        create user user_waiter with role waiter password ''password'';
        create user user_manager with role manager password ''password'';

        insert into users (key, name) values (1111, ''Waiter'');
        insert into users (key, name) values (2222, ''Manager'');
        insert into users (key, name) values (3333, ''Waiter2'');

        insert into user_roles (user_id, role) values (1, ''WAITER'');
        insert into user_roles (user_id, role) values (2, ''MANAGER'');
        insert into user_roles (user_id, role) values (3, ''WAITER'');

        insert into dish_categories (name, category_id) values (''Category1'', null);
        insert into dish_categories (name, category_id) values (''Category2'', null);


        insert into dishes (category_id, name, cost) VALUES (1, ''Dish1'', 2000);
        insert into dishes (category_id, name, cost) VALUES (1, ''Dish2'',  3000);

        insert into dishes (category_id, name, cost) VALUES (2, ''Dish3'', 2000);
        insert into dishes (category_id, name, cost) VALUES (2, ''Dish4'',  3000);

        insert into orders (waiter_id, date_time, table_number, status, discount) values (1, ''2020-10-10 10:00:00'', 1, ''PROCESSING'', 0);
        insert into orders (waiter_id, date_time, table_number, status, discount) values (3, ''2020-10-12 12:00:00'', 2, ''PROCESSING'', 0);

        insert into order_content (order_id, dish_id, amount, comment) values (1, 3, 1, ''comment'');
        insert into order_content (order_id, dish_id, amount, comment) values (1, 4, 1, ''comment'');

        insert into order_content (order_id, dish_id, amount, comment) values (2, 5, 1, ''comment'');
        insert into order_content (order_id, dish_id, amount, comment) values (2, 6, 1, ''comment'');
    end;
    ' language plpgsql;

select populate_db();
