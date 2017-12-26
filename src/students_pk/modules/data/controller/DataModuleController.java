package students_pk.modules.data.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import students_pk.Main;

import java.io.IOException;

public class DataModuleController {

    private Tab tab;
    private TabPane dataWindow;

    public DataModuleController() {
        this.tab = new Tab();
        this.dataWindow = new TabPane();
        tab.setText("Данные");
    }

    public Tab init() {
        try {
            //Загружаем все вкладки для "Данных"
            FXMLLoader studentsLoader = new FXMLLoader();
            studentsLoader.setLocation(Main.class.getResource("modules/data/template/students.fxml"));

            FXMLLoader facultyLoader = new FXMLLoader();
            facultyLoader.setLocation(Main.class.getResource("modules/data/template/faculty.fxml"));

            FXMLLoader roomLoader = new FXMLLoader();
            roomLoader.setLocation(Main.class.getResource("modules/data/template/room.fxml"));

            FXMLLoader disciplineLoader = new FXMLLoader();
            disciplineLoader.setLocation(Main.class.getResource("modules/data/template/discipline.fxml"));

            FXMLLoader examLoader = new FXMLLoader();
            examLoader.setLocation(Main.class.getResource("modules/data/template/exam.fxml"));

            Tab faculty = facultyLoader.load();
            Tab students = studentsLoader.load();
            Tab room = roomLoader.load();
            Tab discipline = disciplineLoader.load();
            Tab exam = examLoader.load();

            dataWindow.getTabs().addAll(students, faculty, room, discipline, exam);
            tab.setContent(dataWindow);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
}
