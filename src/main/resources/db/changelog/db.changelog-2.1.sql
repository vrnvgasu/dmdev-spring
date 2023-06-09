--liquibase formatted sql

--changeset dmitrii:1
create table revision
(
    id        int4 generated by default as identity,
    timestamp int8,
    primary key (id)
);
create table users_aud
(
    id         int8 not null,
    rev        int4 not null,
    revtype    int2,
    birth_date date,
    firstname  varchar(255),
    lastname   varchar(255),
    role       varchar(255),
    username   varchar(255),
    company_id int4,
    primary key (id, rev)
);
alter table if exists users_aud
    add constraint FKmrjb3nxent1mi8jjld588s7u6
        foreign key (rev)
            references revision;