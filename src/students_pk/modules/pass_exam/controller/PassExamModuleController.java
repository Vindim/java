package students_pk.modules.pass_exam.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import students_pk.Main;

import java.io.IOException;
import java.lang.reflect.Method;

public class PassExamModuleController {
    private Tab tab;
    //private Pane passExamWindow;

    public PassExamModuleController() {
        this.tab = new Tab();
        //this.passExamWindow = new Pane();
        //tab.setText("Сдача экзаменов");
    }

    public Tab init() {
        try {
            FXMLLoader passExamLoader = new FXMLLoader();
            passExamLoader.setLocation(Main.class.getResource("modules/pass_exam/template/passExam.fxml"));

            tab = passExamLoader.load();
            tab.setText("Сдача экзаменов");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
}
