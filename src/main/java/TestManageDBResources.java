import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TestManageDBResources {

    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        try {
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//            connection = DriverManager.getConnection(dbUrl, username, password);
            connection = DBUtil.getConnection(DBType.MYSQLDB);
            System.out.println("Connection was successful!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            Objects.requireNonNull(connection).close();
        }


    }
}
