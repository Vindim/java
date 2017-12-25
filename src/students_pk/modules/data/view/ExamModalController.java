package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Exam;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;
import java.time.LocalDate;

public class ExamModalController {
    private boolean isSaveClicked;
    private int examId;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox disciplineSelector;

    @FXML
    private ComboBox roomSelector;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    @FXML
    private void saveExamData() throws IOException {
        Object discipline = disciplineSelector.getValue();
        Object room = roomSelector.getValue();
        LocalDate localDate = datePicker.getValue();
        String time = timeField.getText();

        if (discipline == null || room == null || localDate == null || time.equals("")) {
            Stage stg = ExamModalWindow.stg;
            ErrorModal modal = new ErrorModal("Не заполнены данные экзамена");
            modal.showWindow(stg);
        }
        else {
            int disciplineId = ExamController.getIdByDisciplineName(discipline.toString());
            int roomId = ExamController.getIdByRoomNumber(room.toString());
            String date = localDate.toString() + " " + time;
            if (this.examId == 0) {
                new Exam(0, disciplineId, roomId, discipline.toString(), room.toString(), date).save();
            }
            else {
                new Exam(this.examId, disciplineId, roomId, discipline.toString(), room.toString(), date).update();
            }
            isSaveClicked = true;
            ExamModalWindow.stg.close();
        }
    }
}
