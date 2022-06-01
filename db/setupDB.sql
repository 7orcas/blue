
DROP TABLE IF EXISTS org;
DROP TABLE IF EXISTS base;

CREATE TABLE base
(
    _id integer,
    _code varchar,
    _created timestamp without time zone,
    _encoded varchar,
    _encoded_flag integer
);
ALTER TABLE base OWNER to postgres;
CREATE INDEX "base_primary" ON base USING btree (_id) TABLESPACE pg_default;    

CREATE TABLE org
(
    nr integer,
    CONSTRAINT org_primary PRIMARY KEY (_id)
) INHERITS (public.base);
ALTER TABLE public.org OWNER to postgres;
 


CREATE TABLE IF NOT EXISTS public.testtable
(
    label character varying COLLATE pg_catalog."default",
    id integer,
    created date
);
ALTER TABLE public.testtable OWNER to postgres;

