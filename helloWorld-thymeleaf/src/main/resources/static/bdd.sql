create table Region (
	id serial primary key,
	nom varchar(50)
);
CREATE SEQUENCE sqRegion
START 1
INCREMENT 1
OWNED BY Region.id;

INSERT INTO Region (nom) VALUES ('Diana');
INSERT INTO Region (nom) VALUES ('Sava');
INSERT INTO Region (nom) VALUES ('Itasy');
INSERT INTO Region (nom) VALUES ('Analamanga');
INSERT INTO Region (nom) VALUES ('Vakinankaratra');
INSERT INTO Region (nom) VALUES ('Bongolava');
INSERT INTO Region (nom) VALUES ('Sofia');
INSERT INTO Region (nom) VALUES ('Boeny');
INSERT INTO Region (nom) VALUES ('Betsiboka');
INSERT INTO Region (nom) VALUES ('Melaky');
INSERT INTO Region (nom) VALUES ('Alaotra-Mangoro');
INSERT INTO Region (nom) VALUES ('Atsinanana');

create table LoginFront (
	id serial primary key,
    idRegion int,
	nom varchar(50),
    mdp varchar(50),
    FOREIGN KEY (idRegion) REFERENCES Region (id)
);

INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (1,'Rabe','Diana');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (2,'Rakoto','Sava');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (3,'Faly','Itasy');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (4,'Mamy','Analamanga');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (5,'Soanaivo','Vakinankaratra');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (6,'Nary','Bongolava');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (7,'Sandratana','Sofia');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (8,'Mino','Boeny');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (9,'Cristelle','Betsiboka');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (10,'Finaritra','Melaky');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (11,'Doda','Alaotra-Mangoro');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (12,'Basy','Atsinanana');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (13,'Koto','Analanjirofo');
INSERT INTO LoginFront (idRegion,nom,mdp) VALUES (14,'Maya','Amoron i Mania');

create table LoginPerson (
	id serial primary key,
    email varchar(50),
    mdp varchar(50),
	nom varchar(50),
    age int
);


create table Type (
	id serial primary key,
    intitule varchar(50)
);


create table Signalement (
	id serial primary key,
    idType int,
    idRegion int,
    titre varchar(50),
    image varchar(50),
    longitude float,
    latitude float,
    description varchar(50),
    FOREIGN KEY (idType) REFERENCES Type (id),
    FOREIGN KEY (idRegion) REFERENCES Region (id)

);


create table Notif (
	idSignalement int,
    FOREIGN KEY (idSignalement) REFERENCES Signalement (id)
);