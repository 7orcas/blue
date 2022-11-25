
delete from cntrl.lang_label;
delete from cntrl.lang_key; 
delete from cntrl.lang;

insert into cntrl.lang (id, code, descr, org_nr, updated) values (1, 'en', 'English', 0, NOW());
insert into cntrl.lang (id, code, descr, org_nr, updated) values (2, 'de', 'Deutsch', 0, NOW());
insert into cntrl.lang (id, code, descr, org_nr, updated) values (3, 'es', 'Espanol', 0, NOW());


drop table if exists sys._lang;
create table sys._lang
(
  code       varchar,
  pack       varchar,
  org_nr     int,
  en         varchar,
  de         varchar
);

\copy sys._lang from '/media/jarvisting/Jarvis/projects/blue/db/labels.csv' with DELIMITER ','  CSV  ENCODING 'UTF-8';  

drop table if exists sys._langKey;
create table sys._langKey
(
  id         bigint,
  code       varchar,
  pack       varchar
);
insert into sys._langKey (code, pack) select distinct code, pack from sys._lang;
update sys._langKey set id = NEXTVAL('cntrl.lang_key_id_seq');

alter table sys._lang add column lang_key_id bigint;
alter table sys._lang add column en_id bigint;
alter table sys._lang add column de_id bigint;
update sys._lang as l set lang_key_id = (select id from sys._langKey as k where l.code = k.code);
update sys._lang set en_id = NEXTVAL('cntrl.lang_label_id_seq');
update sys._lang set de_id = NEXTVAL('cntrl.lang_label_id_seq'); --need to repeat new id's for each language

insert into cntrl.lang_key (id, code, pack, org_nr, updated) select id, code, pack, 0, NOW() from sys._langkey;
insert into cntrl.lang_label (lang_key_id, id, lang, code, org_nr, updated) select lang_key_id, en_id, 'en', en, org_nr, NOW() from sys._lang where length(en) > 0;
insert into cntrl.lang_label (lang_key_id, id, lang, code, org_nr, updated) select lang_key_id, de_id, 'de', de, org_nr, NOW() from sys._lang where length(de) > 0;