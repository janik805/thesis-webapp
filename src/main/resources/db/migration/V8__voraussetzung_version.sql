alter table voraussetzung
    add version integer not null default 0;

alter table voraussetzung
    alter column version drop default;