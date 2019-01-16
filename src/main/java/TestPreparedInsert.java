import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestPreparedInsert {

    public static void main(String[] args) {
        try {
            // Create a connection
            Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
            int emp_no;
            java.sql.Date birth_date;
            String first_name;
            String last_name;
            String gender;
            java.sql.Date hire_date;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Employee Number: ");
            emp_no = Integer.parseInt(scanner.nextLine());

            System.out.println("Employees Birth Date: ");
            birth_date = java.sql.Date.valueOf(scanner.nextLine());

            System.out.println("Employee First Name: ");
            first_name = scanner.nextLine();

            System.out.println("Employees Last Name: ");
            last_name = scanner.nextLine();

            System.out.println("Employee Gender: ");
            gender = scanner.nextLine();

            System.out.println("Employee Hire Date: ");
            hire_date = java.sql.Date.valueOf(scanner.nextLine());

            String sql = "insert into employees values ( ?, ?, ?, ?, ?, ?)";

            // Prepare the PreparedStatement
            PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

            // Set the values
            pstmt.setInt(1, emp_no);
            pstmt.setDate(2, birth_date);
            pstmt.setString(3, first_name);
            pstmt.setString(4, last_name);
            pstmt.setString(5, gender);
            pstmt.setDate(6, hire_date);

            // Execute the query
            int result = pstmt.executeUpdate();

            if(result == 1) {
                System.out.println("Record Successfully Inserted!");
            } else {
                System.out.println("Error while adding the record!");
            }

            // Close the resources
            scanner.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
