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


    public ArrayList<Object[]> execSelect() {
        ArrayList<Object[]> result = new ArrayList<>();

        try {
            stmt = this.connect();
            rs = stmt.executeQuery(this.query);

            int length = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object row[] = new Object[length];

                for (int i = 0; i < length; i++) {
                    String type = rs.getMetaData().getColumnTypeName(i + 1);
                    switch (type) {
                        case "INT":
                        case "BIGINT":
                            row[i] = rs.getInt(i +1);
                            break;

                        case "VARCHAR":
                            row[i] = rs.getString(i + 1);
                            break;
                        case "DATETIME":
                            row[i] = rs.getString(i + 1);
                            break;
                    }
                }
                result.add(row);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        this.close();
        return result;
    }

    public void execInsertOrUpdate() {
        try {
            stmt = this.connect();
            stmt.executeUpdate(this.query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        this.close();
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