import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestCallableResultSets {

    public static void main(String[] args) {
        try(
                Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
                CallableStatement callableStatement = Objects.requireNonNull(conn).prepareCall("{call getEmployeesByDept(?)}");
                Scanner scanner = new Scanner(System.in)
                ) {
            System.out.println("Enter Department Number : ");
            String deptNumber = scanner.nextLine();

            callableStatement.setString(1, deptNumber);
            ResultSet rs = callableStatement.executeQuery();

            String format = "%-10d%-15s%-20d%-25s%n";

            while(rs.next()) {
                System.out.format(
                        format,
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4)
                );
            }

            // Gets the last row in the
            // ResultSet
            rs.last();
            System.out.println("Total Number: " + rs.getRow());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
