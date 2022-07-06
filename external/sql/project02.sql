-- Refresh Tables
drop table if exists users cascade;
drop table if exists dogs cascade;
drop table if exists services cascade;
drop table if exists reservations cascade;
drop table if exists transactions cascade;

-- Creating Tables
create table if not exists users (
	email varchar primary key,
	pswd varchar,
	user_type varchar,	-- shopOwner, dogOwner
	first_name varchar,
	last_name varchar,
	phone_number varchar[10],
	fund numeric(6, 2)
);

create table if not exists dogs (
	id serial primary key,
	user_email varchar references users(email),
	status varchar,		-- active, not active (whether user can see it) - delete from user perspective : BUT we need this for transactions
	dog_name varchar,
	breed varchar,
	dog_age int,
	vaccinated boolean
);

-- Grooming, DogWalk, BellyRub, etc...
-- === Dog related ===
-- Grooming : $10
-- BellyRub $100
-- DogWalk $15
-- === Business Services ===
-- HourlyRate $20
create table if not exists services (
	id serial primary key,
	service_type varchar,
	duration_hour int,
	price numeric(6, 2)	-- 0.00 to 9999.99
);

-- REVIEW: Question: Combine Reservations & Transations (keep in mind)

-- Avaliable hours are from 8AM - 4PM
create table if not exists reservations (
	id serial primary key,
	user_email varchar references users(email),
	dog_id int references dogs(id),
	status varchar,				-- Registered, checkedin, finished, Cancelled
	start_datetime timestamp,
	end_datetime timestamp
);

create table if not exists transactions (
	id serial primary key,
	user_email varchar references users(email),
	service_id int references services(id),		-- Dog Related Service
	reservation_id int references reservations(id),
	total_cost numeric(6, 2)				-- TOTAL cost (include days and service)
);

-- Initial Data set
-- TODO do this later

-- View Data
select * from users;
select * from dogs;
select * from services;
select * from reservations;
select * from transactions;

-- All dogs of user (gives back all dog infromation related to user)
select d.*
from users, dogs d
where users.email = d.user_email;