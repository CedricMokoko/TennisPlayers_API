CREATE SEQUENCE player_id_seq;

CREATE TABLE player
(
    id integer NOT NULL DEFAULT nextval('player_id_seq'),
    nome character varying(50) NOT NULL,
    cognome character varying(50) NOT NULL,
    birth_date date NOT NULL,
    punti integer NOT NULL,
    rank integer NOT NULL,
    PRIMARY KEY (id)
);

ALTER SEQUENCE player_id_seq OWNED BY player.id;

ALTER TABLE IF EXISTS public.player OWNER to postgres;

INSERT INTO public.player(nome, cognome, birth_date, punti, rank)
        VALUES ('Cédric', 'Mokoko', '1997-03-04', 5000, 1);

INSERT INTO public.player(nome, cognome, birth_date, punti, rank)
    	VALUES ('Novak', 'Djokovic', '1987-05-22', 4000, 2);

INSERT INTO public.player(nome, cognome, birth_date, punti, rank)
    	VALUES ('Roger', 'Federer', '1981-08-08', 3000, 3);

INSERT INTO public.player(nome, cognome, birth_date, punti, rank)
    	VALUES ('Andy', 'Murray', '1987-05-15', 2000, 4);

INSERT INTO public.player(nome, cognome, birth_date, punti, rank)
    	VALUES ('Rafael', 'Nadal', '1986-06-03', 1500, 5);



CREATE SEQUENCE user_api_id_seq;

CREATE TABLE user_api (
    id integer NOT NULL DEFAULT nextval('user_api_id_seq'),
    login character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    nome character varying(50) NOT NULL,
    cognome character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

ALTER SEQUENCE user_api_id_seq OWNED BY player.id;

ALTER TABLE IF EXISTS public.user_api OWNER to postgres;

INSERT INTO public.user_api(login, password, nome, cognome)
	VALUES ('admin', '$2a$12$RkcdJn2kLrAS9fmvDv/CWehqID8nB3XBWXOtazhQ2PY1ZFwDB3L76', 'Cédric', 'Mokoko');

INSERT INTO public.user_api(login, password, nome, cognome)
	VALUES ('user', '$2a$12$VRnUGZfeEsWHG9jb7NyvQuhpISK65N2LtWyqXAi5t1CBWIQ34uRNa', 'Cédric', 'Bayito');



CREATE TABLE role
(
    nome character varying(50) NOT NULL,
    PRIMARY KEY (nome)
);

INSERT INTO public.role(nome)
	VALUES ('ROLE_ADMIN');

INSERT INTO public.role(nome)
	VALUES ('ROLE_USER');

ALTER TABLE IF EXISTS public.role OWNER to postgres;



CREATE TABLE user_api_role
(
    user_api_id bigint NOT NULL,
    role_nome character varying(50) NOT NULL,
    CONSTRAINT user_api_role_pkey PRIMARY KEY (user_api_id, role_nome),
    CONSTRAINT fk_role_nome FOREIGN KEY (role_nome)
        REFERENCES public.role(nome),
    CONSTRAINT fk_user_api_id FOREIGN KEY (user_api_id)
        REFERENCES public.user_api (id)
);

ALTER TABLE IF EXISTS public.user_api_role OWNER to postgres;

INSERT INTO public.user_api_role(user_api_id, role_nome)
	VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.user_api_role(user_api_id, role_nome)
	VALUES (1, 'ROLE_USER');

INSERT INTO public.user_api_role(user_api_id, role_nome)
	VALUES (2, 'ROLE_USER');