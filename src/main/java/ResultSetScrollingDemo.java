import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ResultSetScrollingDemo {
    public static void main(String[] args) {
        try(Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
            Statement stmt = Objects.requireNonNull(conn).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from employees where emp_no <= 10010")
        ) {

            String format = "No.:%d, Sex:%s %-15s %-15S Hire Date: %tB %te, %tY%n";
            rs.beforeFirst();
            System.out.println("First 10 rows : ");
            while (rs.next()) {
                System.out.format(format, rs.getInt("emp_no"),
                        rs.getString("gender"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                        rs.getDate("hire_date"));

            }

            rs.afterLast();
            System.out.println("Last ten rows : ");
            while (rs.previous()) {
                System.out.format(format, rs.getInt("emp_no"),
                        rs.getString("gender"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                        rs.getDate("hire_date"));

            }

            rs.first();
            System.out.println("First record : ");
            System.out.format(format, rs.getInt("emp_no"),
                    rs.getString("gender"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                    rs.getDate("hire_date"));

            rs.last();
            System.out.println("Last record : ");
            System.out.format(format, rs.getInt("emp_no"),
                    rs.getString("gender"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                    rs.getDate("hire_date"));

            rs.absolute(4);
            System.out.println("Record at 4th Row : ");
            System.out.format(format, rs.getInt("emp_no"),
                    rs.getString("gender"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                    rs.getDate("hire_date"));

            rs.relative(2);
            System.out.println("Record at 6th Row : ");
            System.out.format(format, rs.getInt("emp_no"),
                    rs.getString("gender"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                    rs.getDate("hire_date"));

            rs.relative(-4);
            System.out.println("Record at 2nd Row : ");
            System.out.format(format, rs.getInt("emp_no"),
                    rs.getString("gender"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getDate("hire_date"), rs.getDate("hire_date"),
                    rs.getDate("hire_date"));

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
