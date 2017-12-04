package students_pk.modules.data.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import students_pk.Main;
import students_pk.modules.data.view.StudentsController;

import java.io.IOException;

public class DataModuleController {

    public Tab tab;
    public TabPane dataWindow;

    public DataModuleController() {
        this.tab = new Tab();
        this.dataWindow = new TabPane();
        tab.setText("Данные");
    }

    public Tab init() {
        try {
            FXMLLoader studentsLoader = new FXMLLoader();
            studentsLoader.setLocation(Main.class.getResource("modules/data/template/students.fxml"));
            StudentsController studentController = studentsLoader.getController();


            FXMLLoader facultyLoader = new FXMLLoader();
            facultyLoader.setLocation(Main.class.getResource("modules/data/template/faculty.fxml"));

            Tab faculty = facultyLoader.load();
            Tab students = studentsLoader.load();



            dataWindow.getTabs().addAll(students, faculty);
            tab.setContent(dataWindow);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
}
