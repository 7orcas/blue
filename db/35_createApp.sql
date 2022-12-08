
create table country
(
	id bigserial primary key,
	image varchar
) INHERITS (sys.baseref);
alter table country OWNER to postgres;
alter sequence country_id_seq restart with 10000;

create table currency
(
	id bigserial primary key	
) INHERITS (sys.baseref);
alter table currency OWNER to postgres;
alter sequence currency_id_seq restart with 10000;
