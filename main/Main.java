import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    public static void main(String[] args){
        String url = "jdbc:postgresql://localhost:5433/assignment 3";
        String user = "postgres";
        String password = "admin";

        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null)
                System.out.println("Connected");
            else
                System.out.println("Failed to connect");

            Statement statement = connection.createStatement();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PreparedStatement ps;
            String SQL, fname, lname, email, date;
            int id;

            while (true){
                System.out.println("1. Get all students\n" +
                                "2. Add a student\n" +
                                "3. Update student email\n" +
                                "4. delete student\n" +
                                "5. exit\n" +
                                "Enter: "
                );
                try {
                    int input = Integer.parseInt(reader.readLine());

                    switch (input){
                        case 1:
                            display(statement);
                            break;
                        case 2:
                            System.out.print("Enter first name: ");
                            fname = reader.readLine();
                            System.out.print("Enter last name: ");
                            lname = reader.readLine();
                            System.out.println("Enter email: ");
                            email = reader.readLine();
                            System.out.println("Enter enrollment date: ");
                            date = reader.readLine();

                            SQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?,?,?,?)";
                            ps = connection.prepareStatement(SQL);
                            ps.setString(1, fname);
                            ps.setString(2, lname);
                            ps.setString(3, email);
                            ps.setDate(4, Date.valueOf(date));
                            ps.executeUpdate();

                            System.out.println("Student added");
                            break;
                        case 3:
                            System.out.print("Enter id: ");
                            id = Integer.parseInt(reader.readLine());
                            System.out.print("Enter new email: ");
                            email = reader.readLine();

                            SQL = "UPDATE students SET email = ? WHERE student_id = ?";
                            ps = connection.prepareStatement(SQL);
                            ps.setString(1, email);
                            ps.setInt(2, id);
                            ps.executeUpdate();

                            System.out.println("email updated");
                            break;
                        case 4:
                            System.out.println("Enter id");
                            id = Integer.parseInt(reader.readLine());

                            SQL = "DELETE FROM students WHERE student_id = ?";
                            ps = connection.prepareStatement(SQL);
                            ps.setInt(1, id);
                            ps.executeUpdate();

                            System.out.println("Student deleted");
                            break;
                        case 5:
                            System.exit(0);
                    }

                }catch (NumberFormatException  e){
                    System.out.println("Incorrect input");
                    continue;
                }
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void display(Statement statement) throws SQLException{
        ResultSet rs = statement.executeQuery("SELECT * FROM students");
        while(rs.next()){
            System.out.println("Student ID: " + rs.getInt("student_id") + "\n" +
                    "First Name: " + rs.getString("first_name") + "\n" +
                    "Last Name: " + rs.getString("last_name") + "\n" +
                    "Email: " + rs.getString("email") + "\n" +
                    "Enrollment Date: " + rs.getDate("enrollment_date") + "\n"
            );
        }
    }
}


