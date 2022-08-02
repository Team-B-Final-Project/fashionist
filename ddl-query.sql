create table public.role (
"id" serial4 not null,
"name" varchar(50) not null,
constraint "role_pkey" primary key ("id")
);

CREATE TABLE public.profile_picture (
    "id" bigserial not null,
    "name" varchar NOT NULL,
    "type" varchar NOT NULL,
    "size" bigint NOT NULL,
    "data" bytea NOT NULL,
    CONSTRAINT "profile_picture_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.product_picture (
    "id" serial4 not null,
    "product_id" bigint not null,
    "level" int4 not null,
    "name" varchar not null,
    "type" varchar not null,
    "size" bigint not null,
    "data" bytea not null,
    constraint "product_picture_pkey" primary key ("id")
);

CREATE TABLE public.payment (
"id" serial4 NOT NULL,
"name" varchar NOT NULL,
CONSTRAINT "payment_pkey" PRIMARY KEY("id")
);

CREATE TABLE public.transaction_status (
    "id" bigserial not null,
    "name" varchar not null,
    constraint "transaction_status_pkey" primary key ("id")
);

CREATE TABLE public.shipping (
    "id" serial4 not null,
    "name" varchar not null,
    constraint "shipping_pkey" primary key ("id")
);

CREATE TABLE public.category (
    "id" bigserial not null,
    "user_id" bigint not null,
    "address_id" bigint not null,
    "phone_number" varchar(50) not null,
    "created_at" timestamp not null default NOW(),
    "updated_at" timestamp not null default NOW(),
    "created_by" varchar not null,
    "updated_by" varchar not null,
    constraint "category_pkey" primary key ("id")

);

CREATE TABLE public.address (
    "id" bigserial,
    "name" varchar(50) NOT NULL,
    "phone_number" char(12) NOT NULL,
    "province" varchar(50) NOT NULL,
    "city" varchar(50) NOT NULL,
    "district" varchar(50) NOT NULL,
    "full_address" varchar NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "creatd_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "address_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.user (
    "id" bigserial,
    "profile_picture_id" bigint NULL,
    "first_name" varchar(50) NOT NULL,
    "last_name" varchar(50) NULL,
    "username" varchar(20) NOT NULL,
    "email" varchar(50) NOT NULL,
    "phone_number" char(12) NOT NULL,
    "password" varchar NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "creatd_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "user_pkey" PRIMARY KEY ("id")
);

CREATE TABLE public.user_address (
    "user_id" bigint NOT NULL,
    "address_id" bigint NOT NULL,
    CONSTRAINT "user_address_pkey" PRIMARY KEY ("user_id", "address_id")
);

CREATE TABLE public.store (
    "id" bigserial,
    "user_id" bigint NOT NULL,
    "name" varchar(50) NOT NULL,
    "address_id" bigint NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT NOW(),
    "updated_at" timestamp NOT NULL DEFAULT NOW(),
    "creatd_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "store_pkey" PRIMARY KEY ("id")
    
);

CREATE TABLE public.user_role (
"id" serial4 NOT NULL,
"name" varchar NOT NULL,
CONSTRAINT "user_role_pkey" PRIMARY KEY("id")
);

CREATE TABLE public.product (
    "id" bigserial NOT NULL,
    "store_id" bigint NOT NULL,
    "name" varchar(50) NOT NULL,
    "description" varchar NOT NULL,
    "price" float NOT NULL,
    "stock" int NOT NULL,
    "category_id" int4 NOT NULL,
    "created_at" timestamp NOT null default NOW(),
    "updated_at" timestamp NOT null default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "product_pkey" PRIMARY key ("id")

);

CREATE TABLE public.cart (
    "id" bigint not null,
    "user_id" bigint not null,
    "product_id" bigint not null,
    "item_unit" int4 not null,
    "total_price" float not null,
    "created_at" timestamp not null default NOW(),
    "updated_at" timestamp not null default NOW(),
    "created_by" varchar not null,
    "updated_by" varchar not null,
    constraint "cart_pkey" primary key ("id")
);

CREATE TABLE public.transaction (
    "id" bigint not null,
    "tota_price" float not null,
    "total_item_unit" int4 not null,
    "user_id" bigint not null,
    "send_address_id" bigint not null,
    "payment_id" bigint not null,
    "transaction_status_id" bigint not null,
    "receipt" varchar not null,
    "created_at" timestamp not null default NOW(),
    "updated_at" timestamp not null default NOW(),
    "created_by" varchar not null,
    "updated_by" varchar not null,
    constraint "transaction_pkey" primary key ("id")
);

CREATE TABLE public.product_transaction (
    "id" bigserial NOT NULL,
    "transaction_id" bigint NOT NULL,
    "product_id" bigint NOT NULL,
    "item_unit" int4 NOT NULL,
    "total_price" float NOT NULL,
    "shipping_id" int4 NOT NULL,
    "created_at" timestamp NOT NULL default NOW(),
    "updated_at" timestamp NOT NULL default NOW(),
    "created_by" varchar NOT NULL,
    "updated_by" varchar NOT NULL,
    CONSTRAINT "product_transaction_pkey" PRIMARY KEY ("id")

);

create table public.message (
    "id" bigserial not null,
    "sender" bigint not null,
    "receiver" bigint not null,
    "title" varchar(50) not null,
    "text" varchar not null,
    "is_read" boolean not null,
    "created_at" timestamp not null default NOW(),
    "updated_at" timestamp not null default NOW(),
    "created_by" varchar not null,
    "updated_by" varchar not null,
    constraint "message_pkey" primary key ("id")

);

ALTER TABLE public.product_picture
ADD CONSTRAINT "product_fkey"
FOREIGN KEY("product_id")
REFERENCES public.product("id");

ALTER TABLE public.category
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

ALTER TABLE public.category
ADD CONSTRAINT "address_fkey"
FOREIGN KEY("address_id")
REFERENCES public.address("id");

ALTER TABLE public.user
ADD CONSTRAINT "profile_picture_fkey"
FOREIGN KEY("profile_picture_id")
REFERENCES public.profile_picture("id");

ALTER TABLE public.user_address
ADD CONSTRAINT "user_fkey"
FOREIGN KEY("user_id")
REFERENCES public.user("id");

ALTER TABLE public.user_address
ADD CONSTRAINT "address_fkey"
FOREIGN KEY("address_id")
REFERENCES public.address("id");

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
