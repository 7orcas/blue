create table reference
(
	id bigserial primary key
) INHERITS (sys.base);
alter table reference OWNER to postgres;
alter sequence reference_id_seq restart with 10000;
