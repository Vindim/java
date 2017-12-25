package students_pk.modules.main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import students_pk.Main;

import java.io.IOException;

public class ErrorModal {

    public static Stage stg;
    private String text;

    public ErrorModal(String text) {
        this.text = text;
    }

    public void showWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader errorLoader = new FXMLLoader();
        errorLoader.setLocation(Main.class.getResource("modules/main/template/Error.fxml"));

        Pane root = errorLoader.load();

        Label message = (Label) root.getChildren().get(1);
        message.setText(text);
        Scene scene = new Scene(root);

        stg.setScene(scene);
        stg.setTitle("Ошибка");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
    }
}
