package students_pk.lib;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DateStringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Events {

    public static void setButtonDisable(TableView table, Button editButton, Button deleteButton) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }

    public static void setTimeFormatter(TextField field, Button button) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        DateStringConverter converter = new DateStringConverter(dateFormat);

        TextFormatter<Date> formatter = new TextFormatter<>(converter, null, change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() < 5) {
                    button.setDisable(true);
                }
                if (change.getControlNewText().length() == 5) {
                    try {
                        dateFormat.parse(change.getControlNewText());
                        button.setDisable(false);
                    }
                    catch (ParseException p) {
                        change.getControl().setStyle("-fx-background-color: red;");
                        button.setDisable(true);
                    }
                }
                else if (change.getControlNewText().length() < 5){
                    change.getControl().setStyle(null);
                }
            }
            if (change.isAdded()) {
                if (change.getControlNewText().length() > 5) {
                    return null;
                }
                int caretPosition = change.getCaretPosition();
                if (caretPosition == 2) {
                    change.setText(change.getText() + ":");
                    change.setCaretPosition(change.getControlNewText().length());
                    change.setAnchor(change.getControlNewText().length());
                }
            }
            return change;
        });
        field.setTextFormatter(formatter);
    }
}

