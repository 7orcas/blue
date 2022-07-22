
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (_id, _code, _org, _created, dvalue) VALUES (1, 'Org 1', 1, NOW(), false);
INSERT INTO cntrl.org (_id, _code, _org, _created, _active) VALUES (2, 'Org 2', 2, NOW(), false);
INSERT INTO cntrl.org (_id, _code, _org, _created, dvalue) VALUES (3, 'Org 3', 3, NOW(), true);

