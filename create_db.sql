create table if not exists users (
	id int(11) NOT NULL,
	username varchar(255) NOT NULL UNIQUE, 
	password varchar(255), 
	email varchar(255), 
	firstname varchar(255), 
	lastname varchar(255), 
	type enum('student', 'instructor'),
  CONSTRAINT users_pk PRIMARY KEY (id)
);

create table if not exists courses (
	id varchar(20) NOT NULL, 
	description text,
	title varchar(255), 
	instructor_id int(11),
	daysInSession int,
	INDEX (instructor_id),
	FOREIGN KEY (instructor_id) REFERENCES users(id),
	constraint courses_pk PRIMARY KEY (id)
);

create table if not exists course_enrollment (
	course_id varchar(20), 
	student_id int(11),
	FOREIGN KEY (course_id) REFERENCES courses(id),
	FOREIGN KEY (student_id) REFERENCES users(id)
);

create table if not exists attendance (
	id int(11) NOT NULL AUTO_INCREMENT,
	student_id int(11) NOT NULL,
	course_id varchar(20) NOT NULL,
	date date,
	status enum('present', 'absent', 'excused'),
	FOREIGN KEY (course_id) REFERENCES courses(id),
	FOREIGN KEY (student_id) REFERENCES users(id),
	  CONSTRAINT attendance_pk PRIMARY KEY (id)
	
);