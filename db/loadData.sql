
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (1, 'Org 1', 1, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, active) VALUES (2, 'Org 2', 2, NOW(), false);
INSERT INTO cntrl.org (id, code, org, created, dvalue) VALUES (3, 'Org 3', 3, NOW(), true);

