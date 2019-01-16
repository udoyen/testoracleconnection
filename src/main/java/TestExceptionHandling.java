import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class TestExceptionHandling {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection(DBType.MYSQLDB);
            assert conn != null;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("select * from employees");
            rs.last();
            System.out.println("Total No. of Rows :" + rs.getRow());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        } finally {
            Objects.requireNonNull(conn).close();
        }
    }
}
