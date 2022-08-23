drop table if exists cntrl.lang_label;
drop table if exists cntrl.lang_key;
drop table if exists cntrl.lang;
drop table if exists cntrl.zzz_role;
drop table if exists cntrl.role;
drop table if exists cntrl.zzz;
drop table if exists cntrl.org;
drop table if exists sys.base;
drop table if exists sys._lang;
drop table if exists sys._langkey;
drop schema if exists cntrl;
drop schema if exists sys;

create schema cntrl;
create schema sys;

create table sys.base
(
	org integer,
    code varchar,
    created timestamp without time zone,
    encoded varchar,
    encoded_flag integer,
    active boolean default true
);
alter table sys.base OWNER to postgres;


create table cntrl.org
(
	id bigserial primary key,
	dvalue boolean default false 	
) INHERITS (sys.base);
alter table cntrl.org OWNER to postgres;
 
create table cntrl.zzz
(
	id bigserial primary key,
	xxx varchar,
 	yyy varchar,
 	orgs varchar,
 	attempts integer default 0,
 	constraint zzz_xxx unique (xxx)
) INHERITS (sys.base);
alter table cntrl.zzz OWNER to postgres;

create table cntrl.role
(
	id bigserial primary key
) INHERITS (sys.base);
alter table cntrl.role OWNER to postgres;

create table cntrl.zzz_role
(
	id bigserial primary key,
    id_zzz bigint references cntrl.zzz (id),
    id_role bigint references cntrl.role (id),
    constraint zzz_role_i1 unique (id_zzz,id_role)
) INHERITS (sys.base);
alter table cntrl.zzz_role OWNER to postgres;

create table cntrl.lang
(
	id bigserial primary key,
	descr varchar
) INHERITS (sys.base);
alter table cntrl.lang OWNER to postgres;

create table cntrl.lang_key
(
	id bigserial primary key,
	pack varchar
) INHERITS (sys.base);
alter table cntrl.lang_key OWNER to postgres;

create table cntrl.lang_label
(
	id bigserial primary key,
	id_lang_key bigint references cntrl.lang_key (id), 
    lang varchar
) INHERITS (sys.base);
alter table cntrl.lang_label OWNER to postgres;


