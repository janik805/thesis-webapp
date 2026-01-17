create table thema
(
    id serial primary key,
    titel varchar(100) not null,
    beschreibung text,
    profil_id integer not null references profil(id)
);

create table thema_link
(
    thema integer not null references thema (id),
    url varchar(300) not null,
    text text,
    primary key (thema, url, text)
);

create table thema_fachgebiet
(
    thema integer not null references thema(id),
    fachgebiet varchar(50) not null references fachgebiet(name),
    primary key(thema, fachgebiet)
);

create table thema_voraussetzung
(
    thema integer not null references thema(id),
    voraussetzung varchar(50) references voraussetzung(voraussetzung),
    primary key(thema, voraussetzung)
);

create table thema_datei_value
(
    thema integer not null references thema(id),
    id varchar(150) primary key,
    name varchar(150),
    beschreibung text
);