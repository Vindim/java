package students_pk.modules.data.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;

import java.io.IOException;

public class FacultyModalWindow {

    public static Stage stg;
    private Faculty faculty;

    public FacultyModalWindow (Faculty faculty) {
        this.faculty = faculty;
    }

    public boolean showAddWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/facultyModal.fxml"));

        Pane root = modalLoader.load();
        Scene scene = new Scene(root);

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

        TextField facultyNameField = (TextField) root.getChildren().get(0);

        facultyNameField.setText(this.faculty.getName());

        Scene scene = new Scene(root);

        FacultyModalController controller = modalLoader.getController();
        controller.setFacultyId(this.faculty.getId());

        stg.setScene(scene);
        stg.setTitle("test modal");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }


}
