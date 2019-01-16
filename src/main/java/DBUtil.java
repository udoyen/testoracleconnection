import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtil {

    private static final String mySqlUser = "root";
    private static final String mySqlPwd = "admin";
    private static final String mySQLCS = "jdbc:mysql://172.17.0.2:3306/employees";

    static Connection getConnection(DBType dbType) throws SQLException {
        if (dbType == DBType.MYSQLDB) {
            return DriverManager.getConnection(mySQLCS, mySqlUser, mySqlPwd);
        }

        return null;
    }

    static void showErrorMessage(SQLException e) {
        System.err.println("Error :" + e.getMessage());
        System.err.println("Error Code:" + e.getErrorCode());
    }

    static void showErrorMessage(ParseException e) {
        System.err.println("Error :" + e.getMessage());
    }

    /**
     *
     * @param dateFormat string
     * @return java sql date
     * @throws ParseException error
     */
    static java.sql.Date setMyDate(String dateFormat) throws ParseException {
        java.util.Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);

        return new java.sql.Date(eDate.getTime());

    }

}
