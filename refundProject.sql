CREATE TABLE reimbursement
(	
    reimb_id NUMBER, 
	reimb_ammount NUMBER (7,2) NOT NULL, 
	reimb_submit TIMESTAMP (6)NOT NULL, 
	reimb_resolve TIMESTAMP (6), 
	reimb_description VARCHAR2 (250) NOT NULL, 
	reimb_receipt BLOB, 
	reimb_author NUMBER NOT NULL, 
	reimb_resolver NUMBER, 
	reimb_status_id NUMBER NOT NULL, 
	reimb_type_id NUMBER NOT NULL
   );

CREATE TABLE reimb_status 
  (	
    reimb_status_id NUMBER, 
	reimb_status VARCHAR2(10) NOT NULL
   );
   
CREATE TABLE reimb_type 
  (	
    reimb_type_id NUMBER, 
	reimb_type VARCHAR2(10) NOT NULL
   );

CREATE TABLE reimb_user 
  (
    user_id NUMBER, 
	user_username VARCHAR2(50) NOT NULL, 
	user_password VARCHAR2(50) NOT NULL, 
	user_first_name VARCHAR2(100) NOT NULL, 
	user_last_name VARCHAR2(100) NOT NULL, 
	user_email VARCHAR2(150) NOT NULL, 
	user_role_id NUMBER NOT NULL
   );
   
CREATE TABLE reimb_user_roles 
  (
    user_role_id NUMBER, 
	user_role VARCHAR2(15) NOT NULL
   );

ALTER TABLE reimb_user_roles ADD PRIMARY KEY (user_role_id);
ALTER TABLE reimb_user ADD PRIMARY KEY (user_id);
ALTER TABLE reimbursement ADD PRIMARY KEY (reimb_id);
ALTER TABLE reimb_type ADD PRIMARY KEY (reimb_type_id);
ALTER TABLE reimb_status ADD PRIMARY KEY (reimb_status_id);

ALTER TABLE reimbursement ADD FOREIGN KEY (reimb_author) REFERENCES reimb_user (user_id);
ALTER TABLE reimbursement ADD FOREIGN KEY (reimb_resolver) REFERENCES reimb_user (user_id);
ALTER TABLE reimbursement ADD FOREIGN KEY (reimb_status_id) REFERENCES reimb_status (reimb_status_id);
ALTER TABLE reimbursement ADD FOREIGN KEY (reimb_type_id) REFERENCES reimb_type (reimb_type_id);
ALTER TABLE reimb_user ADD FOREIGN KEY (user_role_id) REFERENCES reimb_user_roles (user_role_id);

INSERT INTO reimb_status VALUES (1, 'PENDING');
INSERT INTO reimb_status VALUES (2, 'APPROVED');
INSERT INTO reimb_status VALUES (3, 'DENIED');

INSERT INTO reimb_type VALUES (1, 'LODGING');
INSERT INTO reimb_type VALUES (2, 'TRAVEL');
INSERT INTO reimb_type VALUES (3, 'FOOD');
INSERT INTO reimb_type VALUES (4, 'OTHER');

INSERT INTO reimb_user_roles VALUES (1, 'EMPLOYEE');
INSERT INTO reimb_user_roles VALUES (2, 'FINANCE MANAGER');

CREATE SEQUENCE reimb_seq
    START WITH 1
    INCREMENT BY 1;
/

CREATE TRIGGER reimb_seq_trigger
BEFORE INSERT ON reimbursement
FOR EACH ROW
BEGIN
    IF :new.reimb_id = 0 THEN
    SELECT reimb_seq.nextval INTO :new.reimb_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1;
/

CREATE TRIGGER user_seq_trigger
BEFORE INSERT ON reimb_user
FOR EACH ROW
BEGIN
    IF :new.user_id = 0 THEN
    SELECT user_seq.nextval INTO :new.user_id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE approve_all(user_id IN NUMBER)
IS
BEGIN
    UPDATE reimbursement SET reimb_resolve = CURRENT_TIMESTAMP WHERE reimb_status_id = 1;
    UPDATE reimbursement SET reimb_resolver = user_id WHERE reimb_status_id = 1;
    UPDATE reimbursement SET reimb_status_id = 2 WHERE reimb_status_id = 1;
END;
/

CREATE OR REPLACE PROCEDURE deny_all(user_id IN NUMBER)
IS
BEGIN
    UPDATE reimbursement SET reimb_resolve = CURRENT_TIMESTAMP WHERE reimb_status_id = 1;
    UPDATE reimbursement SET reimb_resolver = user_id WHERE reimb_status_id = 1;
    UPDATE reimbursement SET reimb_status_id = 3 WHERE reimb_status_id = 1;
END;
/

COMMIT;