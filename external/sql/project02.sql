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
	phone_number varchar(10),
	fund numeric(6, 2)
);

create table if not exists dogs (
	id serial primary key,
	user_email varchar references users(email),
	status boolean,		-- active, not active (whether user can see it) - delete from user perspective : BUT we need this for transactions
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
insert into users values
('owner',		'secret',	'Wolf',		'Flow',		'OWNER',		'5555555555',	100.00),
('email1',		'pass1',	'Alice',	'Apple',	'CUSTOMER',		'1234567890',	1000.00),
('email2',		'pass2',	'Bob',		'Bacon',	'CUSTOMER',		'1112223333',	100.00),
('email3',		'pass3',	'Carl',		'Cake',		'CUSTOMER',		'4445556666',	10.00),
('email4',		'pass4',	'David',	'Dumpling',	'CUSTOMER',		'7778889999',	0.00),
('email5',		'pass5',	'Evelyn',	'Eggnog',	'CUSTOMER',		'2224448888',	1000.00),
('dogLover',	'pass6',	'Frank',	'Fudge',	'CUSTOMER',		'2358132134',	3.92);

insert into dogs values
-- email1
(default,	'email1',	true,	'Nikita',		'Black Russian Terrier',	2,	true),
(default,	'email1',	true,	'Tyrion',		'German Shepherd',			2,	false),
(default,	'email1',	true,	'Blade',		'Affenpinscher',			2,	true),
(default,	'email1',	false,	'Yeti',			'Beagle',					2,	false),
-- email2 - active & nonactive
(default,	'email2',	true,	'Ribena',		'Boxer',					2,	true),
(default,	'email2',	false,	'Riddick',		'Bichon Frise',				2,	true),
-- email3 - vacinated & non vacinated
(default,	'email3',	true,	'Marmite',		'Maltese Dog',				2,	true),
(default,	'email3',	true,	'Lupin',		'Chow Chow',				2,	false),
-- email4 - normal dog - no cash
(default,	'email4',	true,	'Gryffindor',	'Japanese Chin',			2,	true),
-- email5 - no dogs (blank slate)
-- dogLover - all the dogs
(default,	'dogLover',	true,	'Basquiat',		'Siberian Husky',			2,	true),
(default,	'dogLover',	true,	'Caramel',		'Corgi',					2,	true),
(default,	'dogLover',	true,	'Pimms',		'Afghan Hound',				2,	true),
(default,	'dogLover',	true,	'Arrow',		'Staffordshire Terrier',	2,	true),
(default,	'dogLover',	true,	'Ash',			'Chihuahua Devil',			2,	true),
(default,	'dogLover',	true,	'Cinder',		'Komondor',					2,	true),
(default,	'dogLover',	true,	'Harper',		'Bedlington Terrier',		2,	true),
(default,	'dogLover',	true,	'Kane',			'Puli',						2,	true),
(default,	'dogLover',	true,	'Ursa',			'Russian Borzoi',			2,	true),
(default,	'dogLover',	true,	'Pip',			'Brussels Griffon',			2,	true),
(default,	'dogLover',	true,	'Mixie',		'French Bulldog',			2,	true);

insert into services values
(default,	'RATE',			1,	10.00),
(default,	'GROOMING',		2,	25.00),
(default,	'BELLYRUB',		1,	0.99),
(default,	'DOGWALK',		2,	15.00),
(default,	'TRIMNAILS',	1,	19.99);

insert into reservations values
-- Current day (7 dogs)
(default,	'email1',	1,	'REGISTERED',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'email1',	2,	'REGISTERED',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'email2',	5,	'CHECKEDIN',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'email3',	7,	'CHECKEDIN',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'email3',	8,	'CANCELLED',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'dogLover',	10,	'CHECKEDOUT',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
(default,	'dogLover',	11,	'REGISTERED',	'2022-07-01 9:00:00',	'2022-07-01 11:00:00'),
-- Past days
(default,	'dogLover',	12,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	13,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	14,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	15,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	16,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	17,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	18,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	19,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00'),
(default,	'dogLover',	20,	'CHECKEDOUT',	'2022-06-01 9:00:00',	'2022-06-01 11:00:00');

insert into transactions values
-- Current day
(default,	'email1',	null,	1,	20.00),
(default,	'email1',	2,		2,	45.00),
(default,	'email2',	3,		3,	20.99),
(default,	'email3',	4,		4,	35.00),
(default,	'email3',	5,		5,	39.99),
(default,	'dogLover',	null,	6,	20.00),
(default,	'dogLover',	null,	7,	20.00),
-- Past days
(default,	'dogLover',	null,	8,	20.00),
(default,	'dogLover',	null,	9,	20.00),
(default,	'dogLover',	null,	10,	20.00),
(default,	'dogLover',	null,	11,	20.00),
(default,	'dogLover',	null,	12,	20.00),
(default,	'dogLover',	null,	13,	20.00),
(default,	'dogLover',	null,	14,	20.00),
(default,	'dogLover',	null,	15,	20.00),
(default,	'dogLover',	null,	16,	20.00);
--
---- View Data
--select * from users;
--select * from dogs;
--select * from services;
--select * from reservations;
--select * from transactions;
--
---- All dogs of user (gives back all dog infromation related to user)
--select d.*
--from users, dogs d
--where users.email = d.user_email;