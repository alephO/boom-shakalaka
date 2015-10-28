CREATE TABLE tourists (
	user_id serial,
	user_name text,
	password text
);

ALTER TABLE tourists ADD PRIMARY KEY (user_id);

CREATE TABLE guides(
	user_id serial PRIMARY KEY,
	user_name text,
	password text
);
ALTER TABLE guides ADD COLUMN nation integer references nation (nation_id);

CREATE TABLE request (
	request_id serial PRIMARY KEY,
	tourist integer references tourists(user_id),
	guide integer references guides(user_id),
	requirement_1 integer references requirement(requirement_id),
	requirement_2 integer references requirement(requirement_id),
	requirement_3 integer references requirement(requirement_id),
	requirement_4 integer references requirement(requirement_id),
	fromdate date,
	todate date,
	status text,
	nation integer references nation (nation_id)
);


CREATE TABLE nation (
	nation_id serial PRIMARY KEY NOT NULL,
	nation_name text NOT NULL
);


CREATE TABLE requirement (
	requirement_id serial PRIMARY KEY NOT NULL,
	requirement_name text NOT NULL
);


CREATE TABLE message (
	message_id serial PRIMARY KEY NOT NULL,
	user_1 integer references tourists(user_id),
	user_2 integer references guides(user_id),
	time timestamp,
	content text
);

/* insert dummy users*/
insert into tourists(user_name,password) values('jackzhong','12345');
insert into guides(user_name,password) values('jialongchen','12345');
insert into tourists(user_name,password) values('chaoliu','12345');
insert into guides(user_name,password) values('chaoliu','12345');

/* insert requirements*/
insert into requirement(requirement_name) values('SightSeeing');
insert into requirement(requirement_name) values('Eat and Drink');
insert into requirement(requirement_name) values('Shopping');
insert into requirement(requirement_name) values('Transportation');

/* insert dummy requests*/
insert into request(tourist,requirement_1,requirement_2,requirement_3,requirement_4,fromdate,todate,status,nation) 
	values(
	(select user_id from tourists where user_name = 'jackzhong'),
	(select requirement_id from requirement where requirement_name = 'SightSeeing'),
	(select requirement_id from requirement where requirement_name = 'Shopping'),
	(select requirement_id from requirement where requirement_name = 'Transportation'),
	(select requirement_id from requirement where requirement_name = 'Eat and Drink'),
	'2015-10-25','2015-10-28','pending',
	(select nation_id from nation where nation_name = 'United States'));

/* dummy guide accept a request */
Update request set guide = (select user_id from guides where user_name = 'chaoliu'), status = 'upcoming'
	where request_id = '4';

/* dummy cancel a request*/
Delete from request where request_id = '3';

select * from request where tourist = (select user_id from tourists where user_name = 'jackzhong') AND status = 'pending';
/* dummy message between user1 and user2 */
insert into message(user_1, user_2, time, content) values (
	(select user_id from tourists where user_name = 'jackzhong'),
	(select user_id from guides where user_name = 'chaoliu'),
	'2015-10-25 10:23:42+02',
	'hello world!'
);

