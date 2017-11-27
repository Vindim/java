package students_pk.lib;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DB {
    private static final String PathToProperties = "src/students_pk/config/config.properties";

    // JDBC URL, username and password of MySQL server
    private String url;
    private String user;
    private String password;

    // JDBC variables for opening and managing connection
    private static Statement stmt;
    private static Connection con;
    private static ResultSet rs;

    private String query;

    public DB(String sql) {
        Properties prop = new Properties();
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(PathToProperties);
            prop.load(fileInputStream);

            this.url = "jdbc:mysql://" +
                    prop.getProperty("host") + ":" +
                    prop.getProperty("port") + "/" +
                    prop.getProperty("databaseName") + "?" +
                    prop.getProperty("connectionParams");
            this.user = prop.getProperty("user");
            this.password = prop.getProperty("password");
        }
        catch (IOException e) {
            System.out.println("Ошибка: файл " + PathToProperties + " не найден");
            e.printStackTrace();
        }
        this.query = sql;
    }

    private Statement connect() {
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            return stmt;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return stmt;
    }


    public ArrayList<String[]> execSelect() {
        ArrayList<String[]> result = new ArrayList<>();

        String fieldString = this.query.replaceFirst("SELECT", "");
        int end = fieldString.lastIndexOf("FROM");
        fieldString = fieldString.substring(0, end);
        String[] fields = fieldString.split(", ");
        //System.out.print(fields.length);

        //String[] fields = this.query.split(", ");
        try {
            stmt = this.connect();
            rs = stmt.executeQuery(this.query);
            while (rs.next()) {
                String row[] = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
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
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}