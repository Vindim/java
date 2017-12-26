package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import students_pk.Main;
import students_pk.lib.Events;
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
    private FilteredList<Student> filteredStudentsData;
    private SortedList<Student> sortedStudentsData;

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
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox facultySelector;

    @FXML
    private TextField searchField;

    //перезагрузка данных в окне
    @FXML
    private void fullReload() {
        reloadFacultyFilter();
        reloadTable();
    }

    //очистка поиска
    @FXML
    private void clearSearch() {
        searchField.clear();
    }

    //добавление нового студента
    @FXML
    public void addClicked() throws IOException {
        initFaculty();
        Student fake = new Student(0, "", "", "", "", 0);
        StudentsModalWindow modal = new StudentsModalWindow(facultyData, fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    //редактирование студента
    @FXML
    public void editClicked() throws IOException {
        initFaculty();
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        StudentsModalWindow modal = new StudentsModalWindow(facultyData, selectedStudent);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    //удаление студента, с выводом алерта
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
            reloadTable();
        }
    }

    //сброс фильтра по факультетам
    @FXML
    private void clearSelectedFaculty() {
        facultySelector.getSelectionModel().clearSelection();
        this.selectedFaculty = 0;
        reloadFacultyFilter();
        reloadTable();
    }

    public void initialize() {
        //ставим обработчик на поле для поиска
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredStudentsData.setPredicate(Student -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                String lastName = Student.getLastName().toLowerCase();
                String firstName = Student.getFirstName().toLowerCase();
                String middleName = Student.getMiddleName().toLowerCase();
                String faculty = Student.getFaculty().toLowerCase();

                String fullString = lastName + " " + firstName + " " + middleName + " " + faculty;

                if (String.valueOf(lastName).contains(lowerCaseFilter) ||
                        String.valueOf(firstName).contains(lowerCaseFilter) ||
                        String.valueOf(middleName).contains(lowerCaseFilter) ||
                        String.valueOf(faculty).contains(lowerCaseFilter) ||
                        String.valueOf(fullString).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        //установка обработчика для фильтра по факультетам
        facultySelector.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.equals("Факультет")) {
                selectedFaculty = getIdByFacultyName(newSelection.toString());
            }
            reloadTable();
        });

        //по-умолчанию дизейблим кнопки редактирования и удаления
        Events.setButtonDisable(studentsTable, editButton, deleteButton);

        //связываем столбец таблицы с полем класса
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        //перезагружаем данные
        reloadFacultyFilter();
        reloadTable();
    }

    private void reloadTable() {
        //получаем данные о студентах
        initStudentData();
        //сбрасываем выделение
        studentsTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        filteredStudentsData = new FilteredList<>(studentsData, p -> true);
        sortedStudentsData = new SortedList<>(filteredStudentsData);
        sortedStudentsData.comparatorProperty().bind(studentsTable.comparatorProperty());
        studentsTable.setItems(sortedStudentsData);
    }

    //перезагрузка фильтра по факультетам
    private void reloadFacultyFilter() {
        initFaculty();
        facultySelector.setItems(facultyData);
        facultySelector.getSelectionModel().select(0);
    }

    //инициализация данных по студенту
    private void initStudentData() {
        ArrayList<Object[]> studentArray = StudentList.getList(this.selectedFaculty);
        studentsData = FXCollections.observableArrayList();
        for (int i = 0; i < studentArray.size(); i++) {
            Object row[] = studentArray.get(i);
            int id = (int) row[0];
            String lastName = (String) row[1];
            String firstName = (String) row[2];
            String middleName = (String) row[3];
            String faculty = (String) row[4];
            int facultyId = (int) row[5];
            studentsData.add(new Student(id, lastName, firstName, middleName, faculty, facultyId));
        }
    }

    //инициализация данных по факультетам
    private void initFaculty() {
        ArrayList<Object[]> facultyArray = null;
        facultyArray = FacultyList.getList();
        facultyData = FXCollections.observableArrayList();
        facultyMap = new HashMap<>();
        facultyData.add(new Faculty(0, "Факультет", 0));
        facultyMap.put("Факультет", 0);
        for (int i = 0; i < facultyArray.size(); i++) {
            Object row[] = facultyArray.get(i);
            String faculty = (String) row[1];
            int id = (int) row[0];
            facultyMap.put(faculty, id);
            facultyData.add(new Faculty(id, faculty, 0));
        }
    }

    //получение ID факультета по названию
    public static int getIdByFacultyName(String name) {
        return facultyMap.get(name);
    }
}
