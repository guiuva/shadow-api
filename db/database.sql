-- +----------------------------------------------+
-- | GUI SHADOW database schema                   |
-- +----------------------------------------------+
-- | Version  : 2.0                               |
-- | Language : Standard SQL                      |
-- | Date     : Wed Mar 11 2015                   |
-- | Schema   : shadow/2.0                        |
-- | Authors  : David Soler <aensoler@gmail.com>  |
-- +----------------------------------------------+


-- Database Section
-- ________________ 

--create database shadow;
-- ## REPLACE "main" to "shadow" for no SQLite database.

-- Drop Section
-- ____________

DROP TABLE IF EXISTS main.fields_users;
DROP TABLE IF EXISTS main.types;
DROP TABLE IF EXISTS main.fields;
DROP TABLE IF EXISTS main.users_positions;
DROP TABLE IF EXISTS main.users;
DROP TABLE IF EXISTS main.studies;
DROP TABLE IF EXISTS main.positions;

PRAGMA foreign_keys = ON;


-- Creation Section
-- ________________

CREATE TABLE main.studies (
	id   VARCHAR(10)	CONSTRAINT studies_pk PRIMARY KEY,
	name VARCHAR(100)
);

CREATE TABLE main.positions (
	id VARCHAR(100) CONSTRAINT positions_pk PRIMARY KEY
);

CREATE TABLE main.users (
	login      VARCHAR(100) CONSTRAINT users_pk PRIMARY KEY,
	password   VARCHAR(100) CONSTRAINT users_password_nn NOT NULL,
	dni        VARCHAR(10),
	name       VARCHAR(100) CONSTRAINT users_name_nn NOT NULL,
	surname    VARCHAR(100) CONSTRAINT users_surname_nn NOT NULL,
	email      VARCHAR(100) CONSTRAINT users_email_nn NOT NULL,
	phone      VARCHAR(15),
	membership DATE,
	study      VARCHAR(100)
		CONSTRAINT users_study_studies_fk REFERENCES studies(id)
);

CREATE TABLE main.users_positions (
	user_id     VARCHAR(100) CONSTRAINT users_positions_user_id_fk     REFERENCES users(login),
	position_id VARCHAR(100) CONSTRAINT users_positions_position_id_fk REFERENCES positions(id)
);

CREATE TABLE main.types (
	id VARCHAR(100) CONSTRAINT types_pk PRIMARY KEY
);

CREATE TABLE main.fields (
	id      INTEGER      CONSTRAINT fields_id         PRIMARY KEY,
	type_id VARCHAR(100) CONSTRAINT fields_type_id_fk REFERENCES types(id),
	value   VARCHAR(100) CONSTRAINT fields_value
);

CREATE TABLE main.fields_users (
	field_id VARCHAR(100) CONSTRAINT fields_users_field_id_fk REFERENCES fields(id),
	user_id  VARCHAR(100) CONSTRAINT fields_users_user_id_fk  REFERENCES users(login)
);


-- Inserts Section
-- _______________

INSERT INTO main.studies VALUES
	("GII", "Grado en Ingeniería Informática"),
	("GII-IS", "Grado en Ingeniería Informática - Mención de Ingeniería de Software"),
	("GII-TI", "Grado en Ingeniería Informática - Mención en Tecnologías de la Información"),
	("GII-C", "Grado en Ingeniería Informática - Mención en Computación"),
	("GT", "Grado en Telemática"),
	("ITIG", "Ingeniería técnica en Informática de Gestión"),
	("ITIS", "Ingeniería técnica en Informática de Sistemas"),
	("GF", "Grado en Físicas"),
	("Indat", "Grado en Ingeniería Informática y Estadística"),
	("MII", "Master en Ingeniería Informática");

INSERT INTO main.positions VALUES
	("Presidente"),("Vicepresidente"),("Secretario"),("Tesorero"),
	("Vocal"),("Vocal:Administración"),("Vocal:Exteriores"),("Vocal:Sede"),
	("Vocal:Informática"),("Vocal:Servicios"),("Vocal:Desarrollo"),("Vocal:Actividades");

INSERT INTO main.types VALUES
	("Avatar");
