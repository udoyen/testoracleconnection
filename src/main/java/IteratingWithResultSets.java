import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class IteratingWithResultSets {

    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
             Statement stmt = Objects.requireNonNull(conn).createStatement();
             ResultSet rs = stmt.executeQuery("select * from employees")
        ) {
            String format = "No.:%d, Sex:%s %-15s %-15S Hire Date: %s%n";
            while (rs.next()) {
                System.out.format(format, rs.getInt("emp_no"),
                        rs.getString("gender"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("hire_date").toString());
            }
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
