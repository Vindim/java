package students_pk.lib;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;


public class Events {

    public static void setButtonDisable(TableView table, Button editButton, Button deleteButton) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }
}

