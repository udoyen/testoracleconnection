import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RetrieveImageFromDB {
    public static void main(String[] args) throws IOException, SQLException {
        Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
        String sql = "SELECT photo FROM employeeinfo WHERE emp_no = 901";
        PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            Blob imgBlob = rs.getBlob("photo");

            FileOutputStream fos = new FileOutputStream("/home/georgek/Pictures/java/162.jpg");

            fos.write(imgBlob.getBytes(1, (int)imgBlob.length()));

            fos.flush();
            fos.close();

            System.out.println("Photo of Employee 901 has been Downloaded successfully");
        } else {
            System.out.println("Employee Record Not Found.");
        }

        rs.close();
        pstmt.close();
        conn.close();
    }
}
