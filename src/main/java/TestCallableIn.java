import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestCallableIn {
    public static void main(String[] args) {
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
                CallableStatement callableStatement = Objects.requireNonNull(conn).prepareCall("{call addnewemployee(?,?,?,?,?,?)}")

        ) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee # :");
            int empno = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Employee Birth Date : ");
            java.sql.Date birth_date = java.sql.Date.valueOf(scanner.nextLine());

            System.out.println("Employee First Name: ");
            String first_name = scanner.nextLine();

            System.out.println("Employees Last Name: ");
            String last_name = scanner.nextLine();

            System.out.println("Employee Gender: ");
            String gender = scanner.nextLine();

            System.out.println("Employee Hire Date: ");
            java.sql.Date hire_date = java.sql.Date.valueOf(scanner.nextLine());

            callableStatement.setInt(1, empno);
            callableStatement.setDate(2, birth_date);
            callableStatement.setString(3, first_name);
            callableStatement.setString(4, last_name);
            callableStatement.setString(5, gender);
            callableStatement.setDate(6, hire_date);

            callableStatement.execute();
            System.out.println("Employee Added Successfully!");
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
