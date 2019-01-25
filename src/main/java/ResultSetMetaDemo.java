import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ResultSetMetaDemo {

    public static void main(String[] args) {
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQLDB, "bank");
                Statement stmt = Objects.requireNonNull(conn).createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM employee")
                ) {
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            String format = "%-50s%-25s%-20s%-20s\n";
            System.out.format(format, "Column Name", "Column Type", "Is Nullable", "Is AutoIncremmented");
            System.out.format(format, "------------", "-------------", "------------", "--------------------");
            for (int i = 1; i <= columnCount; i++) {
                System.out.format(format, rsmd.getColumnName(i),rsmd.getColumnType(i), rsmd.isNullable(i), rsmd.isAutoIncrement(i));
            }

            System.out.println("Total Columns : " + columnCount);
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
