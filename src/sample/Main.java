package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DB;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Array result = new DB({"id", "first_name"}, "sql query");

        ResultSet rs = DB.exec("SELECT s.ID, s.LAST_NAME, s.FIRST_NAME, s.MIDDLE_NAME, f.NAME AS FACULT_NAME  FROM student s JOIN faculty f ON f.ID = s.FACULTY_ID");

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String lastName = rs.getString(2);
                String firstName = rs.getString(3);
                String middleName = rs.getString(4);
                String faculty = rs.getString(5);
                System.out.printf("id: %d, name: %s %s %s, faculty: %s\n", id, lastName, firstName, middleName, faculty);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        DB.close();
        launch(args);
    }
}