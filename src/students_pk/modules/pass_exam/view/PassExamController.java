package students_pk.modules.pass_exam.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import students_pk.lib.Events;
import students_pk.modules.pass_exam.Classes.StudentWithMark;
import students_pk.modules.pass_exam.Model.StudentWithMarkList;

import java.util.ArrayList;

public class PassExamController {

    private ObservableList<StudentWithMark> studentsWithMarkData;
    private FilteredList<StudentWithMark> filteredStudentsWithMarkData;
    private SortedList<StudentWithMark> sortedStudentsWithMarkData;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField facultyField;

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
    private void fullReload() {
        reloadTable();
    }

    @FXML
    private void clearStudentCard() {
        studentsWithMarkTable.getSelectionModel().clearSelection();
        lastNameField.clear();
        middleNameField.clear();
        facultyField.clear();
        firstNameField.clear();
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    @FXML
    private void selectStudent() {
        StudentWithMark student = studentsWithMarkTable.getSelectionModel().getSelectedItem();
        lastNameField.setText(student.getLastName());
        firstNameField.setText(student.getFirstName());
        middleNameField.setText(student.getMiddleName());
        facultyField.setText(student.getFaculty());
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
                String mark = StudentWithMark.getMark() != null ? StudentWithMark.getMark().toString() : "";

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

        studentsWithMarkTable.getSelectionModel().clearSelection();
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        filteredStudentsWithMarkData = new FilteredList<>(studentsWithMarkData, p -> true);
        sortedStudentsWithMarkData = new SortedList<>(filteredStudentsWithMarkData);
        sortedStudentsWithMarkData.comparatorProperty().bind(studentsWithMarkTable.comparatorProperty());
        studentsWithMarkTable.setItems(sortedStudentsWithMarkData);
    }

    private void initStudentWithMarkData() {
        ArrayList<Object[]> studentWithMarkArray = StudentWithMarkList.getList();
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
