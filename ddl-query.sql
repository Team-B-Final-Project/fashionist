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