
DROP TABLE IF EXISTS cntrl.lang_label;
DROP TABLE IF EXISTS cntrl.lang_key;
DROP TABLE IF EXISTS cntrl.zzz;
DROP TABLE IF EXISTS cntrl.org;
DROP TABLE IF EXISTS base;
DROP SCHEMA cntrl;


CREATE SCHEMA cntrl;


CREATE TABLE base
(
    _id bigint,
    _org integer,
    _code varchar,
    _created timestamp without time zone,
    _encoded varchar,
    _encoded_flag integer,
    _active boolean default true
);
ALTER TABLE base OWNER to postgres;
CREATE INDEX "base_primary" ON base USING btree (_id) TABLESPACE pg_default;    

CREATE TABLE cntrl.org
(
	dvalue boolean default false, 	
    CONSTRAINT org_primary PRIMARY KEY (_id)
) INHERITS (public.base);
ALTER TABLE cntrl.org OWNER to postgres;
 
CREATE TABLE cntrl.zzz
(
 	usename varchar,
 	pw varchar,
 	orgs varchar,
	CONSTRAINT zzz_primary PRIMARY KEY (_id)
) INHERITS (public.base);
ALTER TABLE cntrl.zzz OWNER to postgres;
 
CREATE TABLE cntrl.lang_key
(
 	pack varchar,
	CONSTRAINT lang_key_primary PRIMARY KEY (_id)
) INHERITS (public.base);
ALTER TABLE cntrl.lang_key OWNER to postgres;

CREATE TABLE cntrl.lang_label
(
 	_id_lang_key bigint references cntrl.lang_key (_id), 
    lang varchar,
 	CONSTRAINT lang_label_primary PRIMARY KEY (_id)
) INHERITS (public.base);
ALTER TABLE cntrl.lang_label OWNER to postgres;


