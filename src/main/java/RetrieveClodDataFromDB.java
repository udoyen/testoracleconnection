import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RetrieveClodDataFromDB {

    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = DBUtil.getConnection(DBType.MYSQLDB);

        String sql = "SELECT resume from employeeinfo WHERE emp_no = 901";

        PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            Clob resume = rs.getClob("resume");
            Reader data = resume.getCharacterStream();

            int i;
            StringBuilder resumeDetails = new StringBuilder();
            while ((i = data.read()) != -1) {
                resumeDetails.append((char) i);
            }
            System.out.println("Resume Details for Employee 901");
            System.out.println(resumeDetails);
        } else {
            System.out.println("No Record Found For Emlployee With The ID 901.");
        }

        rs.close();
        pstmt.close();
        conn.close();
    }

}
