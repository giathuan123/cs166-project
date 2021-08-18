DROP TABLE IF EXISTS Customer CASCADE;--OK
DROP TABLE IF EXISTS Mechanic CASCADE;--OK
DROP TABLE IF EXISTS Car CASCADE;--OK
DROP TABLE IF EXISTS Owns CASCADE;--OK
DROP TABLE IF EXISTS Service_Request CASCADE;--OK
DROP TABLE IF EXISTS Closed_Request CASCADE;--OK


-------------
---DOMAINS---
-------------
CREATE DOMAIN us_postal_code AS TEXT CHECK(VALUE ~ '^\d{5}$' OR VALUE ~ '^\d{5}-\d{4}$');
CREATE DOMAIN _STATUS CHAR(1) CHECK (value IN ( 'W' , 'C', 'R' ) );
CREATE DOMAIN _GENDER CHAR(1) CHECK (value IN ( 'F' , 'M' ) );
CREATE DOMAIN _CODE CHAR(2) CHECK (value IN ( 'MJ' , 'MN', 'SV' ) ); --Major, Minimum, Service
CREATE DOMAIN _PINTEGER AS int4 CHECK(VALUE > 0);
CREATE DOMAIN _PZEROINTEGER AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _YEARS AS int4 CHECK(VALUE >= 0 AND VALUE < 100);
CREATE DOMAIN _YEAR AS int4 CHECK(VALUE >= 1970);

------------
---TABLES---
------------
CREATE TABLE Customer
(
	id SERIAL,
	fname CHAR(32) NOT NULL,
	lname CHAR(32) NOT NULL,
	phone CHAR(13) NOT NULL,
	address CHAR(256) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Mechanic
(
	id SERIAL,
	fname CHAR(32) NOT NULL,
	lname CHAR(32) NOT NULL,
	experience _YEARS NOT NULL,
	PRIMARY KEY (id) 
);

CREATE TABLE Car
(
	vin VARCHAR(16) NOT NULL,
	make VARCHAR(32) NOT NULL,
	model VARCHAR(32) NOT NULL,
	year _YEAR NOT NULL,
	PRIMARY KEY (vin)
);
---------------
---RELATIONS---
---------------
CREATE TABLE Owns
(
	ownership_id SERIAL,
	customer_id INTEGER NOT NULL,
	car_vin VARCHAR(16) NOT NULL,
	PRIMARY KEY (ownership_id),
	FOREIGN KEY (customer_id) REFERENCES Customer(id),
	FOREIGN KEY (car_vin) REFERENCES Car(vin)
);

CREATE TABLE Service_Request
(
  rid SERIAL,
	customer_id INTEGER NOT NULL,
	car_vin VARCHAR(16) NOT NULL,
	date DATE NOT NULL,
	odometer _PINTEGER NOT NULL,
	complain TEXT,
	PRIMARY KEY (rid),
	FOREIGN KEY (customer_id) REFERENCES Customer(id),
	FOREIGN KEY (car_vin) REFERENCES Car(vin)
);

CREATE TABLE Closed_Request
(
  wid SERIAL,
	rid INTEGER NOT NULL,
	mid INTEGER NOT NULL,
	date DATE NOT NULL,
	comment TEXT,
	bill _PINTEGER NOT NULL,
	PRIMARY KEY (wid),
	FOREIGN KEY (rid) REFERENCES Service_Request(rid),
	FOREIGN KEY (mid) REFERENCES Mechanic(id)
);

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Customer (
	id,
	fname,
	lname,
	phone,
	address
)
FROM 'customer.csv'
WITH DELIMITER ',';
-- auto set serial id
SELECT setval('customer_id_seq', max(id)) from Customer;

COPY Mechanic (
	id,
	fname,
	lname,
	experience
)
FROM 'mechanic.csv'
WITH DELIMITER ',';

-- auto set serial id
SELECT setval('mechanic_id_seq', max(id)) from Mechanic;

COPY Car ( vin,
	make,
	model,
	year
)
FROM 'car.csv'
WITH DELIMITER ',';

COPY Owns (
	ownership_id,
	customer_id,
	car_vin
)
FROM 'owns.csv'
WITH DELIMITER ',';

-- auto set serial id
SELECT setval('owns_ownership_id_seq', max(ownership_id)) from Owns;

COPY Service_Request (
	rid,
	customer_id,
	car_vin,
	date,
	odometer,
	complain
)
FROM 'service_request.csv'
WITH DELIMITER ',';

-- auto set serial id
SELECT setval('service_request_rid_seq', max(rid)) from Service_Request;

COPY Closed_Request (
	wid,
	rid,
	mid,
	date,
	comment,
	bill
)
FROM 'closed_request.csv'
WITH DELIMITER ',';

SELECT setval('closed_request_wid_seq', max(wid)) from Closed_Request;
---------------------
-- SCHEMA TRIGGERS --
---------------------
-- Constrait 1: mileage of new service_request must be at least
--              the maximum requests from previous service_request

-- Constrait 2: any date inserted must be before or at the current
--              date
