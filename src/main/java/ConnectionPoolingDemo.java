import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPoolingDemo {

    public static void main(String[] args) throws SQLException {
        // Data Source
        PoolDataSource ds = PoolDataSourceFactory.getPoolDataSource();
        // Set values
        ds.setConnectionFactoryClassName("com.mysql.cj.jdbc.MysqlDataSource");
        ds.setConnectionPoolName("PoolDemo");
        ds.setURL(DBUtil.mySQLCS);
        ds.setServerName("localhost");
        ds.setPortNumber(3306);
        ds.setUser("root");
        ds.setPassword("admin");
        ds.setInitialPoolSize(5);

        // Create Pool connection object
        Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM departments");

        String format = "%-30s%-50s\n";
        System.out.format(format, "Department #", "Department Name");
        System.out.format(format, "-------------", "----------------");

        while (rs.next()) {
            System.out.format(format, rs.getString("dept_no"), rs.getString("dept_name"));
        }

        rs.close();
        stmt.close();
        conn.close();

    }
}
