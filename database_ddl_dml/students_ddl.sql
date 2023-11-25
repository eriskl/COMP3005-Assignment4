-- COMP 3005 Assignment 4 Part 1
-- by Eris Keogh-Lim    101094703

create table students
    (
    student_id     serial,
    first_name     text    not null,
    last_name      text    not null,
    email          text    not null unique,
    enrollment_date date,
    
    primary key (student_id)
    );
