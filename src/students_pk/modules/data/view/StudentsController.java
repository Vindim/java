package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.model.FacultyList;
import students_pk.modules.data.model.StudentList;
import students_pk.modules.data.classes.Student;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentsController {

    private ObservableList<Student> studentsData;
    private ObservableList<Faculty> facultyData;
    private static Map<String, Integer> facultyMap;
    private int selectedFaculty = 0;
    private boolean isInit = false;

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> middleNameColumn;

    @FXML
    private TableColumn<Student, String> facultyColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox facultySelector;

    @FXML
    private TextField searchField;

    @FXML
    private void clearSearch() {

    }

    @FXML
    public void addClicked() throws IOException {
        initFaculty();
        Student fake = new Student(0, "", "", "", "", 0);
        StudentsModalWindow modal = new StudentsModalWindow(facultyData, fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked){
            initialize();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initFaculty();
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        StudentsModalWindow modal = new StudentsModalWindow(facultyData, selectedStudent);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked){
            initialize();
        }
    }

    @FXML
    public void deleteClicked() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Удаление студента");
        alert.setContentText("Вы уверены, что хотите удалить данного студента?");
        alert.showAndWait();
        if (!alert.getResult().getButtonData().isCancelButton()) {
            selectedStudent.delete();
            initialize();
        }
    }

    @FXML
    private void clearSelectedFaculty() {
        facultySelector.getSelectionModel().clearSelection();
        this.selectedFaculty = 0;
        initialize();
    }

    @FXML
    public void onSearch() {

    }

    public void initialize() {
        initFaculty();

        facultySelector.setItems(facultyData);
        if (!isInit) {
            facultySelector.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> {
                if (newSelection != null && !newSelection.equals("Факультет")) {
                    this.selectedFaculty = getIdByFacultyName(newSelection.toString());
                }
                initStudentData();
                studentsTable.setItems(studentsData);
            });
        }
        facultySelector.getSelectionModel().select(0);
        initStudentData();
        studentsTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        if (!isInit) {
            studentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> {
                if (newSelection != null) {
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            });
        }
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        studentsTable.setItems(studentsData);
        isInit = true;
    }

    private void initStudentData() {
        ArrayList<Object[]> studentArray = StudentList.getList(this.selectedFaculty);
        studentsData = FXCollections.observableArrayList();

        for (int i = 0; i < studentArray.size(); i++) {
            Object row[] = studentArray.get(i);

            int id = (int) row[0];
            String lastName = (String) row[1];
            String firstName = (String) row[2];
            String middleName = (String ) row[3];
            String faculty = (String) row[4];
            int facultyId = (int) row [5];

            studentsData.add(new Student(id, lastName, firstName, middleName, faculty, facultyId));
        }
    }

    private void initFaculty() {
        ArrayList<Object[]> facultyArray = null;
        facultyArray = FacultyList.getList();
        facultyData = FXCollections.observableArrayList();
        facultyMap = new HashMap<>();
        facultyData.add(new Faculty(0,"Факультет"));
        facultyMap.put("Факультет", 0);

        for (int i = 0; i< facultyArray.size(); i++) {
            Object row[] = facultyArray.get(i);
            String faculty = (String) row[1];
            int id = (int) row[0];
            facultyMap.put(faculty, id);
            facultyData.add(new Faculty(id, faculty));
        }
    }



    public static int getIdByFacultyName(String name) {
        return facultyMap.get(name);
    }
}
