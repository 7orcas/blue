create table reftype
(
	id bigserial primary key
) INHERITS (sys.base);
alter table reftype OWNER to postgres;
alter sequence reftype_id_seq restart with 10000;

create table reference
(
	id bigserial primary key,
	sort integer default 0,
	dvalue boolean default false,
	reftype_id bigint references reftype (id)
) INHERITS (sys.base);
alter table reference OWNER to postgres;
alter sequence reference_id_seq restart with 10000;
