

INSERT INTO cntrl.lang_key (_id, _code, _org, _created) VALUES (1, 'userid', 0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, _org, _created) VALUES (2, 'pw',     0, NOW());
INSERT INTO cntrl.lang_key (_id, _code, _org, _created) VALUES (3, 'org',    0, NOW());

INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (1, 1, 'en', 'User ID',      0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (2, 2, 'en', 'Password',     0, NOW());
INSERT INTO cntrl.lang_label (_id_lang_key, _id, lang, _code, _org, _created) VALUES (3, 3, 'en', 'Organisation', 0, NOW());