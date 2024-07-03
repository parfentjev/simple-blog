CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public.post (
	id uuid DEFAULT uuid_generate_v4() NOT NULL,
	title varchar(128) NULL,
	summary text NULL,
	"text" text NULL,
	"date" timestamp NULL,
	visible boolean NULL,
	CONSTRAINT post_pk PRIMARY KEY (id)
);

CREATE TABLE public.user (
	id uuid DEFAULT uuid_generate_v4() NOT NULL,
	username varchar(128) NOT NULL,
	password varchar(128) NOT NULL,
	active boolean NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);
