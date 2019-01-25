import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBUtil {

    private static final String mySqlUser = "root";
    private static final String mySqlPwd = "admin";
    private static final String mySQLCS = "jdbc:mysql://172.17.0.2:3306/employees";
    private static final String mySQLCSBank = "jdbc:mysql://172.17.0.2:3306/newbank";


    static Connection getConnection(DBType dbType) throws SQLException {
        if (dbType == DBType.MYSQLDB) {
            return DriverManager.getConnection(mySQLCS, mySqlUser, mySqlPwd);
        }

        return null;
    }

    static Connection getConnection(DBType dbType, String bank) throws SQLException {
        if (dbType == DBType.MYSQLDB) {
            return DriverManager.getConnection(mySQLCSBank, mySqlUser, mySqlPwd);
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
     * @param dateFormat string
     * @return java sql date
     * @throws ParseException error
     */
    static java.sql.Date setMyDate(String dateFormat) throws ParseException {
        java.util.Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);

        return new java.sql.Date(eDate.getTime());

    }

    static void recordTransaction(Connection conn, PreparedStatement pstmt,
                                  double amount, int accId, String transacType
    ) {
        String command;
        try {
            if (transacType.equalsIgnoreCase("CDT")) {
                command = "INSERT INTO acc_transaction(AMOUNT, FUNDS_AVAIL_DATE, TXN_DATE, TXN_TYPE_CD, ACCOUNT_ID, EXECUTION_BRANCH_ID, TELLER_EMP_ID)" +
                        " values(?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(command);
                pstmt.setDouble(1, amount);
                pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
                pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
                pstmt.setString(4, transacType);
                pstmt.setInt(5, accId);
                pstmt.setString(6, null);
                pstmt.setString(7, null);
                if (pstmt.execute()) {
                    System.out.println("The Credit Transaction was Successful!");
                }
            } else if (transacType.equalsIgnoreCase("WD")) {

                command = "INSERT INTO acc_transaction(AMOUNT, FUNDS_AVAIL_DATE, TXN_DATE, TXN_TYPE_CD, ACCOUNT_ID, EXECUTION_BRANCH_ID, TELLER_EMP_ID)" +
                        " values(?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(command);
                pstmt.setDouble(1, amount);
                pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
                pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
                pstmt.setString(4, transacType);
                pstmt.setInt(5, accId);
                pstmt.setString(6, null);
                pstmt.setString(7, null);
                if (pstmt.execute()) {
                    System.out.println("The Withdrawal Transaction was Successful!");
                }
            } else {
                System.out.println("The Transaction Type isn't Known!");
            }
        } catch (SQLException e) {
            showErrorMessage(e);
        }


    }

}
