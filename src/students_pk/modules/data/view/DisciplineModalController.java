package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Discipline;
import students_pk.modules.data.classes.Room;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;

public class DisciplineModalController {



    private boolean isSaveClicked;
    private int disciplineId;

    @FXML
    private Button saveButton;

    @FXML
    private TextField disciplineNameInput;

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    public void setDisciplineId(int id) {
        this.disciplineId = id;
    }



    @FXML
    private void saveDisciplineData() throws IOException {
        String disciplineName = disciplineNameInput.getText();

        if (disciplineName.equals("")) {
            Stage stg = DisciplineModalWindow.stg;
            ErrorModal modal = new ErrorModal("Введите название предмета");
            modal.showWindow(stg);
        }
        else {
            if (this.disciplineId == 0) {
                new Discipline(0, disciplineName).save();
            }
            else {
                new Discipline(this.disciplineId, disciplineName).update();
            }
            isSaveClicked = true;
            DisciplineModalWindow.stg.close();
        }
    }
}
