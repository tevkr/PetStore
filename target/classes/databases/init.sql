create table if not exists pets
(
    id SERIAL PRIMARY KEY ,
    name TEXT
);

create table if not exists product_types
(
    id serial primary key,
  	pet_id integer,
    name TEXT,
    FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE
);

create table if not exists product_types_detailed
(
    id serial primary key,
  	pet_id integer,
  	product_type_id integer,
    name text,
    FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE,
    FOREIGN KEY (product_type_id) REFERENCES product_types (id) ON DELETE CASCADE
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
  	description text,
    FOREIGN KEY (pet_id) REFERENCES pets (id) ON DELETE CASCADE,
    FOREIGN KEY (product_type_id) REFERENCES product_types (id) ON DELETE CASCADE,
    FOREIGN KEY (product_type_detailed_id) REFERENCES product_types_detailed (id) ON DELETE CASCADE
);

create table if not exists users
(
    id serial primary key,
    email text,
    username text,
    password text,
    role text
);

create table if not exists shopping_cart
(
    id serial primary key,
    user_id integer,
    product_id integer,
    product_count integer,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);