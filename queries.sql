DROP DATABASE IF EXISTS bank_accs;
CREATE DATABASE bank_accs;
USE bank_accs;

CREATE USER 'TSOUCHLAKIS'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON * . * TO 'TSOUCHLAKIS'@'localhost';

CREATE TABLE accs(aid INT PRIMARY KEY NOT NULL AUTO_INCREMENT, # 'aid' = Account ID.
 name VARCHAR(30) NOT NULL,
 surname VARCHAR(30) NOT NULL,
 contactPhone VARCHAR(30) NOT NULL,
 address VARCHAR(30) NOT NULL,
 accBalance FLOAT UNSIGNED NOT NULL DEFAULT 0, 
 activated BOOL NOT NULL DEFAULT 1);
 
 ALTER TABLE accs AUTO_INCREMENT = 10;
 
INSERT INTO accs(name, surname, contactPhone, address, accBalance)
 VALUES ("CHRIS", "TSOUCHLAKIS", "6986868969", "Street 42", 100);
INSERT INTO accs(name, surname, contactPhone, address, accBalance)
 VALUES ("MARKOS", "MARKOY", "6985368422", "Street 47", 225);
INSERT INTO accs(name, surname, contactPhone, address, accBalance)
 VALUES ("IWANNA", "IWANNOY", "6976663345", "Street 107", 150);
INSERT INTO accs(name, surname, contactPhone, address, accBalance)
 VALUES ("IWANNHS", "IWANNOY", "6977775569", "Street 107", 10);
INSERT INTO accs(name, surname, contactPhone, address, accBalance)
 VALUES ("GIWRGOS", "GIWRGOY", "6966665544", "Street 21", 1000);

SELECT * FROM accs;