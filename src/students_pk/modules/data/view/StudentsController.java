package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import students_pk.modules.data.model.StudentList;
import students_pk.modules.data.model.Student;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class StudentsController {

    private ObservableList<Student> studentsData = FXCollections.observableArrayList();

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
    public void onClickMethod() {
        addButton.setText("YOOOOO");
    }

    public void initialize() {
        initData();
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

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
}
