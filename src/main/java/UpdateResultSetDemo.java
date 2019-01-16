import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class UpdateResultSetDemo {

    public static void main(String[] args) {
        try(Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
            Statement stmt = Objects.requireNonNull(conn).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select dept_no, dept_name from departments")
        ) {

            rs.absolute(6);
            rs.updateString("dept_name", "Quality Management Department");
            rs.updateRow();

            System.out.println("Record Updated Successfully");

            // Add a new row of data
            rs.moveToInsertRow();
            rs.updateString("dept_no", "d010");
            rs.updateString("dept_name", "Church matters");
            rs.insertRow();

            System.out.println("Record added successfully");

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }
}
