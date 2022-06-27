INSERT INTO role (name) VALUES ('ROLE_CUSTOMER');
INSERT INTO role (name) VALUES ('ROLE_PROVIDER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

INSERT INTO banks (name) VALUES ('Vietcombank');
INSERT INTO banks (name) VALUES ('DongA Bank');

INSERT INTO status(name) VALUES ('WAITING');
INSERT INTO status(name) VALUES ('ACCEPT');
INSERT INTO status(name) VALUES ('REFUSE');

INSERT INTO province(name) VALUES  ('Đà Nẵng');
INSERT INTO province(name) VALUES  ('Hà Nội');
INSERT INTO province(name) VALUES  ('Hồ Chí Minh');
INSERT INTO province(name) VALUES  ('An Giang');
INSERT INTO province(name) VALUES  ('Bà Rịa – Vũng Tàu');
INSERT INTO province(name) VALUES  ('Bắc Giang');
INSERT INTO province(name) VALUES  ('Bắc Kạn');
INSERT INTO province(name) VALUES  ('Bạc Liêu');
INSERT INTO province(name) VALUES  ('Bắc Ninh');
INSERT INTO province(name) VALUES  ('Bến Tre');
INSERT INTO province(name) VALUES  ('Bình Định');
INSERT INTO province(name) VALUES  ('Bình Dương');


INSERT INTO categories(name) VALUES ('Mạo Hiểm');
INSERT INTO categories(name) VALUES ('Ngắm Cảnh');
INSERT INTO categories(name) VALUES ('Khám Pha');

INSERT INTO account (email, password, username ,role_id, create_at) VALUES ('hoangusa08@gmail.com', '123', 'hoang', 3 , '2022-05-05');
INSERT INTO account (email, password, username ,role_id, create_at) VALUES ('hoangusa081@gmail.com', '123', 'hoang1', 2 , '2022-05-05');
INSERT INTO account (email, password, username ,role_id, create_at) VALUES ('hoangusa082@gmail.com', '123', 'hoang2', 1 , '2022-05-05');

INSERT INTO admin (account_id) VALUES (1);
INSERT INTO provider (account_id, address, bank_id, bank_number, name_company, owner, phone_number, status_id) VALUES (2 ,'abc', 1, '123456789' ,'abc' , 'abc', '12222', 1 );
INSERT INTO customer (address, full_name, phone_number, account_id, is_delete) VALUES ('abc', 'abc' ,'abc', 3, false);