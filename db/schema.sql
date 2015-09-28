/*
 +----------------------------------------------+
 | GUI SHADOW database schema                   |
 +----------------------------------------------+
 | Version  : 2.1                               |
 | Language : Standard SQL                      |
 | Date     : Sat Sep 19 2015                   |
 | Schema   : shadow/2.1                        |
 | Authors  : David Soler <aensoler@gmail.com>  |
 +----------------------------------------------+


 Database Section
 ________________ 

create database shadow;
 ## REPLACE "main" to "shadow" for no SQLite database.

 Drop Section
 ____________

*/

DROP TABLE IF EXISTS fields_users;
DROP TABLE IF EXISTS fields;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS users_positions;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS studies;
DROP TABLE IF EXISTS positions;

/*PRAGMA foreign_keys = ON;


 Creation Section
 ________________

*/

CREATE TABLE studies (
	id   VARCHAR(10) PRIMARY KEY,
	name VARCHAR(100)
);

CREATE TABLE positions (
	id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE users (
	login      VARCHAR(100) PRIMARY KEY,
	password   VARCHAR(100) NOT NULL,
	dni        VARCHAR(10),
	name       VARCHAR(100) NOT NULL,
	surname    VARCHAR(100) NOT NULL,
	email      VARCHAR(100) NOT NULL,
	phone      VARCHAR(15),
	membership DATE,
	study      VARCHAR(100),
	FOREIGN KEY (study) REFERENCES studies (id)
);

CREATE TABLE users_positions (
	user_id     VARCHAR(100),
	position_id VARCHAR(100),
	FOREIGN KEY (user_id) REFERENCES users (login),
	FOREIGN KEY (position_id) REFERENCES positions (id)
);

CREATE TABLE types (
	id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE fields (
	id      INTEGER PRIMARY KEY,
	type_id VARCHAR(100),
	value   VARCHAR(100),
	FOREIGN KEY (type_id) REFERENCES types (id)
);

CREATE TABLE fields_users (
	field_id INTEGER,
	user_id  VARCHAR(100),
	FOREIGN KEY (field_id) REFERENCES fields (id),
	FOREIGN KEY (user_id) REFERENCES users (login)
);


-- Inserts Section
-- _______________

INSERT INTO studies VALUES
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

INSERT INTO positions VALUES
	("Presidente"),("Vicepresidente"),("Secretario"),("Tesorero"),
	("Vocal"),("Vocal:Administración"),("Vocal:Exteriores"),("Vocal:Sede"),
	("Vocal:Informática"),("Vocal:Servicios"),("Vocal:Desarrollo"),("Vocal:Actividades");

