CREATE TABLE public.profile_picture (
    "id" bigserial,
    "name" varchar NOT NULL,
    "type" varchar NOT NULL,
    "size" bigint NOT NULL,
    "data" bytea NOT NULL,
    CONSTRAINT "profile_picture_pkey" PRIMARY KEY ("id")
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
    CONSTRAINT "user_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "username_ukey" UNIQUE ("username"),
    CONSTRAINT "email_ukey" UNIQUE ("email"),
    CONSTRAINT "phone_number_ukey" UNIQUE ("phone_number"),
    CONSTRAINT "profile_picture_fkey" FOREIGN KEY ("profile_picture_id") REFERENCES public.profile_picture("id")
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

CREATE TABLE public.user_address (
    "user_id" bigint NOT NULL,
    "address_id" bigint NOT NULL,
    CONSTRAINT "user_address_pkey" PRIMARY KEY ("user_id", "address_id"),
    CONSTRAINT "user_fkey" FOREIGN KEY ("user_id") REFERENCES public.user("id"),
    CONSTRAINT "address_fkey" FOREIGN KEY ("address_id") REFERENCES public.address("id")
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
    CONSTRAINT "store_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "address_fkey" FOREIGN KEY ("address_id") REFERENCES public.address("id")
);


CREATE TABLE public.product (
"id" bigesrial NOT NULL,
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
CONSTRAINT "product_pkey" PRIMARY key ("id"),
CONSTRAINT "store_fkey" FOREIGN KEY ("store_id") REFERENCE public.store("id"),
CONSTRAINT "category_fkey" FOREIGN KEY ("category_id") REFERENCE public.category("id")
);

CREATE TABLE public.product_transaction (
"id" bigesrial NOT NULL,
"transaction_id" bigint NOT NULL,
"product_id" bigint NOT NULL,
"item_unit" int4 NOT NULL,
"total_price" float NOT NULL,
"shipping_id" int4 NOT NULL,
"created_at" timestamp NOT NULL default NOW(),
"updated_at" timestamp NOT NULL default NOW(),
"created_by" varchar NOT NULL,
"updated_by" varchar NOT NULL,
CONSTRAINT "product_transaction_pkey" PRIMARY KEY ("id"),
CONSTRAINT "transaction_fkey" FOREIGN KEY ("transaction_id") REFERENCE public.transaction ("id"),
CONSTRAINT "product_fkey" FOREIGN KEY ("product_it") REFERENCE public.product ("id"),
CONSTRAINT "shipping_fkey" FOREIGN KEY ("shipping_id") REFERENCE public.shippig ("id")
);

CREATE TABLE public.payment (
"id" serial4 NOT NULL,
"name" varchar NOT NULL,
CONSTRAINT "payment_pkey" PRIMARY KEY("id")
);

CREATE TABLE public.user_role (
"id" serial4 NOT NULL,
"name" varchar NOT NULL,
CONSTRAINT "user_role_pkey" PRIMARY KEY("id");

    create table public.role (
"id" serial4 not null,
"name" varchar(50) not null,
constraint "role_pkey" primary key ("id")
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
constraint "role_pkey" primary key ("id"),
constraint "sender_fkey" foreign key ("id") references public.user("id"),
constraint "receiver_fkey" foreign key ("id") references public.user("id")
);
