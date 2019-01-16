import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PreparedStatementRetrieveDemo {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQLDB);
            String sql = "select * from employees where birth_date < ? and gender = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            pstmt.setDate(1, DBUtil.setMyDate("1960-1-1"));
            pstmt.setString(2, "F");

            rs = pstmt.executeQuery();

            String format = "%-15d %-15s %-15s %-15s %-15s %s%n";
            while (rs.next()) {
                System.out.format(format, rs.getInt("emp_no"),
                        rs.getDate("birth_date").toString(), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("gender"),
                        rs.getDate("hire_date").toString());

            }

            rs.last();
            System.out.println("Total Employee : " + rs.getRow());

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        } catch (ParseException e) {
            DBUtil.showErrorMessage(e);
        }
    }


}
