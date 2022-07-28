
DROP TABLE IF EXISTS cntrl.lang_label;
DROP TABLE IF EXISTS cntrl.lang_key;
DROP TABLE IF EXISTS cntrl.lang;
DROP TABLE IF EXISTS cntrl.zzz;
DROP TABLE IF EXISTS cntrl.org;
DROP TABLE IF EXISTS base;
DROP SCHEMA cntrl;


CREATE SCHEMA cntrl;


CREATE TABLE base
(
    id bigint,
    org integer,
    code varchar,
    created timestamp without time zone,
    encoded varchar,
    encoded_flag integer,
    active boolean default true
);
ALTER TABLE base OWNER to postgres;
CREATE INDEX "base_primary" ON base USING btree (id) TABLESPACE pg_default;    

CREATE TABLE cntrl.org
(
	dvalue boolean default false, 	
    CONSTRAINT org_primary PRIMARY KEY (id)
) INHERITS (public.base);
ALTER TABLE cntrl.org OWNER to postgres;
 
CREATE TABLE cntrl.zzz
(
 	xxx varchar,
 	yyy varchar,
 	orgs varchar,
 	attempts integer default 0,
	CONSTRAINT zzz_primary PRIMARY KEY (id)
) INHERITS (public.base);
ALTER TABLE cntrl.zzz OWNER to postgres;

CREATE TABLE cntrl.lang
(
 	descr varchar,
	CONSTRAINT lang_primary PRIMARY KEY (id)
) INHERITS (public.base);
ALTER TABLE cntrl.lang OWNER to postgres;

CREATE TABLE cntrl.lang_key
(
 	pack varchar,
	CONSTRAINT lang_key_primary PRIMARY KEY (id)
) INHERITS (public.base);
ALTER TABLE cntrl.lang_key OWNER to postgres;

CREATE TABLE cntrl.lang_label
(
 	id_lang_key bigint references cntrl.lang_key (id), 
    lang varchar,
 	CONSTRAINT lang_label_primary PRIMARY KEY (id)
) INHERITS (public.base);
ALTER TABLE cntrl.lang_label OWNER to postgres;


