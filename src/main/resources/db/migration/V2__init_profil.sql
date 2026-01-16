create table profil
(
    id integer primary key,
    name varchar(50) not null
);

create table profil_kontakt
(
    id serial primary key,
    profil integer not null references profil (id),
    label varchar(100),
    wert varchar(100) not null,
    kontaktart varchar(50)  not null,
    unique (profil, label, wert, kontaktart)
);

create table profil_fachgebiet
(
    profil integer not null references profil (id),
    fachgebiet varchar(50) not null references fachgebiet(name),
    primary key (profil, fachgebiet)
);

create table profil_link
(
    profil integer not null references profil (id),
    url varchar(300) not null,
    text varchar(500) not null,
    primary key (profil, url, text)
);

create table profil_thema_value
(
    id varchar(150) primary key,
    profil integer not null references profil (id),
    name varchar(50)
);

create table profil_datei_value
(
    id varchar(150) primary key,
    profil integer not null references profil (id),
    name varchar(50),
    beschreibung varchar(200)
);