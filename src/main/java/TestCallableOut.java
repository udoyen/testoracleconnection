import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Scanner;

public class TestCallableOut {
    public static void main(String[] args) {
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
                CallableStatement callableStatement = Objects.requireNonNull(conn)
                        .prepareCall("{call gettotalnumberofemployee (?,?,?)}");
                Scanner scanner = new Scanner(System.in)
                ) {

            System.out.println("Enter Employee Birth Date : ");
            java.sql.Date birth_date = java.sql.Date.valueOf(scanner.nextLine());

            System.out.println("Enter Employee Gender : ");
            String gender = scanner.nextLine();

            // Set the IN parameters
            callableStatement.setDate(1, birth_date);
            callableStatement.setString(2, gender);


            // Get the out parameter
            callableStatement.registerOutParameter(3, Types.INTEGER);

            callableStatement.execute();

            // Get OUT parameter
            int totalEmployees = callableStatement.getInt(3);

            System.out.println("Total Employees : " + totalEmployees);

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
