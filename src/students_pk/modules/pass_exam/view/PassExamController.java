package students_pk.modules.pass_exam.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import students_pk.Main;
import students_pk.lib.Events;
import students_pk.modules.data.classes.Exam;
import students_pk.modules.data.model.ExamList;
import students_pk.modules.pass_exam.classes.StudentWithMark;
import students_pk.modules.pass_exam.model.StudentWithMarkList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassExamController {

    private ObservableList<StudentWithMark> studentsWithMarkData;
    private ObservableList<Exam> examData;
    private Map<String, Integer> examMap;
    private FilteredList<StudentWithMark> filteredStudentsWithMarkData;
    private SortedList<StudentWithMark> sortedStudentsWithMarkData;
    private int selectedExam;

    @FXML
    private ComboBox examSelector;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField facultyField;

    @FXML
    private TextField markField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<StudentWithMark> studentsWithMarkTable;

    @FXML
    private TableColumn<StudentWithMark, String> lastNameColumn;

    @FXML
    private TableColumn<StudentWithMark, String> firstNameColumn;

    @FXML
    private TableColumn<StudentWithMark, String> middleNameColumn;

    @FXML
    private TableColumn<StudentWithMark, String> facultyColumn;

    @FXML
    private TableColumn<StudentWithMark, Integer> markColumn;

    @FXML
    private TextField searchField;

    @FXML
    private void clearSearch() {
        searchField.clear();
    }

    @FXML
    private void removeMark() {
        StudentWithMark student = studentsWithMarkTable.getSelectionModel().getSelectedItem();
        student.deleteMark();
        reloadTable();
    }

    @FXML
    private void fullReload() {
        reloadExamFilter();
        reloadTable();
    }

    @FXML
    private void clearExamFilter() {
        examSelector.getSelectionModel().clearSelection();
        this.selectedExam = 0;
        reloadExamFilter();
        reloadTable();
    }

    @FXML
    private void clearStudentCard() {
        studentsWithMarkTable.getSelectionModel().clearSelection();
        lastNameField.clear();
        middleNameField.clear();
        facultyField.clear();
        firstNameField.clear();
        markField.clear();
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    @FXML
    private void saveExamResult() {
        StudentWithMark student = studentsWithMarkTable.getSelectionModel().getSelectedItem();
        String oldMark = student.getMark();
        String mark = markField.getText();
        Integer markInt;
        int errorType = 0;
        if (mark.equals("")) {
            errorType = 1;
        }
        else {
            try {
                markInt = Integer.parseInt(mark);
                if (markInt < 1 || markInt > 100) {
                    errorType = 3;
                }
                else {
                    student.setExamId(selectedExam);
                    student.setMark(markInt);
                    if (oldMark.equals("")) {
                        student.saveMark();
                    }
                    else student.updateMark();
                    reloadTable();
                }
            }
            catch (NumberFormatException e) {
                errorType = 2;
            }
        }
        if (errorType != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Main.primaryStage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Сохранение оценки");
            switch (errorType) {
                case 1:
                    alert.setContentText("Вы не ввели оценку");
                    break;
                case 2:
                case 3:
                    alert.setContentText("Оценка должна быть числом от 1 до 100");
                    break;
            }
            alert.showAndWait();
        }
    }

    @FXML
    private void selectStudent() {
        StudentWithMark student = studentsWithMarkTable.getSelectionModel().getSelectedItem();
        if (student != null) {
            lastNameField.setText(student.getLastName());
            firstNameField.setText(student.getFirstName());
            middleNameField.setText(student.getMiddleName());
            facultyField.setText(student.getFaculty());
            markField.setText(student.getMark());
        }
    }

    public void initialize() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredStudentsWithMarkData.setPredicate(StudentWithMark -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();

                String lastName = StudentWithMark.getLastName().toLowerCase();
                String firstName = StudentWithMark.getFirstName().toLowerCase();
                String middleName = StudentWithMark.getMiddleName().toLowerCase();
                String faculty = StudentWithMark.getFaculty().toLowerCase();
                String mark = StudentWithMark.getMark();

                String fullString = lastName + " " + firstName + " " + middleName + " " + faculty;

                if (String.valueOf(lastName).contains(lowerCaseFilter) ||
                        String.valueOf(firstName).contains(lowerCaseFilter) ||
                        String.valueOf(middleName).contains(lowerCaseFilter) ||
                        String.valueOf(faculty).contains(lowerCaseFilter) ||
                        String.valueOf(fullString).contains(lowerCaseFilter) ||
                        String.valueOf(mark).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        examSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.equals("Выберите предмет")) {
                selectedExam = getIdByExamName(newSelection.toString());
            }
            reloadTable();
        });

        Events.setButtonDisable(studentsWithMarkTable, saveButton, cancelButton);

        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));

        reloadTable();

    }

    private void reloadTable() {
        initStudentWithMarkData();
        clearStudentCard();
        studentsWithMarkTable.getSelectionModel().clearSelection();

        filteredStudentsWithMarkData = new FilteredList<>(studentsWithMarkData, p -> true);
        sortedStudentsWithMarkData = new SortedList<>(filteredStudentsWithMarkData);
        sortedStudentsWithMarkData.comparatorProperty().bind(studentsWithMarkTable.comparatorProperty());
        studentsWithMarkTable.setItems(sortedStudentsWithMarkData);
    }

    private void initExamData() {
        ArrayList<Object[]> examArray = null;
        examArray = ExamList.getList();
        examData = FXCollections.observableArrayList();
        examMap = new HashMap<>();
        examData.add(new Exam(0, 0, 0, "Выберите предмет", "", ""));
        examMap.put("Выберите предмет", 0);

        for (int i = 0; i < examArray.size(); i++) {
            Object row[] = examArray.get(i);
            int id = (int) row[0];
            int disciplineId = (int) row[1];
            int roomId = (int) row[2];
            String exam = (String) row[3];
            String room = (String) row[4];
            String date = (String) row[5];
            examMap.put(exam, id);
            examData.add(new Exam(id, disciplineId, roomId, exam, room, date));
        }
    }

    private void reloadExamFilter() {
        initExamData();
        examSelector.setItems(examData);
        examSelector.getSelectionModel().select(0);
    }

    public int getIdByExamName(String name) {
        return examMap.get(name);
    }

    private void initStudentWithMarkData() {
        ArrayList<Object[]> studentWithMarkArray = StudentWithMarkList.getList(selectedExam);
        studentsWithMarkData = FXCollections.observableArrayList();

        for (int i = 0; i < studentWithMarkArray.size(); i++) {
            Object row[] = studentWithMarkArray.get(i);

            int id = (int) row[0];
            String lastName = (String) row[1];
            String firstName = (String) row[2];
            String middleName = (String) row[3];
            String faculty = (String) row[4];
            int facultyId = (int) row[5];
            Integer examId = (Integer) row[6];
            Integer mark = (Integer) row[7];
            studentsWithMarkData.add(new StudentWithMark(id, lastName, firstName, middleName, faculty, facultyId, examId, mark));
        }
    }

}
