drop function if exists init_db();
drop function if exists drop_all();

create function drop_all() returns bool as '
begin
    drop table if exists order_dishes;
    drop table if exists order_discount;
    drop table if exists dishes_modifiers;
    drop table if exists modifiers;
    drop table if exists dishes;
    drop table if exists dish_categories;
    drop table if exists user_roles;
    drop table if exists orders;
    drop table if exists user_shifts;
    drop table if exists users;
    drop sequence if exists global_seq;
    drop owned by waiter;
    drop owned by manager;
    drop role if exists waiter;
    drop role if exists manager;
    return true;
end;
' language plpgsql;

create function init_db() returns void as '
begin
    if (select drop_all() = true) then
        create sequence global_seq start with 10000;

        create table users
        (
            id   integer primary key default nextval(''global_seq''),
            key  varchar not null check ( length(key) >= 4 and length(key) <= 10 ),
            name varchar not null check ( length(name) >= 2 and length(name) <= 100 )
        );

        create unique index users_unique_key_idx on users (key);

        create table user_roles
        (
            user_id integer not null,
            role    varchar not null,
            constraint user_roles_unique_idx unique (user_id, role),
            foreign key (user_id) references users (id) on delete cascade
        );

        create table user_shifts
        (
            id              integer primary key default nextval(''global_seq''),
            user_id         integer   not null,
            start_date_time timestamp not null  default now(),
            end_date_time   timestamp check ( end_date_time >= start_date_time ),
            foreign key (user_id)  references users (id) on delete cascade
        );

        create unique index user_shifts_user_id_start_date on user_shifts(user_id, date(start_date_time));

        create table dish_categories
        (
            id          integer primary key default nextval(''global_seq''),
            name        varchar not null,
            category_id integer,
            foreign key (category_id) references dish_categories (id) on delete cascade
        );

        create table dishes
        (
            id          integer primary key default nextval(''global_seq''),
            name        varchar not null,
            category_id integer,
            cost        integer not null default 0 check ( cost >= 0 ),
            foreign key (category_id) references dish_categories (id) on delete cascade
        );

        create table modifiers
        (
            id        integer primary key default nextval(''global_seq''),
            name      varchar not null,
            min_value integer not null default 1 check ( min_value >= 0 ),
            max_value integer check ( max_value >= min_value )
        );

        create table dishes_modifiers
        (
            dish_id     integer not null,
            modifier_id integer not null,
            constraint  dish_modifiers_unique_idx unique (dish_id, modifier_id),
            foreign key (dish_id)     references dishes    (id) on delete cascade,
            foreign key (modifier_id) references modifiers (id) on delete cascade
        );

        create table orders
        (
            id           integer primary key default nextval(''global_seq''),
            user_id    integer,
            date_time    timestamp not null default now(),
            discount     smallint  not null default 0 check (discount >= 0 and discount <= 100),
            table_number integer   not null,
            status       varchar   not null default ''PROCESSING'',
            foreign key (user_id) references users (id) on delete set null
        );

        create index orders_user_id_date_time_idx on orders (user_id, date_time);

        create table order_dishes
        (
            id       integer primary key default nextval(''global_seq''),
            order_id integer not null,
            dish_id  integer,
            amount   integer not null default 1 check ( amount > 0 ),
            comment  text,
            foreign key (order_id) references orders (id) on delete cascade,
            foreign key (dish_id)  references dishes (id) on delete set null
        );

        create role manager with createrole;
        create role waiter;

        grant usage on schema public to waiter;

        grant select (id, name) on table users to waiter;
        grant select on table user_roles, user_shifts, dishes, dish_categories, modifiers, dishes_modifiers, orders, order_dishes to waiter;

        grant insert on table user_shifts, orders, order_dishes to waiter;

        grant update (end_date_time) on table user_shifts to waiter;
        grant update (table_number, status) on table orders to waiter;
        grant update (order_id) on table order_dishes to waiter;
        grant update (name, key) on table users to waiter;


        grant usage on schema public to waiter;
        grant select (id, name) on table users to waiter;

        grant select on table user_roles, user_shifts, dishes, dish_categories, modifiers, dishes_modifiers, orders, order_dishes to manager;

        grant insert on table users, user_shifts, user_roles, orders, order_dishes, modifiers, dishes_modifiers, dishes, dish_categories to manager;

        grant update (end_date_time) on table user_shifts to waiter;
        grant update (user_id, table_number, status, discount) on table orders to manager;
        grant update (order_id, dish_id, amount, comment) on table order_dishes to manager;
        grant update (name, min_value, max_value) on table modifiers to manager;
        grant update (name, category_id, cost) on table dishes to manager;
        grant update (name, category_id) on table dish_categories to manager;
        grant update (name, key) on table users to manager;

        grant delete on table users, user_shifts, user_roles, orders, order_dishes, modifiers, dishes_modifiers, dishes, dish_categories to manager;
    end if;
end;
    ' language plpgsql;

select init_db();