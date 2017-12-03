package students_pk.modules.data.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.classes.Student;

import javax.lang.model.type.NullType;
import java.io.IOException;

public class ModalWindow {

    private ObservableList<Faculty> faculty;
    public static Stage stg;
    private Student student;

    public ModalWindow(ObservableList<Faculty> faculty, Student student) {
        this.faculty = faculty;
        this.student = student;
    }

    public boolean showAddWindow(Window modal) throws IOException{
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/studentsModal.fxml"));

        Pane root = modalLoader.load();

        ComboBox facultyList = (ComboBox) root.getChildren().get(3);

        facultyList.setItems(this.faculty);

        Scene scene = new Scene(root);

        ModalWindowController controller = modalLoader.getController();
        controller.setStudentId(0);

        stg.setScene(scene);
        stg.setTitle("test modal");
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

        TextField lastNameField = (TextField) root.getChildren().get(0);
        TextField firstNameField = (TextField) root.getChildren().get(1);
        TextField middleNameField = (TextField) root.getChildren().get(2);
        ComboBox facultyList = (ComboBox) root.getChildren().get(3);

        lastNameField.setText(this.student.getLastName());
        firstNameField.setText(this.student.getFirstName());
        middleNameField.setText(this.student.getMiddleName());

        facultyList.setItems(this.faculty);
        facultyList.getSelectionModel().select(this.student.getFaculty());

        Scene scene = new Scene(root);

        ModalWindowController controller = modalLoader.getController();
        controller.setStudentId(this.student.getId());

        stg.setScene(scene);
        stg.setTitle("test modal");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }
}
