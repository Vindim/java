package students_pk.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import students_pk.models.Students;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Controller {

    private ObservableList<Students> studentsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Students> studentsTable;

    @FXML
    private TableColumn<Students, String> lastNameColumn;

    @FXML
    private TableColumn<Students, String> firstNameColumn;

    @FXML
    private TableColumn<Students, String> middleNameColumn;

    @FXML
    private TableColumn<Students, String> facultyColumn;

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
        ArrayList<String[]> studentArray = Students.getTable();
        //studentsData

        for (int i = 0; i < studentArray.size(); i ++) {
            String row[] = studentArray.get(i);


            //String id = row[0];
            String lastName = row[1];
            String firstName = row[2];
            String middleName = row[3];
            String faculty = row[4];

            studentsData.add(new Students(lastName, firstName, middleName, faculty));
            //System.out.printf("id: %s, name: %s %s %s, faculty: %s\n", id, lastName, firstName, middleName, faculty);

        }
    }
}
