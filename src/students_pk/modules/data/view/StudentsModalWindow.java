package students_pk.modules.data.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.classes.Student;

import java.io.IOException;

public class StudentsModalWindow {
    private ObservableList<Faculty> faculty;
    public static Stage stg;
    private Student student;

    public StudentsModalWindow(ObservableList<Faculty> faculty, Student student) {
        this.faculty = faculty;
        this.student = student;
    }

    public boolean showAddWindow(Window modal) throws IOException{
        //создаем новый стейдж и загружаем файл с шаблоном
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/studentsModal.fxml"));
        Pane root = modalLoader.load();
        ComboBox facultyList = (ComboBox) root.getChildren().get(3);
        facultyList.setItems(this.faculty);

        Scene scene = new Scene(root);
        StudentModalController controller = modalLoader.getController();
        controller.setStudentId(0);

        stg.setScene(scene);
        stg.setTitle("Добавление студента");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    public boolean showEditWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/studentsModal.fxml"));

        Pane root = modalLoader.load();
        //получаем необходимые поля
        TextField lastNameField = (TextField) root.getChildren().get(0);
        TextField firstNameField = (TextField) root.getChildren().get(1);
        TextField middleNameField = (TextField) root.getChildren().get(2);
        ComboBox facultyList = (ComboBox) root.getChildren().get(3);

        //заполняем их значения
        lastNameField.setText(this.student.getLastName());
        firstNameField.setText(this.student.getFirstName());
        middleNameField.setText(this.student.getMiddleName());

        facultyList.setItems(this.faculty);
        facultyList.getSelectionModel().select(this.student.getFaculty());

        Scene scene = new Scene(root);

        StudentModalController controller = modalLoader.getController();
        controller.setStudentId(this.student.getId());

        stg.setScene(scene);
        stg.setTitle("Редактирование студента");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }
}
