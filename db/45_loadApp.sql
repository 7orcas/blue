INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (10000,'org',       'Read Only Organisations', '-R--', 0, current_timestamp);
INSERT INTO cntrl.permission (id, code, descr, crud, org_nr, updated) VALUES (10001,'plan/fix',  'Fixing Simulations',      '*',    0, current_timestamp);

INSERT INTO cntrl.role (id, code, org_nr, updated, updated_userid) VALUES (10000, 'Fix', 0, current_timestamp, 1);

INSERT INTO cntrl.role_permission (id, role_id, permission_id, org_nr, updated, updated_userid) VALUES (10000, 10000, 10001, 1, current_timestamp, 1);

INSERT INTO reference (id, code, descr, org_nr, updated, updated_userid) VALUES (10000, 'code', 'desc', 1, current_timestamp, 1);
