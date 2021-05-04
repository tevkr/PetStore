create table if not exists pets
(
    id SERIAL PRIMARY KEY ,
    name TEXT
);

create table if not exists product_types
(
    id serial primary key,
  	pet_id integer,
    name TEXT
);

create table if not exists product_types_detailed
(
    id serial primary key,
  	pet_id integer,
  	product_type_id integer,
    name text
);

create table if not exists products
(
    id serial primary key,
  	pet_id integer,
  	product_type_id integer,
  	product_type_detailed_id integer,
    name text,
  	price integer,
  	weight text,
  	product_details text,
  	description text
);