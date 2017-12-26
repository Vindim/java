package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Discipline;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.model.DisciplineList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacultyModalWindow {

    public static Stage stg;
    private Faculty faculty;
    private ObservableList<CheckBox> checkBoxes;
    private Map<String, Integer> disciplineMap;

    public FacultyModalWindow (Faculty faculty) {
        initCheckBoxes();
        this.faculty = faculty;
    }

    public boolean showAddWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/facultyModal.fxml"));

        Pane root = modalLoader.load();
        Scene scene = new Scene(root);

        VBox box = (VBox) root.getChildren().get(0);
        box.getChildren().addAll(checkBoxes);

        FacultyModalController controller = modalLoader.getController();
        controller.setFacultyId(0);

        stg.setScene(scene);
        stg.setTitle("Добавление факультета");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    public boolean showEditWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/facultyModal.fxml"));

        Pane root = modalLoader.load();

        VBox box = (VBox) root.getChildren().get(0);

        TextField facultyNameField = (TextField) box.getChildren().get(0);

        facultyNameField.setText(this.faculty.getName());

        Scene scene = new Scene(root);

        int fullMask = faculty.getFullMask();

        for (int i = 0; i < checkBoxes.size(); i++) {
            int mask = disciplineMap.get(checkBoxes.get(i).getId());
            if ((fullMask & mask) != 0) {
                checkBoxes.get(i).setSelected(true);
            }
        }

        box.getChildren().addAll(checkBoxes);

        FacultyModalController controller = modalLoader.getController();
        controller.setFacultyId(this.faculty.getId());

        stg.setScene(scene);
        stg.setTitle("Редактирование факультета");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    private void initCheckBoxes() {
        ArrayList<Object[]> disciplineArray = DisciplineList.getList();
        checkBoxes = FXCollections.observableArrayList();
        disciplineMap = new HashMap<>();
        for (int i = 0; i < disciplineArray.size(); i++) {
            Object row[] = disciplineArray.get(i);
            String disciplineName = (String) row[1];
            Integer mask = (Integer) row[2];
            CheckBox discipline = new CheckBox();
            discipline.setId(disciplineName.replace(" ", "_"));
            discipline.setText(disciplineName);
            discipline.setPrefHeight(30.0);
            discipline.setPrefWidth(269.0);
            checkBoxes.add(discipline);
            disciplineMap.put(disciplineName.replace(" ", "_"), mask);
        }
    }
}
