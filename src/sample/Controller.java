package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private Button addButton;

    @FXML
    public void onClickMethod() {
        addButton.setText("YOOOOO");
    }
}
