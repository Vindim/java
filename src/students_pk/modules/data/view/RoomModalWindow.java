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
import students_pk.modules.data.classes.Room;

import java.io.IOException;

public class RoomModalWindow {


    public static Stage stg;
    private Room room;

    public RoomModalWindow (Room room) {
        this.room = room;
    }

    public boolean showAddWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/roomModal.fxml"));

        Pane root = modalLoader.load();
        Scene scene = new Scene(root);

        RoomModalController controller = modalLoader.getController();
        controller.setRoomId(0);

        stg.setScene(scene);
        stg.setTitle("Добавление аудитории");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    public boolean showEditWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/roomModal.fxml"));

        Pane root = modalLoader.load();

        TextField roomNumberField = (TextField) root.getChildren().get(0);

        roomNumberField.setText(this.room.getNumber());

        Scene scene = new Scene(root);

        RoomModalController controller = modalLoader.getController();
        controller.setRoomId(this.room.getId());

        stg.setScene(scene);
        stg.setTitle("Редактирование аудитории");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

}
