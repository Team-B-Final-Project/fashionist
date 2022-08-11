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
    "id" bigserial,
    "profile_picture_id" bigserial NULL,
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
