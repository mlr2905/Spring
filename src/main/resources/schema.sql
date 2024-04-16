CREATE TABLE IF NOT EXISTS users (
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username character varying(60) COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default",
    email character varying(320) COLLATE pg_catalog."default" NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_emall_key UNIQUE (email)
        INCLUDE(email),
    CONSTRAINT user_username_key UNIQUE (username),
    CONSTRAINT role_id FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);