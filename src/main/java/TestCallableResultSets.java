import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

            // Register the two OUT parameters
//            callableStatement.registerOutParameter(2, Types.JAVA_OBJECT);
//            callableStatement.registerOutParameter(3, Types.INTEGER);

//            callableStatement.execute();

//            ResultSet rs = (ResultSet) callableStatement.getObject(2);
            ResultSet rs = callableStatement.executeQuery();

            String format = "%-10d%-10s%-20d%-25s%n";

            while(rs.next()) {
                System.out.format(
                        format,
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4)
                );
            }
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
