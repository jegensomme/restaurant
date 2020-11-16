drop function if exists init_db();
drop function if exists drop_all();

create function drop_all() returns bool as '
begin
    drop table if exists order_content;
    drop table if exists order_discount;
    drop table if exists dish_modifier;
    drop table if exists modifiers;
    drop table if exists dishes;
    drop table if exists dish_categories;
    drop table if exists user_roles;
    drop table if exists orders;
    drop table if exists shift_user_start;
    drop table if exists shift_user_end;
    drop table if exists users;
    drop table if exists shifts;
    drop sequence if exists user_id_seq;
    drop sequence if exists order_id_seq;
    drop sequence if exists shift_id_seq;
    drop sequence if exists dish_id_seq;
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
        create sequence shift_id_seq;
        create sequence user_id_seq;
        create sequence order_id_seq;
        create sequence dish_id_seq;

        create table users
        (
            id   integer primary key default nextval(''user_id_seq''),
            key  varchar not null,
            name varchar not null,
            constraint key_length check ( length(key) >= 4 and length(key) <= 10)
        );

        create unique index user_unique_key_idx on users (key);

        create table user_roles
        (
            user_id integer not null,
            role    varchar not null,
            constraint user_roles_unique_idx unique (user_id, role),
            foreign key (user_id) references users (id) on delete cascade
        );

        create table shifts
        (
            id   integer primary key default nextval(''shift_id_seq''),
            date date not null       default current_date
        );

        create table shift_user_start
        (
            shift_id    integer   not null,
            user_id     integer   not null,
            start_shift timestamp not null default now(),
            unique (shift_id, user_id),
            foreign key (shift_id) references shifts (id) on delete cascade,
            foreign key (user_id) references users (id) on delete cascade
        );

        create table shift_user_end
        (
            shift_id  integer   not null,
            user_id   integer   not null,
            end_shift timestamp not null default now(),
            unique (shift_id, user_id),
            foreign key (shift_id) references shifts (id) on delete cascade,
            foreign key (user_id) references users (id) on delete cascade
        );

        create table dish_categories
        (
            id          integer primary key default nextval(''dish_id_seq''),
            name        varchar not null,
            category_id integer,
            foreign key (category_id) references dish_categories (id) on delete cascade
        );

        create table dishes
        (
            id          integer primary key default nextval(''dish_id_seq''),
            name        varchar not null,
            category_id integer,
            cost        integer,
            foreign key (category_id) references dish_categories (id) on delete cascade
        );

        create table modifiers
        (
            id   integer primary key default nextval(''dish_id_seq''),
            name varchar not null
        );

        create table dish_modifier
        (
            dish_id     integer not null,
            modifier_id integer not null,
            min_value   integer not null default 1,
            max_value   integer check ( max_value > min_value ),
            constraint dish_modifier_idx unique (dish_id, modifier_id),
            foreign key (dish_id) references dishes (id) on delete cascade,
            foreign key (modifier_id) references modifiers (id) on delete cascade
        );

        create table orders
        (
            id           integer primary key default nextval(''order_id_seq''),
            waiter_id    integer,
            date_time    timestamp not null  default now(),
            discount     smallint  not null check (discount >= 0 and discount <= 100),
            table_number integer   not null,
            status       varchar   not null,
            foreign key (waiter_id) references users (id) on delete set null
        );

        create table order_content
        (
            order_id integer not null,
            dish_id  integer not null,
            amount   integer not null default 1,
            comment  text    not null,
            foreign key (order_id) references orders (id) on delete cascade,
            foreign key (dish_id) references dishes (id) on delete set null
        );

        create role manager with createrole ;
        create role waiter;

        --         grant usage on schema public to waiter;
        --         grant insert, select on table orders, order_content to waiter;
        --         grant update (table_number) on table orders to waiter;
        --         grant select on table dish_categories, dishes, modifiers, dish_modifier to waiter;
        --         grant select (name) on table users to waiter;
        --         grant select (role) on table user_roles to waiter;
        --         grant insert, select on table shift_user_start, shift_user_end to waiter;
        --
        --         grant usage on schema public to waiter;
        --         grant select on all tables in schema public to manager;
        --         grant insert, update, delete on table dish_categories, dishes, modifiers, dish_modifier to manager;
        --         grant insert, update, delete on table shifts, users, user_roles to manager;
    end if;
end;
    ' language plpgsql;

select init_db();