// COMP 3005 Assignment 4 Part 1
// by Eris Keogh-Lim    101094703
// Code here built off the example shown in class

import java.sql.*;
import java.util.Scanner;

public class StudentsDBApp {

    public static Connection conn;

    // Retrieves and displays all records from the students table
    // Returns the number of rows found in the students table
    public static int getAllStudents() {
        int records_found = 0;
        try{
            // Set up query
            String query = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            // Display results
            while (results.next()){
                records_found++;
                System.out.print("student_id: " + results.getInt("student_id"));
                System.out.print(", first_name: " + results.getString("first_name"));
                System.out.print(", last_name: " + results.getString("last_name"));
                System.out.print(", email: " + results.getString("email"));
                System.out.println(", enrollment_date: " + results.getString("enrollment_date"));
            }
        } catch (SQLException e){
            System.out.println("Failed to retreive data from students table.");
            e.printStackTrace();
        }

        return records_found;
    }

    // Inserts a new student record into the students table.
    // Returns the number of rows affected. Return value of -1 indicates an error occured.
    public static int addStudent(String first_name, String last_name, String email, String enrollment_date) {
        int rows = -1;
        // Check that values to insert are not null.
        if (first_name.length() < 1) { System.out.println("first_name was left empty. You must enter a first_name."); return 0;}
        if (last_name.length() < 1) { System.out.println("last_name was left empty. You must enter a last_name."); return 0;}
        if (email.length() < 1) { System.out.println("email was left empty. You must enter an email."); return 0;}
        if (enrollment_date.length() < 1) { System.out.println("enrollment_date was left empty. You must enter an enrollment_date."); return 0;}

        // Query to see if student with existing email already in table, abort if true
        try {
            String query = "SELECT * FROM students WHERE email = \'" + email + "\'";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);
            if (results.next()) { System.out.println("Email already in use. Each new student's email must be unique."); return 0;}
        } catch (SQLException e){
            System.out.print("Failed to query students table due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        // Add student to table
        try{
            // Set up query
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES \n" + 
                         "(\'" + first_name + "\', \'" + last_name + "\', \'" + email + "\', \'" + enrollment_date + "\');";
            Statement stmt = conn.createStatement();
            // Run query
            rows = stmt.executeUpdate(sql);
            if (rows < 1) {
                System.out.println("Student not added to student table. Check if date was valid.");
            } else if (rows == 1) {
                System.out.println("Student added to students table");
            } else {
                System.out.println("Student added to table but somehow multiple rows were affected. This should never happen.");
            }
        } catch (SQLException e){
            System.out.print("Failed to add student due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        return rows;
    }

    // Updates the email address for a student with the specified student_id
    // Returns the number of rows affected. Return value of -1 indicates an error occured.
    public static int updateStudentEmail(int student_id, String new_email) {
        int rows = -1;
        // Check email is not empty
        if (new_email.length() < 1) { System.out.println("new_email was left empty. You must enter a new_email."); return 0;}

        // Query to see if the student with student_id is in the table
        try {
            String query = "SELECT * FROM students WHERE student_id = " + student_id;
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);
            if (!results.next()) { System.out.println("Student with that student_id not found."); return 0;}
        } catch (SQLException e){
            System.out.print("Failed to query students table due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        // Query to see if student with existing email already in table, abort if true
        try {
            String query = "SELECT * FROM students WHERE email = \'" + new_email + "\'";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);
            if (results.next()) { System.out.println("Email already in use. Each student's email must be unique."); return 0;}
        } catch (SQLException e){
            System.out.print("Failed to query students table due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        // Update the student
        try{
            // Set up query
            String sql = "UPDATE students " + 
                         "SET email = \'" + new_email + "\' WHERE student_id = " + student_id;
            Statement stmt = conn.createStatement();
            // Run query
            rows = stmt.executeUpdate(sql);
            if (rows < 1) {
                System.out.println("Student email not updated - unknown error"); // not sure how we can get here if student_id and new_email have been checked
            } else if (rows == 1) {
                System.out.println("Student email updated.");
            } else {
                System.out.println("Student email updated but somehow multiple rows were affected. This should never happen.");
            }
        } catch (SQLException e){
            System.out.print("Failed to add student due to SQLException.");
            e.printStackTrace();
        }

        return rows;
    }

    // Deletes the record of the student with the specified student_id
    // Returns the number of rows affected. Return value of -1 indicates an error occured.
    public static int deleteStudent(int student_id) {
        int rows = -1;

        // Query to see if the student with student_id is in the table
        try {
            String query = "SELECT * FROM students WHERE student_id = " + student_id;
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);
            if (!results.next()) { System.out.println("Student with that student_id not found."); return 0;}
        } catch (SQLException e){
            System.out.print("Failed to query students table due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        // Delete student from table
        try{
            // Set up query
            String sql = "DELETE FROM students WHERE student_id = " + student_id;
            Statement stmt = conn.createStatement();
            // Run query
            rows = stmt.executeUpdate(sql);
            if (rows < 1) {
                System.out.println("Student not deleted from table - unknown error."); // not sure how we can get here if student_id was checked
            } else if (rows == 1) {
                System.out.println("Student deleted from table table");
            } else {
                System.out.println("Somehow multiple students were deleted. This should never happen.");
            }
        } catch (SQLException e){
            System.out.print("Failed to add student due to SQLException.");
            e.printStackTrace();
            return rows;
        }

        return rows;
    }

    // Gives the user various prompts for the purpose of testing the above 4 functions.
    public static void testFunctions(Connection conn) {
        Scanner scan = new Scanner(System.in);

        // Test getAllStudents()
        System.out.println("Testing getAllStudents() ...");
        int records_found = getAllStudents();
        System.out.println("getAllStudents() complete with " + records_found + " records found.");
        System.out.println();

        // Test addStudent()
        String first_name = "";
        String last_name = "";
        String email = "";
        String enrollment_date = "";
        System.out.println("Testing addStudent() ...");
        System.out.print("Enter the student's first_name: ");
        first_name = scan.nextLine();
        System.out.print("Enter the student's last_name: ");
        last_name = scan.nextLine();
        System.out.print("Enter the student's email: ");
        email = scan.nextLine();
        System.out.print("Enter the student's enrollment_date. Format is YYYY-MM-DD: ");
        enrollment_date = scan.nextLine();
        int rows_affected = addStudent(first_name, last_name, email, enrollment_date);
        if (rows_affected == 1) System.out.println("addStudent() test successful.");
        else if (rows_affected == 0) System.out.println("addStudent() ran correctly but did not add a student.");
        else System.out.println("addStudent() test failed.");
        System.out.println();

        // Test updateStudentEmail()
        int student_id = -1;
        email = "";
        System.out.println("Testing updateStudentEmail() ...");
        System.out.print("Enter the student_id of the student to be updated: ");
        student_id = scan.nextInt();
        scan.nextLine(); // Read in the newline char
        System.out.print("Enter the new email for that student: ");
        email = scan.nextLine();
        rows_affected = updateStudentEmail(student_id, email);
        if (rows_affected == 1) System.out.println("updateStudentEmail() test successful.");
        else if (rows_affected == 0) System.out.println("updateStudentEmail() ran correctly but did not update student.");
        else System.out.println("updateStudentEmail() test failed.");
        System.out.println();

        // Test deleteStudent()
        student_id = -1;
        System.out.println("Testing deleteStudent() ...");
        System.out.print("Enter the student_id of the student to be deleted: ");
        student_id = scan.nextInt();
        scan.nextLine();
        rows_affected = deleteStudent(student_id);
        if (rows_affected == 1) System.out.println("deleteStudent() test successful.");
        else if (rows_affected == 0) System.out.println("deleteStudent() ran correctly but did not delete a student.");
        else System.out.println("deleteStudent() test failed.");

        // Testing complete
    }

    // Attempts to establish a connectin to the database
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/students_db" ;
        String user = "admin" ;
        String password = "verysecure" ;
        try { 
            // Connect to the database
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println( "Connected to PostgreSQL successfully!" );
                testFunctions(conn);
            } else {
                System.out.println( "Failed to establish connection." );
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

} // end class