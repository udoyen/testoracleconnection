import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class InsertImageWithDB {
    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = DBUtil.getConnection(DBType.MYSQLDB);

        String sql = "UPDATE employeeinfo SET photo = ? WHERE emp_no = 901";

        PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
        File file = new File("/home/georgek/Pictures/162.jpg");

        FileInputStream fis = new FileInputStream(file);

        pstmt.setBinaryStream(1, fis, fis.available());

        int count = pstmt.executeUpdate();

        System.out.println("Total Records Updated : " + count);
        pstmt.close();
        conn.close();
    }
}
