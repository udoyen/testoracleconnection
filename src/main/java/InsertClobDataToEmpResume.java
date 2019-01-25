import java.io.DataOutput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Scanner;

public class InsertClobDataToEmpResume {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Connection conn = DBUtil.getConnection(DBType.MYSQLDB);
        PreparedStatement pstmt = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Employee Number : ");
        int emp_no = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the Employee First Name : ");
        String first_name = scanner.nextLine();

        System.out.println("Enter the Employee Last Name : ");
        String last_name = scanner.nextLine();

        System.out.println("Enter the Hire Date : ");
        java.sql.Date hire_date = java.sql.Date.valueOf(scanner.nextLine());

        System.out.println("Enter the Salary : ");
        double salary = Double.parseDouble(scanner.nextLine());

        String sql = "INSERT INTO employeeinfo(emp_no, first_name, last_name, hire_date, salary, resume, photo)" +
                "values(?,?,?,?,?,?,?)";
        pstmt = Objects.requireNonNull(conn).prepareStatement(sql);

        String resumeFile = "/home/georgek/Documents/resume/resume.docx";
        File file = new File(resumeFile);
        FileReader reader = new FileReader(file);

        pstmt.setInt(1, emp_no);
        pstmt.setString(2, first_name);
        pstmt.setString(3, last_name);
        pstmt.setDate(4, hire_date);
        pstmt.setDouble(5, salary);
        pstmt.setCharacterStream(6, reader, file.length());
        pstmt.setNull(7, Types.BLOB);

        pstmt.execute();

        System.out.println("Resume Inserted Successfully...");
        pstmt.close();
        conn.close();


    }
}
