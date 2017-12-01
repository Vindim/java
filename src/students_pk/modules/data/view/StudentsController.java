package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.model.FacultyList;
import students_pk.modules.data.model.StudentList;
import students_pk.modules.data.classes.Student;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class StudentsController {

    private ObservableList<Student> studentsData = FXCollections.observableArrayList();
    private ObservableList<Faculty> facultyData = FXCollections.observableArrayList();

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
    private Button saveButton;

    @FXML
    public void showModal() throws IOException {
        initFaculty();
        //Field fields = Main.class.getDeclaredField("primaryStage");
        Stage primaryStage = Main.primaryStage;
        System.out.print(facultyData);

        ModalWindow modal = new ModalWindow(facultyData);
        modal.showWindow(primaryStage);
    }

    @FXML
    public void saveStudentData() {
        System.out.print("LOOOOL");
    }

    public void initialize() {
        initData();
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        System.out.print(studentsData);

        studentsTable.setItems(studentsData);

    }

    private void initData() {
        ArrayList<String[]> studentArray = StudentList.getTable();
        //studentsData

        for (int i = 0; i < studentArray.size(); i++) {
            String row[] = studentArray.get(i);
            //String id = row[0];
            String lastName = row[1];
            String firstName = row[2];
            String middleName = row[3];
            String faculty = row[4];

            studentsData.add(new Student(lastName, firstName, middleName, faculty));
        }
    }

    private void initFaculty() {
        ArrayList<String[]> facultyArray = FacultyList.getList();

        for (int i = 0; i< facultyArray.size(); i++) {
            String row[] = facultyArray.get(i);
            String faculty = row[1];

            System.out.printf("%s \n", faculty);
            facultyData.add(new Faculty(faculty));
        }
    }
}
