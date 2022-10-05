DELETE FROM cntrl.zzz_role;
DELETE FROM cntrl.role;
DELETE FROM cntrl.zzz;
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, descr, org_nr, updated) VALUES (1, 'Org 1', 'Org 1 Description', 1, current_timestamp);
INSERT INTO cntrl.org (id, code, org_nr, updated, active) VALUES (2, 'Org 2', 2, current_timestamp, false);
INSERT INTO cntrl.org (id, code, org_nr, updated, dvalue) VALUES (3, 'Org 3', 3, current_timestamp, true);

INSERT INTO cntrl.permission (id, code, crud, org_nr, updated) VALUES (1, 'org', '*', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, crud, org_nr, updated) VALUES (2, 'org', 'r', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, crud, org_nr, updated) VALUES (3, 'lang', '*', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, crud, org_nr, updated) VALUES (4, 'plan/fix', '*', 0, current_timestamp);

INSERT INTO cntrl.role (id, code, org_nr, updated) VALUES (1, 'Org*', 0, current_timestamp);
INSERT INTO cntrl.role (id, code, org_nr, updated) VALUES (2, 'Org RO', 0, current_timestamp);
INSERT INTO cntrl.role (id, code, org_nr, updated) VALUES (3, 'LangEdit', 0, current_timestamp);
INSERT INTO cntrl.role (id, code, org_nr, updated) VALUES (4, 'Fix', 0, current_timestamp);

INSERT INTO cntrl.role_permission (id, role_id, permission_id, updated) VALUES (1, 1, 1, current_timestamp);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, updated) VALUES (2, 2, 2, current_timestamp);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, updated) VALUES (3, 3, 3, current_timestamp);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, updated) VALUES (4, 4, 4, current_timestamp);

INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, updated, active) VALUES (1, 'js@7orcas.com', '123', '1,2,3', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, updated, active) VALUES (2, '111',           '111', '1,2,3', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, updated, active) VALUES (3, '222',           '222', '1', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, updated, active) VALUES (4, '333',           '333', '1', 0, current_timestamp, false);
INSERT INTO cntrl.zzz (id, xxx, yyy, orgs, attempts, updated, active) VALUES (5, '444',           '444', '1', 4, current_timestamp, true);

INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (1, 1, 1, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (2, 1, 3, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (3, 1, 4, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (4, 2, 2, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (5, 2, 4, current_timestamp);
