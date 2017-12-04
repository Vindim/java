package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;

public class FacultyModalController {

    private boolean isSaveClicked;
    private int facultyId;

    @FXML
    private Button saveButton;

    @FXML
    private TextField facultyNameInput;

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    public void setFacultyId(int id) {
        this.facultyId = id;
    }



    @FXML
    private void saveFacultyData() throws IOException {
        String facultyName = facultyNameInput.getText();

        if (facultyName.equals("")) {
            Stage stg = FacultyModalWindow.stg;
            ErrorModal modal = new ErrorModal("Введите название факультета");
            modal.showWindow(stg);
        }
        else {
            if (this.facultyId == 0) {
                new Faculty(0, facultyName).save();
            }
            else {
                new Faculty(this.facultyId, facultyName).update();
            }
            isSaveClicked = true;
            FacultyModalWindow.stg.close();
        }
    }


}
