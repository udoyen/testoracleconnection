import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestTransactionManagement {

    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection(DBType.MYSQLDB, "bank");
            // Prevent the database from committing the transaction results
            Objects.requireNonNull(conn).setAutoCommit(false);

            PreparedStatement pstmt;
            Scanner scanner = new Scanner(System.in);

            System.out.println("GTBank Transactions");
            System.out.println("----------------------");
            System.out.println("Enter Account # to Debit :");
            int fromAccNum = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter Amount to Transfer : ");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter Account Credit : ");
            int toAccNum = Integer.parseInt(scanner.nextLine());

            String withdrawSQL = "UPDATE account SET AVAIL_BALANCE = AVAIL_BALANCE - ?, PENDING_BALANCE = AVAIL_BALANCE WHERE ACCOUNT_ID = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(withdrawSQL);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, fromAccNum);
            int wd = pstmt.executeUpdate();

            // Update the transaction table
            if(wd != 0) {
                DBUtil.recordTransaction(conn, pstmt, amount, fromAccNum, "WD");
            }

            String depositSQL = "UPDATE account SET AVAIL_BALANCE = AVAIL_BALANCE + ?, PENDING_BALANCE = AVAIL_BALANCE WHERE ACCOUNT_ID = ?";
            pstmt = conn.prepareStatement(depositSQL);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, toAccNum);
            int cdt = pstmt.executeUpdate();

            // Update the transaction table
            if(cdt != 0) {
                DBUtil.recordTransaction(conn, pstmt, amount, toAccNum, "CDT");
            }

            String sql = "SELECT AVAIL_BALANCE FROM account WHERE ACCOUNT_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fromAccNum);
            ResultSet rs = pstmt.executeQuery();
            double balanceAmount = 0;
            if (rs.next()) {
                balanceAmount = rs.getDouble("AVAIL_BALANCE");
            }

            // Check to make sure the min balance
            // in the account ot debit is more than
            // N5000
            if(balanceAmount >= 5000) {
                conn.commit();
                System.out.println("Amount Transferred Successfully...");
            } else {
                conn.rollback();
                System.out.println("Insufficient Funds : " + balanceAmount + " Transactions Rolleback..");
            }

            scanner.close();
            pstmt.close();
            conn.close();


        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
