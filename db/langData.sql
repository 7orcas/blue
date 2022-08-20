
delete from cntrl.lang_label;
delete from cntrl.lang_key; 
delete from cntrl.lang;

insert into cntrl.lang (id, code, descr, org, created) values (1, 'en', 'English', 0, NOW());
insert into cntrl.lang (id, code, descr, org, created) values (2, 'de', 'Deutsch', 0, NOW());
insert into cntrl.lang (id, code, descr, org, created) values (3, 'es', 'Espanol', 0, NOW());


drop table if exists sys._lang;
create table sys._lang
(
  code       varchar,
  pack       varchar,
  org        int,
  en         varchar,
  de         varchar
  );

\copy sys._lang from '/media/jarvisting/Jarvis/projects/blue/db/labels.csv' with DELIMITER ','  CSV  ENCODING 'UTF-8';  

alter table sys._lang add column lang_key_id bigint;
alter table sys._lang add column en_id bigint;
alter table sys._lang add column de_id bigint;
update sys._lang set lang_key_id = NEXTVAL('cntrl.lang_key_id_seq');
update sys._lang set en_id = NEXTVAL('cntrl.lang_label_id_seq');
update sys._lang set de_id = NEXTVAL('cntrl.lang_label_id_seq'); --need to repeat new id's for each language

insert into cntrl.lang_key (id, code, pack, org, created) select lang_key_id, code, pack, org, NOW() from sys._lang;
insert into cntrl.lang_label (id_lang_key, id, lang, code, org, created) select lang_key_id, en_id, 'en', en, org, NOW() from sys._lang where length(en) > 0;
insert into cntrl.lang_label (id_lang_key, id, lang, code, org, created) select lang_key_id, de_id, 'de', de, org, NOW() from sys._lang where length(de) > 0;