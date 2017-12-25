package students_pk.modules.data.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;
import students_pk.modules.data.classes.Discipline;
import students_pk.modules.data.classes.Room;

import java.io.IOException;

public class DisciplineModalWindow {



    public static Stage stg;
    private Discipline discipline;

    public DisciplineModalWindow (Discipline discipline) {
        this.discipline = discipline;
    }

    public boolean showAddWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/disciplineModal.fxml"));

        Pane root = modalLoader.load();
        Scene scene = new Scene(root);

        DisciplineModalController controller = modalLoader.getController();
        controller.setDisciplineId(0);

        stg.setScene(scene);
        stg.setTitle("Добавление предмета");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    public boolean showEditWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/disciplineModal.fxml"));

        Pane root = modalLoader.load();

        TextField disciplineNameField = (TextField) root.getChildren().get(0);

        disciplineNameField.setText(this.discipline.getName());

        Scene scene = new Scene(root);

        DisciplineModalController controller = modalLoader.getController();
        controller.setDisciplineId(this.discipline.getId());

        stg.setScene(scene);
        stg.setTitle("Редактирование предмета");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }
}
