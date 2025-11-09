import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class Main {
    public static void getAllStudents() {
        // Credentials to connect to postgres database 'a3_db' on port 5432
        String url = "jdbc:postgresql://localhost:5432/a3_db";
        String user = "postgres";
        String password = "birthday";

        // Handle an unsuccessful connection to the database
        try {
            // Upload the Driver
            Class.forName("org.postgresql.Driver");
            // Make connection with specified url, username, and password
            Connection connection = DriverManager.getConnection(url, user, password);
            // Create a new statement
            Statement statement = connection.createStatement();

            // Select all data from the database and display it in the terminal for the user
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                System.out.print(resultSet.getInt("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.println(resultSet.getDate("enrollment_date"));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void addStudent(String first_name, String last_name, String email, String enrollment_date) {
        // Set up url (using the localhost and 5432 as the port for postgres), and username and password for postgres
        String url = "jdbc:postgresql://localhost:5432/a3_db";
        String user = "postgres";
        String password = "birthday";

        // Convert enrollment_date into the Date data type
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(enrollment_date, formatter);

        // Handle an unsuccessful connection to the database
        try {
            // Upload the Driver
            Class.forName("org.postgresql.Driver");
            // Make connection with specified url, username, and password
            Connection connection = DriverManager.getConnection(url, user, password);
            // Create a new statement
            Statement statement = connection.createStatement();

            // Insert a new student into the database
            statement.executeUpdate(
                    String.format("INSERT INTO students (first_name, last_name, email, enrollment_date)" +
                    "VALUES ('%s', '%s', '%s', '%s')", first_name, last_name, email, date)
            );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void updateStudentEmail(int student_id, String email) {
        // Set up url (using the localhost and 5432 as the port for postgres), and username and password for postgres
        String url = "jdbc:postgresql://localhost:5432/a3_db";
        String user = "postgres";
        String password = "birthday";

        // Handle an unsuccessful connection to the database
        try {
            // Upload the Driver
            Class.forName("org.postgresql.Driver");
            // Make connection with specified url, username, and password
            Connection connection = DriverManager.getConnection(url, user, password);
            // Create a new statement
            Statement statement = connection.createStatement();

            // Update the email of the specified student
            statement.executeUpdate(
                    String.format("UPDATE students SET email = '%s' WHERE student_id = %d", email, student_id)
            );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void deleteStudent(int student_id) {
        // Set up url (using the localhost and 5432 as the port for postgres), and username and password for postgres
        String url = "jdbc:postgresql://localhost:5432/a3_db";
        String user = "postgres";
        String password = "birthday";

        // Handle an unsuccessful connection to the database
        try {
            // Upload driver
            Class.forName("org.postgresql.Driver");
            // Make connection with specified url, username, and password
            Connection connection = DriverManager.getConnection(url, user, password);
            // Create a new statement
            Statement statement = connection.createStatement();

            // Delete the specified student from the database
            statement.executeUpdate(
                    String.format("DELETE FROM students WHERE student_id = %d", student_id)
            );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            // Select an option
            System.out.println(
                    "\nType the number of the function you want to test then press Enter: \n" +
                            "\t(1) getAllStudents()\n" +
                            "\t(2) addStudent()\n" +
                            "\t(3) updateStudentEmail()\n" +
                            "\t(4) deleteStudent()\n" +
                            "\t(5) EXIT"
            );
            int choice = scanner.nextInt();

            // Call the function
            switch (choice) {
                case 1: {
                    // Call getAllStudents to display the results from the table
                    getAllStudents();
                    break;
                }
                case 2: {
                    // Clear the buffer
                    scanner.nextLine();

                    // Collect data to insert into students
                    System.out.print("Enter the student's first name: ");
                    String first_name = scanner.nextLine();
                    System.out.print("Enter the student's last name: ");
                    String last_name = scanner.nextLine();
                    System.out.print("Enter the student's email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter the student's enrollment_date (with the form yyyy-MM-dd): ");
                    String enrollment_date = scanner.nextLine();

                    // Call addStudent to add the student to the table
                    addStudent(first_name, last_name, email, enrollment_date);
                    break;
                }
                case 3: {
                    // Clear the buffer
                    scanner.nextLine();

                    // Collect data for update
                    System.out.print("Enter the student's id: ");
                    int student_id = scanner.nextInt();

                    // Clear the buffer
                    scanner.nextLine();

                    System.out.print("Enter the new email: ");
                    String email = scanner.nextLine();

                    // Call updateStudentEmail to update the student's email in the table
                    updateStudentEmail(student_id, email);
                    break;
                }
                case 4: {
                    // Clear the buffer
                    scanner.nextLine();

                    // Collect id of student to delete
                    System.out.print("Enter the student's id: ");
                    int student_id = scanner.nextInt();

                    // Call deleteStudent to delete the corresponding student from the table
                    deleteStudent(student_id);
                    break;
                }
                case 5: {
                    exit = true;
                    break;
                }
            }
        }

        scanner.close();
    }
}
