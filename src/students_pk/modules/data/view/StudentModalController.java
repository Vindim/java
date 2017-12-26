package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Student;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;

public class StudentModalController {
    private boolean isSaveClicked = false;
    private int studentId;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField middleNameInput;

    @FXML
    private ComboBox facultySelector;

    //возвращаем, была-ли нажата кнопка сохранения
    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    //устанавливаем id студента
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @FXML
    private void saveStudentData() throws IOException{
        String lastName = lastNameInput.getText();
        String firstName = firstNameInput.getText();
        String middleName = middleNameInput.getText();
        Object faculty =  facultySelector.getValue();

        //обработка пустых значений
        if (lastName.equals("") || firstName.equals("") || faculty == null) {
            Stage stg = StudentsModalWindow.stg;
            ErrorModal modal = new ErrorModal("Не заполнены данные студента");
            modal.showWindow(stg);
        }
        else {
            int facultyId = StudentsController.getIdByFacultyName(faculty.toString());
            //сохранение нового
            if (this.studentId == 0) {
                new Student(0, lastName, firstName, middleName, faculty.toString(), facultyId).save();
            }
            //редактирование существующего
            else {
                new Student(this.studentId, lastName, firstName, middleName, faculty.toString(), facultyId).update();
            }
            isSaveClicked = true;
            StudentsModalWindow.stg.close();
        }
    }
}