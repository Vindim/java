package students_pk.lib;

import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/kursovic?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "123456";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    // some vars
    private String query;
    private String fields[];

    public DB(String fields[], String sql) {
        this.fields = fields;
        this.query = sql;
    }

    private Statement connect() {
        try {
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            return stmt;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return stmt;
    }


    public ArrayList<String[]> execSelect() {
        ArrayList<String[]> result = new ArrayList<>();
        try {
            stmt = this.connect();
            rs = stmt.executeQuery(this.query);
            while (rs.next()) {
                String row[] = new String[this.fields.length];
                for (int i = 0; i < this.fields.length; i++) {
                    row[i] = rs.getString(i+1);
                }
                result.add(row);

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        this.close();
        return result;
    }

    private void close() {
        try {
            con.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            stmt.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            rs.close();
        } catch (SQLException se) { /*can't do anything */ }
    }
}