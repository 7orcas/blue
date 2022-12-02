INSERT INTO cntrl.permission (id,code,descr,crud,org_nr,updated) VALUES (10000,'org',      'Read Only Organisations','-R--',0,current_timestamp);
INSERT INTO cntrl.permission (id,code,descr,crud,org_nr,updated) VALUES (10001,'plan/fix', 'Fixing Simulations',     '*',   0,current_timestamp);

INSERT INTO cntrl.role (id,code,org_nr,updated,updated_userid) VALUES (10000,'Fix',0,current_timestamp,1);

INSERT INTO cntrl.role_permission (id,role_id,permission_id,org_nr,updated,updated_userid) VALUES (10000,10000,10001,1,current_timestamp,1);


INSERT INTO reftype (id,code,descr,org_nr,updated,updated_userid) VALUES (10001,'type1','type 1 desc',1,current_timestamp,1);
INSERT INTO reftype (id,code,descr,org_nr,updated,updated_userid) VALUES (10002,'type2','type 2 desc',1,current_timestamp,1);

INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10010,10001,1,'code11','desc1',1,current_timestamp,1);
INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10011,10001,2,'code12','desc2',1,current_timestamp,1);
INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10012,10001,3,'code13','desc3',1,current_timestamp,1);

INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10020,10002,3,'code21','desc1',1,current_timestamp,1);
INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10021,10002,2,'code22','desc2',1,current_timestamp,1);
INSERT INTO reference (id,reftype_id,sort,code,descr,org_nr,updated,updated_userid) VALUES (10022,10002,1,'code23','desc3',1,current_timestamp,1);
