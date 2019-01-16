import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class TestStaticSQLStatement {
    public static void main(String[] args) {
        Statement stat = null;
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQLDB)) {
            stat = Objects.requireNonNull(conn).createStatement();
            rs = stat.executeQuery("select * from employees");
            rs.last();

            System.out.println("Total rows: " + rs.getRow());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
