CREATE USER IF NOT EXISTS 'bbq_user'@'%' IDENTIFIED BY 'qwerty';
GRANT ALL PRIVILEGES ON *.* TO 'bbq_user'@'%' WITH GRANT OPTION;
CREATE DATABASE IF NOT EXISTS db_products;
CREATE TABLE IF NOT EXISTS db_products.products(
    id bigint unsigned auto_increment primary key,
    is_active tinyint(1)   not null,
    code varchar(191) not null,
    unit_price double not null,
    scope longtext not null,
    constraint code unique (code)
);
INSERT INTO db_products.products (id, is_active, code, unit_price, scope) VALUES
(1, 1, 'MENU0001', 19.9, 'MENU'),
(2, 1, 'MENU0003', 39.9, 'MENU'),
(3, 1, 'MENU0004', 49.9, 'MENU'),
(4, 1, 'MENU0005', 59.9, 'MENU'),
(5, 1, 'MENU0006', 19.9, 'MENU'),
(6, 1, 'MENU0007', 19.9, 'MENU'),
(7, 0, 'MENU0008', 19.9, 'MENU'),
(8, 0, 'MENU0009', 19.9, 'MENU');
CREATE DATABASE IF NOT EXISTS db_menu_options;
CREATE TABLE IF NOT EXISTS db_menu_options.menu_options(
    id bigint auto_increment primary key,
    category varchar(255) null,
    description varchar(255) null,
    product_code varchar(255) null,
    constraint UK_product_code unique (product_code)
);
INSERT INTO db_menu_options.menu_options (description, category, product_code) VALUES
('Costillas de cerdo ahumadas', 'MAIN', 'MENU0001'),
('Pollo a la parrilla', 'MAIN', 'MENU0002'),
('Alitas de pollo BBQ', 'MAIN', 'MENU0003'),
('Maíz asado con mantequilla y especias', 'MAIN', 'MENU0004'),
('Jarra de chicha', 'DRINK', 'MENU0005'),
('Jarra de maracuyá', 'DRINK', 'MENU0006'),
('Torta de chocolate', 'DESSERT', 'MENU0007'),
('Pie de manzana', 'DESSERT', 'MENU0008');