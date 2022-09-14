DELETE FROM cntrl.zzz_role;
DELETE FROM cntrl.role;
DELETE FROM cntrl.zzz;
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, org, created) VALUES (1, 'Org 1', 1, NOW());
INSERT INTO cntrl.org (id, code, org, created, active) VALUES (2, 'Org 2', 2, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (3, 'Org 3', 3, NOW(), true);
INSERT INTO cntrl.org (id, code, org, created) VALUES (4, 'Org 4', 4, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (5, 'Org 5', 5, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (6, 'Org 6', 6, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (7, 'Org 7', 7, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (8, 'Org 8', 8, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (9, 'Org 9', 9, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (10, 'Org 10', 10, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (11, 'Org 11', 11, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (12, 'Org 12', 12, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (13, 'Org 13', 13, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (14, 'Org 14', 14, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (15, 'Org 15', 15, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (16, 'Org 16', 16, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (17, 'Org 17', 17, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (18, 'Org 18', 18, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (19, 'Org 19', 19, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (20, 'Org 20', 20, NOW());
INSERT INTO cntrl.org (id, code, org, created) VALUES (21, 'Org 21', 21, NOW());





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
