CREATE TABLE public.role (
    "id" serial4 NOT NULL,
    "name" varchar(50) NOT NULL,
    CONSTRAINT "role_pkey" primary key ("id")
);

CREATE TABLE public.profile_picture (
    "id" bigserial NOT NULL,
    "name" varchar NOT NULL,
    "type" varchar NOT NULL,
    "size" bigint NOT NULL,
    "data" bytea NOT NULL,
    CONSTRAINT "profile_picture_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.product_picture (
    "id" bigserial NOT NULL,
    "product_id" bigserial NOT NULL,
    "level" int4 NOT NULL,
    "name" varchar NOT NULL,
    "type" varchar NOT NULL,
    "size" bigint NOT NULL,
    "data" bytea NOT NULL,
    CONSTRAINT "product_picture_pkey" primary key ("id")
);

CREATE TABLE public.payment (
    "id" serial4 NOT NULL,
    "name" varchar NOT NULL,
    CONSTRAINT "payment_pkey" PRIMARY KEY("id")
);

CREATE TABLE public.transaction_status (
    "id" bigserial NOT NULL,
    "name" varchar NOT NULL,
    CONSTRAINT "transaction_status_pkey" primary key ("id")
);

CREATE TABLE public.shipping (
    "id" serial4 NOT NULL,
    "name" varchar NOT NULL,
    CONSTRAINT "shipping_pkey" primary key ("id")
);

CREATE TABLE public.category (
    "id" bigserial NOT NULL,
    "name" varchar(50) NOT NULL,
    "slug" varchar NOT NULL,
    CONSTRAINT "category_pkey" primary key ("id")
);

CREATE TABLE public.address (
    "id" bigserial NOT NULL,
    "user_id" bigserial NOT NULL,
    "name" varchar(50) NOT NULL,
    "phone_number" char(12) NOT NULL,
    "province" varchar(50) NOT NULL,
    "city" varchar(50) NOT NULL,
    "district" varchar(50) NOT NULL,
    "full_address" varchar NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "address_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.user (
    "id" bigserial NOT NULL,
    "profile_picture_id" bigserial NOT NULL,
    "first_name" varchar(50) NOT NULL,
    "last_name" varchar(50) NULL,
    "username" varchar(20) NOT NULL,
    "email" varchar(50) NOT NULL,
    "phone_number" char(12) NOT NULL,
    "password" varchar NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "user_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.store (
    "id" bigserial,
    "user_id" bigserial NOT NULL,
    "name" varchar(50) NOT NULL,
    "address_id" bigserial NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "store_pkey" PRIMARY KEY ("id")
    
);

CREATE TABLE public.user_role (
    "user_id" bigserial NOT NULL,
    "role_id" serial4 NOT NULL,
    CONSTRAINT "user_role_pkey" PRIMARY KEY("user_id", "role_id")
);

CREATE TABLE public.product (
    "id" bigserial NOT NULL,
    "store_id" bigserial NOT NULL,
    "name" varchar(50) NOT NULL,
    "description" varchar NOT NULL,
    "price" float NOT NULL,
    "stock" int NOT NULL,
    "category_id" int4 NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "product_pkey" PRIMARY key ("id")

);

CREATE TABLE public.cart (
    "id" bigserial NOT NULL,
    "user_id" bigserial NOT NULL,
    "product_id" bigserial NOT NULL,
    "item_unit" int4 NOT NULL,
    "total_price" float NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "cart_pkey" primary key ("id")
);

CREATE TABLE public.transaction (
    "id" bigserial NOT NULL,
    "tota_price" float NOT NULL,
    "total_item_unit" int4 NOT NULL,
    "user_id" bigserial NOT NULL,
    "send_address_id" bigserial NOT NULL,
    "payment_id" serial4 NOT NULL,
    "transaction_status_id" bigserial NOT NULL,
    "receipt" varchar NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "transaction_pkey" primary key ("id")
);

CREATE TABLE public.product_transaction (
    "id" bigserial NOT NULL,
    "transaction_id" bigserial NOT NULL,
    "product_id" bigserial NOT NULL,
    "item_unit" int4 NOT NULL,
    "total_price" float NOT NULL,
    "shipping_id" int4 NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "product_transaction_pkey" PRIMARY KEY ("id")

);

CREATE TABLE public.message (
    "id" bigserial NOT NULL,
    "sender" bigserial NOT NULL,
    "receiver" bigserial NOT NULL,
    "title" varchar(50) NOT NULL,
    "text" varchar NOT NULL,
    "is_read" boolean NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "message_pkey" primary key ("id")
);

CREATE TABLE wishlist (
    "id" serial4 NOT NULL,
    "user_id" bigserial NOT null,
    "product_id" bigserial NOT null,
    CONSTRAINT "wishlist_pkey" primary key ("id")
);

CREATE TABLE reset_password_token (
    "id" serial4 NOT NULL,
    "email_address" varchar(50) NOT NULL,
    "token" uuid NOT NULl,
    CONSTRAINT "reset_password_token_pkey" primary key ("id")	
);

CREATE TABLE public.review(
"id" bigserial NOT NULL,
"rating" float NOT NULL,
"comment" varchar NOT NULL,
"user_id" bigserial NOT NULL,
"product_id" bigserial NOT NULL,
constraint "review_pkey" primary key ("id")
);

ALTER TABLE public.review
add constraint "user_id_fkey"
foreign key("user_id")
references public.user("id");

ALTER TABLE public.review
add constraint "product_id_fkey"
foreign key("product_id")
references public.product("id");

ALTER TABLE public.reset_password_token
ADD CONSTRAINT "email_fkey"
FOREIGN KEY("email_address")
REFERENCES public.user("email");

ALTER TABLE public.product_picture
ADD CONSTRAINT "product_fkey"
FOREIGN KEY("product_id")
REFERENCES public.product("id");

ALTER TABLE public.user
ADD CONSTRAINT "profile_picture_fkey"
FOREIGN KEY("profile_picture_id")
REFERENCES public.profile_picture("id");


ALTER TABLE public.store
ADD CONSTRAINT "address_fkey"
FOREIGN KEY("address_id")
REFERENCES public.address("id");

ALTER TABLE public.product
ADD CONSTRAINT "store_fkey"
FOREIGN KEY("store_id")
REFERENCES public.store("id");

ALTER TABLE public.product
ADD CONSTRAINT "category_fkey"
FOREIGN KEY("category_id")
REFERENCES public.category("id");

ALTER TABLE public.cart
ADD CONSTRAINT "product_id_fkey"
FOREIGN KEY("product_id")
REFERENCES public.product("id");

ALTER TABLE public.cart
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

ALTER TABLE public.transaction
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

ALTER TABLE public.transaction
ADD CONSTRAINT "send_address_fkey"
FOREIGN KEY("user_id")
REFERENCES public.address("id");

ALTER TABLE public.transaction
ADD CONSTRAINT "payment_fkey"
FOREIGN KEY("payment_id")
REFERENCES public.payment("id");

ALTER TABLE public.transaction
ADD CONSTRAINT "transaction_status_fkey"
FOREIGN KEY("transaction_status_id")
REFERENCES public.transaction_status("id");

ALTER TABLE public.product_transaction
ADD CONSTRAINT "transaction_fkey"
FOREIGN KEY("transaction_id")
REFERENCES public.transaction("id");

ALTER TABLE public.product_transaction
ADD CONSTRAINT "product_fkey"
FOREIGN KEY("product_id")
REFERENCES public.product("id");

ALTER TABLE public.product_transaction
ADD CONSTRAINT "shipping_fkey"
FOREIGN KEY("shipping_id")
REFERENCES public.shipping("id");

ALTER TABLE public.message
ADD CONSTRAINT "sender_fkey"
FOREIGN KEY("sender")
REFERENCES public.user("id");

ALTER TABLE public.message
ADD CONSTRAINT "receiver_fkey"
FOREIGN KEY("receiver")
REFERENCES public.user("id");

ALTER TABLE public.user_role
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

ALTER TABLE public.user_role
ADD CONSTRAINT "role_fkey"
FOREIGN KEY("role_id")
REFERENCES public.role("id");

ALTER TABLE public.address
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

-- Create tables
create table public.province (
	id int4,
	name varchar,
	constraint province_pkey primary key (id)
);

create table public.regency (
	id int4,
	province_id int4,
	name varchar,
	constraint regency_pkey primary key(id)
);

create table public.district (
	id int4,
	regency_id int4,
	name varchar,
	constraint district_pkey primary key(id)
);

create table public.village (
	id int8,
	district_id int4,
	name varchar,
	postal varchar(50),
	constraint village_pkey primary key(id)
);

-- Add contstraints

alter table public.regency 
add constraint province_fkey 
foreign key(province_id) 
references public.province(id);

alter table public.district
add constraint regency_fkey 
foreign key(regency_id) 
references public.regency(id);

alter table public.village
add constraint district_fkey 
foreign key(district_id) 
references public.district(id);

-- Delete some column in address

ALTER TABLE public.address 
DROP COLUMN province;

ALTER TABLE public.address 
DROP COLUMN city;

ALTER TABLE public.address 
DROP COLUMN district;

-- Add village column in address
ALTER TABLE public.address 
ADD COLUMN village_id int8;

ALTER TABLE public.address 
ADD COLUMN postal_code char(5);

alter table public.address
add constraint village_fkey 
foreign key(village_id) 
references public.village(id);
--------------------------------------------
CREATE TABLE public.product_category (
    "product_id" bigserial NOT NULL,
    "category_id" serial4 NOT NULL,
    CONSTRAINT "product_category_pkey" PRIMARY KEY("product_id", "category_id")
);

ALTER TABLE public.product_category
ADD CONSTRAINT "product_fkey"
FOREIGN KEY("product_id")
REFERENCES public.product("id");

ALTER TABLE public.product_category
ADD CONSTRAINT "category_fkey"
FOREIGN KEY("category_id")
REFERENCES public.category("id");


