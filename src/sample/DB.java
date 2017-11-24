package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 * @author Javin Paul
 */
public class DB {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/kursovic?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "123456";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static ResultSet exec(String sql) {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            rs = stmt.executeQuery(sql);


            return rs;
            /*while (rs.next()) {
                int id = rs.getInt(1);
                String lastName = rs.getString(2);
                String firstName = rs.getString(3);
                String middleName = rs.getString(4);
                String faculty = rs.getString(5);
                System.out.printf("id: %d, name: %s %s %s, faculty: %s\n", id, lastName, firstName, middleName, faculty);
            }*/

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return rs;
    }

    public static void close()
    {
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
    }


}