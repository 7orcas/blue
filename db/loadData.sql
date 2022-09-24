DELETE FROM cntrl.zzz_role;
DELETE FROM cntrl.role;
DELETE FROM cntrl.zzz;
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, org, created) VALUES (1, 'Org 1', 1, NOW());
INSERT INTO cntrl.org (id, code, org, created, active) VALUES (2, 'Org 2', 2, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (3, 'Org 3', 3, NOW(), true);

INSERT INTO cntrl.role (id, code, org, created) VALUES (1, 'LangEdit', 0, NOW());
INSERT INTO cntrl.role (id, code, org, created) VALUES (2, 'Fix', 0, NOW());

INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (1, 'js@7orcas.com', '123', '1,2,3', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (2, '111',           '111', '1,2,3', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (3, '222',           '222', '1', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (4, '333',           '333', '1', 0, NOW(), false);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (5, '444',           '444', '1', 4, NOW(), true);

INSERT INTO cntrl.zzz_role (id, id_zzz, id_role, created) VALUES (1, 1, 1, NOW());
INSERT INTO cntrl.zzz_role (id, id_zzz, id_role, created) VALUES (2, 1, 2, NOW());
INSERT INTO cntrl.zzz_role (id, id_zzz, id_role, created) VALUES (3, 2, 2, NOW());
INSERT INTO cntrl.zzz_role (id, id_zzz, id_role, created) VALUES (4, 3, 1, NOW());
