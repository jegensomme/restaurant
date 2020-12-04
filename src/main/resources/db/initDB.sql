drop table if exists order_dishes;
drop table if exists order_discount;
drop table if exists dishes;
drop table if exists dish_categories;
drop table if exists user_roles;
drop table if exists orders;
drop table if exists tables;
drop table if exists user_shifts;
drop table if exists users;
drop sequence if exists global_seq;

create sequence global_seq start with 10000;

create table users
(
    id    integer primary key default nextval('global_seq'),
    key   varchar not null check ( length(key) >= 4 and length(key) <= 10 ),
    name  varchar not null check ( length(name) >= 2 and length(name) <= 100 )
);

create unique index users_unique_key_idx on users (key);

create table user_roles
(
    user_id integer not null,
    role    varchar not null,
    foreign key (user_id) references users (id) on delete cascade,
    primary key (user_id, role)
);

create table user_shifts
(
    id         integer primary key default nextval('global_seq'),
    user_id    integer   not null,
    date       date      not null,
    start_time time      not null,
    end_time   time check ( end_time >= start_time ),
    foreign key (user_id)  references users (id) on delete cascade
);

create unique index user_shifts_unique_idx on user_shifts (user_id, date);

create table dish_categories
(
    id          integer primary key default nextval('global_seq'),
    name        varchar not null,
    category_id integer,
    foreign key (category_id) references dish_categories (id) on delete set null
);

create index dish_categories_category_id_idx on dish_categories (category_id);

create table dishes
(
    id          integer primary key default nextval('global_seq'),
    name        varchar not null,
    category_id integer,
    cost        real not null default 0 check ( cost >= 0 ),
    foreign key (category_id) references dish_categories (id) on delete set null
);

create index dishes_category_id_idx on dishes (category_id);

create table tables
(
    id     integer primary key default nextval('global_seq'),
    number smallint not null,
    seats  smallint not null default 1 check ( seats >= 1 ),
    constraint tables_unique_idx  unique (number)
);

create table orders
(
    id           integer primary key default nextval('global_seq'),
    user_id      integer,
    date_time    timestamp not null default now(),
    discount     smallint  not null default 0 check (discount >= 0 and discount <= 100),
    check_amount real      not null default 0 check ( check_amount >= 0 ),
    table_id     integer,
    status       varchar   not null default 'PROCESSING',
    foreign key (user_id) references users (id) on delete set null,
    foreign key (table_id) references tables (id) on delete set null
);

create table order_dishes
(
    id       integer primary key default nextval('global_seq'),
    order_id integer not null,
    dish_id  integer,
    amount   integer not null default 1 check ( amount > 0 ),
    comment  text,
    foreign key (order_id) references orders (id) on delete cascade,
    foreign key (dish_id)  references dishes (id) on delete set null
);

create index order_dishes_idx on order_dishes (order_id);

create role manager with createrole login password 'password';
create role waiter with login password 'password';

grant usage on schema public to waiter;

grant select, update on sequence global_seq to waiter;

grant select on table users, user_roles, user_shifts, dishes, dish_categories, orders, tables, order_dishes to waiter;

grant insert on table user_shifts, orders, order_dishes to waiter;

grant update on table user_shifts to waiter;
grant update on table orders to waiter;
grant update on table users to waiter;


grant usage on schema public to manager;

grant select, update on sequence global_seq to manager;

grant select on table users, user_roles, user_shifts, dishes, dish_categories, tables, orders, order_dishes to manager;

grant insert on table users, user_shifts, user_roles, tables, order_dishes, dishes, dish_categories to manager;

grant update on table user_shifts to manager;
grant update on table tables to manager;
grant update on table orders to manager;
grant update on table dishes to manager;
grant update on table dish_categories to manager;
grant update on table users to manager;

grant delete on table users, user_shifts, user_roles, orders, tables, order_dishes , dishes, dish_categories, tables to manager;