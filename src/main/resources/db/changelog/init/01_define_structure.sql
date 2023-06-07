CREATE TABLE person (
	id int8 NOT NULL,
	date_of_birth date NOT NULL,
	"name" varchar(500) NOT NULL,
	"password" varchar(500) NOT NULL,
	CONSTRAINT client_pkey PRIMARY KEY (id)
);

CREATE TABLE email_data (
	id int8 NOT NULL,
	email varchar(200) NULL,
	person_id int8 NULL,
	CONSTRAINT email_data_pkey PRIMARY KEY (id)
);

ALTER TABLE email_data ADD CONSTRAINT email_client FOREIGN KEY (person_id) REFERENCES person(id);

CREATE TABLE phone_data (
	id int8 NOT NULL,
	phone varchar(13) NULL,
	person_id int8 NULL,
	CONSTRAINT phone_data_pkey PRIMARY KEY (id)
);

ALTER TABLE phone_data ADD CONSTRAINT phone_client FOREIGN KEY (person_id) REFERENCES person(id);

CREATE TABLE account (
	id int8 NOT NULL,
	balance numeric(19,2) NULL,
	person_id int8 NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id)
);

ALTER TABLE account ADD CONSTRAINT account_client FOREIGN KEY (person_id) REFERENCES person(id);

CREATE SEQUENCE  MY_CUSTOM_SEQ MINVALUE 1 START WITH 1 INCREMENT BY 1;