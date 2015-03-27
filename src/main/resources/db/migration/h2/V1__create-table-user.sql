CREATE SCHEMA IF NOT EXISTS public;
set schema public;

-- Table: users

-- DROP TABLE users;

CREATE TABLE users(
	username varchar(50) PRIMARY KEY,
	password varchar(255) not null,
	enabled boolean not null);

-- Table: authorities

-- DROP TABLE authorities;
create table authorities (
      username varchar(50) not null,
      authority varchar(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username));      
