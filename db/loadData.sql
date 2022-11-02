DELETE FROM cntrl.zzz_role;
DELETE FROM cntrl.zzz;
DELETE FROM cntrl.role_permission;
DELETE FROM cntrl.role;
DELETE FROM cntrl.permission;
DELETE FROM cntrl.org;

INSERT INTO cntrl.org (id, code, descr, org_nr, updated, updated_userid) VALUES (1, 'Org 1', 'Org 1 Description', 1, current_timestamp, 1);
INSERT INTO cntrl.org (id, code, org_nr, updated, updated_userid, active) VALUES (2, 'Org 2', 2, current_timestamp, 1, false);
INSERT INTO cntrl.org (id, code, org_nr, updated, updated_userid, dvalue) VALUES (3, 'Org 3', 3, current_timestamp, 1, true);

INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (1, 'org', 'Full edit Organisations', '*', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (2, 'org', 'Read Only Organisations', '-R--', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (3, 'lang', 'Full edit Labels', '*', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (4, 'plan/fix', 'Fixing Simulations', '*', 0, current_timestamp);

INSERT INTO cntrl.role (id, code, org_nr, updated, updated_userid) VALUES (1, 'Org*', 0, current_timestamp, 1);
INSERT INTO cntrl.role (id, code, org_nr, updated, updated_userid) VALUES (2, 'Org RO', 0, current_timestamp, 1);
INSERT INTO cntrl.role (id, code, org_nr, updated, updated_userid) VALUES (3, 'LangEdit', 0, current_timestamp, 1);
INSERT INTO cntrl.role (id, code, org_nr, updated, updated_userid) VALUES (4, 'Fix', 0, current_timestamp, 1);

INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (1, 1, 1, 0, current_timestamp, 1);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (2, 1, 2, 0, current_timestamp, 1);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (3, 2, 2, 0, current_timestamp, 1);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (4, 3, 3, 0, current_timestamp, 1);
INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (5, 4, 4, 0, current_timestamp, 1);

INSERT INTO cntrl.zzz (id, xxx, yyy, org_nr, orgs, attempts, updated, active) VALUES (1, 'js@7orcas.com', '123', 0, '1,2,3', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, org_nr, orgs, attempts, updated, active) VALUES (2, '111',           '111', 0, '1,2,3', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, org_nr, orgs, attempts, updated, active) VALUES (3, '222',           '222', 0, '1', 0, current_timestamp, true);
INSERT INTO cntrl.zzz (id, xxx, yyy, org_nr, orgs, attempts, updated, active) VALUES (4, '333',           '333', 0, '1', 0, current_timestamp, false);
INSERT INTO cntrl.zzz (id, xxx, yyy, org_nr, orgs, attempts, updated, active) VALUES (5, '444',           '444', 0, '1', 4, current_timestamp, true);

INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (1, 1, 1, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (2, 1, 3, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (3, 1, 4, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (4, 2, 2, current_timestamp);
INSERT INTO cntrl.zzz_role (id, zzz_id, role_id, updated) VALUES (5, 2, 4, current_timestamp);
