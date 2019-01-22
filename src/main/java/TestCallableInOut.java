import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Scanner;

public class TestCallableInOut {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        CallableStatement callableStatement = null;
        Scanner scanner = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQLDB);
            callableStatement = conn.prepareCall("{call getemployeebyid(?,?,?,?,?,?,?)}");

            scanner = new Scanner(System.in);

            System.out.println("Enter Employee Number : ");
            int eid = Integer.parseInt(scanner.nextLine());

            callableStatement.setInt(1, eid);

            // Register the OUT parameters
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.registerOutParameter(3, Types.DATE);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.registerOutParameter(6, Types.VARCHAR);
            callableStatement.registerOutParameter(7, Types.DATE);

            callableStatement.execute();

            // Get the stored value from the stored procedure
            // getemployeebyid
            int emp_no = callableStatement.getInt(2);
            java.sql.Date bdate = callableStatement.getDate(3);
            String fname = callableStatement.getString(4);
            String lname = callableStatement.getString(5);
            String gender = callableStatement.getString(6);
            java.sql.Date hdate = callableStatement.getDate(7);

            System.out.println(fname);

            // make the second storedProcedure call
            callableStatement = conn.prepareCall("{call registeremployee(?,?,?,?,?,?,?)}");

            // Set the values

            callableStatement.setInt(1, emp_no);
            callableStatement.setDate(2, bdate);
            callableStatement.setString(3, fname);
            callableStatement.setString(4, lname);
            callableStatement.setString(5, gender);
            callableStatement.setDate(6, hdate);

            // register the id of the new employee
            callableStatement.registerOutParameter(7, Types.INTEGER);

            callableStatement.execute();

            // Get the id
            int neid = callableStatement.getInt(7);

            System.out.println("The New Employee Id is : " + neid);


        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        } finally {
            Objects.requireNonNull(scanner).close();
            callableStatement.close();
            conn.close();
        }
    }
}
