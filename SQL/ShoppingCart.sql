create database shoppingCart;

use shoppingCart;

create table purchase_order (
    order_id int auto_increment not null,
    name varchar(64) not null,
    email varchar(64) not null,
    primary key (order_id)
);

create table line_items (
    item_id int auto_increment not null,
    description varchar(64),
    quantity int,
    unit_price decimal (14, 4),
    order_id int,
    primary key (item_id),
    constraint fk_order_id
        foreign key(order_id)
        references purchase_order(order_id)
);