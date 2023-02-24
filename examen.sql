insert into category values (1, "Mueble", "Mueble");
insert into category values (2, "Herramienta", "Herramienta");
insert into category values (3, "Comida", "Comida");

insert into user values (1, "1997-03-12", "user@gmail.com", "password", "usuario1");

select P.* from cart_item CI left join product P on CI.product_id = P.product_id where CI.user_id = 1;
select CI.cart_item_id from cart_item CI left join product P on CI.product_id = P.product_id where CI.user_id = 1 and P.name = "Mesa pequeña";

select * from cart_item;
select * from user;
select * from category;
select * from product;