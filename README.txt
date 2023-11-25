COMP 3005 Assignment 4 Part 1
By Eris Keogh-Lim      101094703

Link to demonstration video: https://youtu.be/TPkyqN3lGC4
========================================================================================

Each of the 4 required functions are present and error-check their input, exiting gracefully if they encounter blank strings or information that violates the requirements of the students table (like an email already in use).
However, testFunctions() was not actually a requirement of the assignment, and so it does not always verify appropriate input.
It exists only for testing and demonstration purposes.
You can crash the program by, for example, giving a string when it asks for a student_id.
This is a problem with testFunctions() and not the 4 required functions.

========================================================================================
Looking at the code and comments probably provides a better explanation of the functions but the assignment spec says to include this in the README
Explanation of 4 functions:

getAllStudents() simply sends the query "SELECT * FROM students" to the database and displays the results.
The returned value is the number of rows found in the students table.

addStudent() takes in first_name, last_name, email, enrollment_date.
It first checks that none of the inputs are null (empty string).
Then it checks if the supplied email is not already in use.
If those two tests pass, it attempts to add the student to the table.
The returned value is the number of rows affected.
  It will be 0 if the input was invalid.
  1 if the function correctly added one student.
  If it returns -1 or some number >1, an error has occured.

updateStudentEmail() takes in a student_id and new_email.
It first checks if the email is not null.
Then it checks that a student with that student_id exists in the students table.
Then it checks if the supplied email is not already in use.
If those two tests pass, it updates the email of the student with student_id.
The returned value is the number of rows affected.
  It will be 0 if the input was invalid.
  1 if the function correctly updated an email.
  If it returns -1 or some number >1, an error has occured.

deleteStudent() takes in a student_id.
It first checks that a student with that student_id exists in the table.
If that test passes, it deletes that student from the table.
The returned value is the number of rows affected.
  It will be 0 if the input was invalid.
  1 if the function correctly deleted one student.
  If it returns -1 or some number >1, an error has occured.

========================================================================================

Instructions to run and test:
* These instructions assume you already have PostgreSQL, pgAdmin and Java installed. If you do not, go install them now.
- Create a new database in PostgreSQL called students_db
- Using the query tool, in the database_ddl_dml folder open and run students_ddl.sql
- Do the same for students_dml.sql
- If you have Java 8, the driver postgresql-42.7.0.jar in the java folder should be suitable.
- If you have another version of Java you may need to go to https://jdbc.postgresql.org/download/ and replace the driver .jar with the appropriate version.
- Open StudentsDBApp.java in an editor.
- On line 241, change USER to the username of a user (probably the admin) with adequate privileges in PostgreSQL
- On line 242, change PASSWORD to that user's password
- Open a PowerShell window in the java folder
- Compile the program using command
	javac StudentDBAppjava
- Run using command
	java -cp ".\postgresql-42.7.0.jar;.\" StudentsDBApp
- Follow the prompts in the console to test the 4 functions.
- You may need to run it multiple times to test different inputs for each prompt.

