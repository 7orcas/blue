create schema cntrl;
create schema sys;

create sequence sys.temp_id as bigint start 10000; 

create table sys.base
(
	org_nr integer,
    code varchar,
    descr varchar,
    updated timestamp without time zone,
    updated_userid bigint,
    encoded varchar,
    encoded_flag integer,
    active boolean default true
);
alter table sys.base OWNER to postgres;

create table cntrl.org
(
	id bigserial primary key,
	dvalue boolean default false,
	constraint org_nr_i1 unique (org_nr)
) INHERITS (sys.base);
alter table cntrl.org OWNER to postgres;
alter sequence cntrl.org_id_seq restart with 11000;
 
create table cntrl.zzz
(
	id bigserial primary key,
	xxx varchar,
 	yyy varchar,
 	orgs varchar,
 	attempts integer default 0,
    lastlogin timestamp without time zone, 	
 	constraint zzz_xxx unique (xxx)
) INHERITS (sys.base);
alter table cntrl.zzz OWNER to postgres;
alter sequence cntrl.zzz_id_seq restart with 11000;

create table cntrl.permission
(
	id bigserial primary key,
	crud varchar	
) INHERITS (sys.base);
alter table cntrl.permission OWNER to postgres;
alter sequence cntrl.permission_id_seq restart with 11000;

create table cntrl.role
(
	id bigserial primary key
) INHERITS (sys.base);
alter table cntrl.role OWNER to postgres;
alter sequence cntrl.role_id_seq restart with 11000;

create table cntrl.role_permission
(
	id bigserial primary key,
    role_id bigint references cntrl.role (id),
    permission_id bigint references cntrl.permission (id),
    constraint role_permission_i1 unique (role_id,permission_id)
) INHERITS (sys.base);
alter table cntrl.role_permission OWNER to postgres;
alter sequence cntrl.role_permission_id_seq restart with 11000;

create table cntrl.zzz_role
(
	id bigserial primary key,
    zzz_id bigint references cntrl.zzz (id),
    role_id bigint references cntrl.role (id),
    constraint zzz_role_i1 unique (zzz_id,role_id)
) INHERITS (sys.base);
alter table cntrl.zzz_role OWNER to postgres;
alter sequence cntrl.zzz_role_id_seq restart with 11000;

create table cntrl.lang
(
	id bigserial primary key
) INHERITS (sys.base);
alter table cntrl.lang OWNER to postgres;
alter sequence cntrl.lang_id_seq restart with 11000;

create table cntrl.lang_key
(
	id bigserial primary key,
	pack varchar
) INHERITS (sys.base);
alter table cntrl.lang_key OWNER to postgres;
alter sequence cntrl.lang_key_id_seq restart with 11000;

create table cntrl.lang_label
(
	id bigserial primary key,
	lang_key_id bigint references cntrl.lang_key (id), 
    lang varchar
) INHERITS (sys.base);
alter table cntrl.lang_label OWNER to postgres;
alter sequence cntrl.lang_label_id_seq restart with 11000;

create table reftype
(
	id bigserial primary key
) INHERITS (sys.base);
alter table reftype OWNER to postgres;
alter sequence reftype_id_seq restart with 10000;

create table sys.baseref
(
	sort integer default 0,
	dvalue boolean default false,
	reftype_id bigint references reftype (id)
) INHERITS (sys.base);
alter table sys.baseref OWNER to postgres;



