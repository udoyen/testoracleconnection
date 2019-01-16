import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class TestPreparedUpdate {
    public static void main(String[] args) throws SQLException {

            Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
            int emp_no;
            String first_name;

            String sql = "Update employees set first_name = ? where emp_no = ? ";

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Employee Number: ");
            emp_no = scanner.nextInt();

            System.out.println("Enter New First Name: ");
            first_name = scanner.next();

            PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

            pstmt.setInt(2, emp_no);
            pstmt.setString(1, first_name);

            int result = pstmt.executeUpdate();

            if(result == 1) {
                System.out.println("The record was successfully updated!");
            } else {
                System.out.println("A error in updating the record!");
            }

            scanner.close();
            pstmt.close();
            conn.close();



    }
}
