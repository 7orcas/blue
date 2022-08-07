
DELETE FROM cntrl.lang_label;
DELETE FROM cntrl.lang_key; 
DELETE FROM cntrl.lang;

INSERT INTO cntrl.lang (id, code, descr, org, created) VALUES (1, 'en', 'English', 0, NOW());
INSERT INTO cntrl.lang (id, code, descr, org, created) VALUES (2, 'de', 'Deutsch', 0, NOW());
INSERT INTO cntrl.lang (id, code, descr, org, created) VALUES (3, 'es', 'Espanol', 0, NOW());

INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (1, 'userid',    'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (2, 'pw',        'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (3, 'org',       'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (4, 'login',     'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (5, 'yes',       '',      0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (6, 'no',        '',      0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (7, 'lang',      'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (8, 'loginT',    'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (9, 'maxatt',    'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (10, 'invorg',   'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (11, 'invpw',    'login', 0, NOW());
INSERT INTO cntrl.lang_key (id, code, pack, org, created) VALUES (12, 'invuid',   'login', 0, NOW());


INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (1, 1, 'en', 'User ID',      0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (2, 2, 'en', 'Password',     0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (3, 3, 'en', 'Organisation', 0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (4, 4, 'en', 'GO Login',     0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (5, 5, 'en', 'Yes',          0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (6, 6, 'en', 'No',           0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (7, 7, 'en', 'Language',     0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (8, 8, 'en', 'Blue Login',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (9, 9, 'en', 'Max. Login Attempts Exceeded',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (10, 10, 'en', 'No Permission for Organisation',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (11, 11, 'en', 'Invalid Password (%1 of %2 attempts)',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (12, 12, 'en', 'Invalid UserID / Password',   0, NOW());

INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (1, 101, 'de', 'Benutser ID',  0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (2, 102, 'de', 'Passwort',     0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (3, 103, 'de', 'Organisation', 0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (4, 104, 'de', 'Anmeldung',    0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (5, 105, 'de', 'Ja',           0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (6, 106, 'de', 'Nein',         0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (7, 107, 'de', 'Sprache',      0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (9, 109, 'de', '(D) Max. Login Attempts Exceeded',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (10, 110, 'de', '(D) No Permission for Organisation',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (11, 111, 'de', '(D) Invalid Password (%1 of %2 attempts)',   0, NOW());
INSERT INTO cntrl.lang_label (id_lang_key, id, lang, code, org, created) VALUES (12, 112, 'de', '(D) Invalid UserID / Password',   0, NOW());