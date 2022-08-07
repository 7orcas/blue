
DELETE FROM cntrl.zzz;
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (1, 'Org 1', 1, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, active) VALUES (2, 'Org 2', 2, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (3, 'Org 3', 3, NOW(), true);

INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (1, 'js@7orcas.com', '123', '1,2,3', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (2, '111',           '111', '1,2,3', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (3, '222',           '222', '1', 0, NOW(), true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (4, '333',           '333', '1', 0, NOW(), false);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, created, active) VALUES (5, '444',           '444', '1', 4, NOW(), true);
