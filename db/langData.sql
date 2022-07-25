
DELETE FROM cntrl.lang_label;
DELETE FROM cntrl.lang_key; 

INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (1, 'userid', 'login', 0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (2, 'pw',     'login', 0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (3, 'org',    'login', 0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (4, 'login',  'login', 0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (5, 'yes',    '',      0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (6, 'no',     '',      0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, pack, _org, _created) VALUES (7, 'lang',   'login', 0, NOW());

INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (1, 1, 'en', 'User ID',      0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (2, 2, 'en', 'Password',     0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (3, 3, 'en', 'Organisation', 0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (4, 4, 'en', 'GO Login',     0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (5, 5, 'en', 'Yes',          0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (6, 6, 'en', 'No',           0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (7, 7, 'en', 'Language',     0, NOW());

INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (1, 101, 'de', 'Beutser ID',   0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (2, 102, 'de', 'Passwort',     0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (3, 103, 'de', 'Organisation', 0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (4, 104, 'de', 'Anmeldung',    0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (5, 105, 'de', 'Ja',           0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (6, 106, 'de', 'Nein',         0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (7, 107, 'de', 'Sprache',      0, NOW());