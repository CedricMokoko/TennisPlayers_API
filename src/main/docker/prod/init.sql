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

ALTER TABLE IF EXISTS public.player OWNER to u1sjlg3madur15gm5iqu;

INSERT INTO public.player(nome, cognome, birth_date, punti, rank) VALUES
        ('Cédric', 'Mokoko', '1997-03-04', 10000, 1),
    	('Djokovic', 'Novak', '1987-05-22', 9855, 2),
        ('Alcaraz', 'Carlos', '2003-05-05', 8805, 3),
        ('Jannik', 'Sinner', '2001-08-16', 8270, 4),
        ('Daniil', 'Medvedev', '1996-02-11', 8015, 5),
        ('Andrey', 'Rublev', '1997-10-20', 5110, 6),
        ('Alexander', 'Zverev', '1997-04-20', 5085,7),
        ('Holger', 'Rune', '2003-04-29', 3700, 8),
        ('Hubert', 'Hurkacz', '1997-02-11', 3395, 9),
        ('Alex', 'de Minaur', '1999-02-17', 3210, 10);

 CREATE SEQUENCE user_api_id_seq;

 CREATE TABLE user_api (
     id integer NOT NULL DEFAULT nextval('user_api_id_seq'),
     login character varying(50) NOT NULL,
     password character varying(60) NOT NULL,
     nome character varying(50) NOT NULL,
     cognome character varying(50) NOT NULL,
     PRIMARY KEY (id)
 );

 ALTER SEQUENCE user_api_id_seq OWNED BY user_api.id;

 ALTER TABLE IF EXISTS public.user_api OWNER to u1sjlg3madur15gm5iqu;

 INSERT INTO public.user_api(login, password, nome, cognome) VALUES
 ('admin', '$2a$12$VLMmCnWg6g1ZWfctUUYpWeyfArfbPzlq1EC1hi5BPSQeJWMwjmpdy', 'Cédric', 'Mokoko'),
 ('visitor', '$2a$12$ACcMbD/j30wmsucWNZpMaeJaO2w0tBIswOzDMOjZhVvEp6RzPhgWS', 'Cédric', 'Bayito');

 CREATE TABLE role
 (
     nome character varying(50) NOT NULL,
     PRIMARY KEY (nome)
 );

 INSERT INTO public.role(nome) VALUES
 ('ROLE_ADMIN'),
 ('ROLE_USER');

ALTER TABLE IF EXISTS public.role OWNER to u1sjlg3madur15gm5iqu;

CREATE TABLE user_api_role
(
    user_api_id bigint NOT NULL,
    role_nome character varying(50) NOT NULL,
    CONSTRAINT user_api_role_pkey PRIMARY KEY (user_api_id, role_nome),
        CONSTRAINT fk_role_nome FOREIGN KEY (role_nome)
            REFERENCES public.role (nome),
        CONSTRAINT fk_user_api_id FOREIGN KEY (user_api_id)
            REFERENCES public.user_api (id)
);

ALTER TABLE IF EXISTS public.user_api_role OWNER to u1sjlg3madur15gm5iqu;

INSERT INTO public.user_api_role(user_api_id, role_nome) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER');
