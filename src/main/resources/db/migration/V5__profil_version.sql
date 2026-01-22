alter table profil
add version integer not null default 0;

alter table profil
alter column version drop default;