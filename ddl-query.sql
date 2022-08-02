CREATE TABLE public.transaction (
    "id" bigint not null,
    "tota_price" float not null,
    "total_item_unit" int4 not null,
    "user_id" bigint not null,
    "send_address_id" bigint not null,
    "payment_id bigint" bigint not null,
    "transaction_status_id" bigint not null,
    "receipt" varchar not null,
    "created_at" timestamp not null default NOW(),
    "updated_at" timestamp not null default NOW(),
    "created_by" varchar not null,
    "updated_by" varchar not null,
    PRIMARY KEY(id),
    CONSTRAINT fkey_user_id
    FOREIGN KEY("user_id")
    REFERENCES public.user("id"),
    CONSTRAINT fkey_send_address_id
    FOREIGN KEY("send_address_id")
    REFERENCES public.address("id"),
    CONSTRAINT fkey_payment_id
    FOREIGN KEY("payment_id")
    REFERENCES public.payment("id"),
    CONSTRAINT fkey_transaction_status_id
    FOREIGN KEY ("transaction_status_id")
    REFERENCES public.transaction_status("id")
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
    PRIMARY KEY(id),
    CONSTRAINT fkey_user_id
    FOREIGN KEY("user_id")
    REFERENCES public.user("id"),
    CONSTRAINT fkey_address_id
    FOREIGN KEY("address_id")
    REFERENCES public.address("id")
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
    PRIMARY KEY(id),
    CONSTRAINT fkey_user_id
    FOREIGN KEY("user_id")
    REFERENCES public.user("id"),
    CONSTRAINT fkey_product_id
    FOREIGN KEY("product_id")
    REFERENCES public.product("id")
);

CREATE TABLE public.transaction_status (
    "id" bigserial not null,
    "name" varchar not null,
    PRIMARY KEY(id)
);

CREATE TABLE public.shipping (
    "id" serial4 not null,
    "name" varchar not null,
    PRIMARY KEY(id)
);

CREATE TABLE public.product_picture (
    "id" serial4 not null,
    "product_id" bigint not null,
    "level" int4 not null,
    "name" varchar not null,
    "type" varchar not null,
    "size" bigint not null,
    "data" bytea not null,
    PRIMARY KEY(id),
    CONSTRAINT fkey_product_id
    FOREIGN KEY("product_id")
    REFERENCES public.product("id")
);
