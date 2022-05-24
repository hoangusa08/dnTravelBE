INSERT INTO role (name) VALUES ('ROLE_CUSTOMER');
INSERT INTO role (name) VALUES ('ROLE_EMPLOYEE');
INSERT INTO role (name) VALUES ('ROLE_PROVIDER');

INSERT INTO Status (name) VALUES ('ACCEPT');
INSERT INTO Status (name) VALUES ('REFUSE');
INSERT INTO Status (name) VALUES ('WAIT');

INSERT INTO account (email, password, username ,role_id) VALUES ('hoangusa08@gmail.com', '123', 'hoang', 1);