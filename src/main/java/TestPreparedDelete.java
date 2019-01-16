import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestPreparedDelete {
    public static void main(String[] args) throws SQLException {

        Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
        String sql = "Delete from employees where emp_no = ?";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Employee Number: ");
        int emp_no = scanner.nextInt();

        PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

        pstmt.setInt(1, emp_no);
        int result = pstmt.executeUpdate();
        if (result == 1) {
            System.out.println("Employee was successfully deleted!");
        } else {
            System.out.println("The employee was not deleted!");
        }

        scanner.close();
        pstmt.close();
        conn.close();
    }

}
