package students_pk.modules.data.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;

import java.io.IOException;

public class ModalWindow {

    private ObservableList<Faculty> faculty;

    public ModalWindow(ObservableList<Faculty> faculty) {
        this.faculty = faculty;
    }

    public void showWindow(Window modal) throws IOException{
        Stage stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/studentsModal.fxml"));


        Pane root = modalLoader.load();

        //modalLoader.setController(new StudentsController());

        ComboBox facultyList = (ComboBox) root.getChildren().get(3);

        facultyList.setItems(this.faculty);

        Scene scene = new Scene(root);

        stg.setScene(scene);
        stg.setTitle("test modal");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
    }
}
