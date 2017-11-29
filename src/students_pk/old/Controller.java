package students_pk.old;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button addButton;

    @FXML
    public void onClickMethod() {
        addButton.setText("YOOOOO");
    }

    public void initialize() {
    }

    private void initData() {

    }
}
