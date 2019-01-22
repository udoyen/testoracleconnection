import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestCallableBatchProcessing {
    public static void main(String[] args) {
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
                CallableStatement callableStatement = Objects.requireNonNull(conn).prepareCall("{call addnewemployee(?,?,?,?,?,?)}")

        ) {

            String options;
            do {
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
                callableStatement.addBatch();

                System.out.println("Do you want to add another record? ");
                options = scanner.nextLine();
            } while (options.equalsIgnoreCase("yes"));

            int[] updateCounts = callableStatement.executeBatch();
            System.out.println("Total Records Inserted are : " + updateCounts.length);
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
