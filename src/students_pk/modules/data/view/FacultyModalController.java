package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.model.DisciplineList;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacultyModalController {

    private boolean isSaveClicked;
    private int facultyId;

    private Map<String, Integer> disciplineMap;
    private String[] disciplines;

    @FXML
    private Button saveButton;

    @FXML
    private TextField facultyNameInput;

    @FXML
    private VBox boxWithCheckboxes;

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    public void setFacultyId(int id) {
        this.facultyId = id;
    }

    @FXML
    private void saveFacultyData() throws IOException {
        String facultyName = facultyNameInput.getText();
        Integer fullMask = 0;
        for (int i = 0; i < disciplines.length; i++) {
            CheckBox discipline = (CheckBox) boxWithCheckboxes.lookup("#" + disciplines[i]);
            if (discipline.isSelected()) {
                fullMask = fullMask + disciplineMap.get(disciplines[i]);
            }
        }


        if (facultyName.equals("")) {
            Stage stg = FacultyModalWindow.stg;
            ErrorModal modal = new ErrorModal("Введите название факультета");
            modal.showWindow(stg);
        }
        else {
            if (this.facultyId == 0) {
                new Faculty(0, facultyName, fullMask).save();
            }
            else {
                new Faculty(this.facultyId, facultyName, fullMask).update();
            }
            isSaveClicked = true;
            FacultyModalWindow.stg.close();
        }
    }

    public void initialize() {
        initDisciplineData();
    }

    private void initDisciplineData() {
        ArrayList<Object[]> disciplineArray = DisciplineList.getList();
        disciplineMap = new HashMap<>();
        disciplines = new String[disciplineArray.size()];
        for (int i = 0; i < disciplineArray.size(); i++) {
            Object row[] = disciplineArray.get(i);
            String disciplineName = (String) row[1];
            Integer mask = (Integer) row[2];
            disciplines[i] = disciplineName.replace(" ", "_");
            disciplineMap.put(disciplineName.replace(" ", "_"), mask);
        }
    }
}
