package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import students_pk.Main;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.classes.Student;
import students_pk.modules.data.model.FacultyList;
import students_pk.modules.data.model.StudentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FacultyController {
    private ObservableList<Faculty> facultyData;
    private boolean isInit;

    @FXML
    private TableView<Faculty> facultyTable;

    @FXML
    private TableColumn <Faculty, String> facultyName;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    public void addClicked() throws IOException {
        initFacultyData();
        Faculty fake = new Faculty(0, "");
        FacultyModalWindow modal = new FacultyModalWindow(fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked){

            initialize();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initFacultyData();
        Faculty selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();
        FacultyModalWindow modal = new FacultyModalWindow(selectedFaculty);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked){
            initialize();
        }
    }

    @FXML
    public void deleteClicked() {
        Faculty selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Удаление факультета");
        alert.setContentText("Вы уверены, что хотите удалить данный факультет?");
        alert.showAndWait();
        if (!alert.getResult().getButtonData().isCancelButton()) {
            selectedFaculty.delete();
            initialize();
        }
    }

    public void initialize() {
        initFacultyData();
        facultyTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        if (!isInit) {
            facultyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> {
                if (newSelection != null) {
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            });
        }
        facultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        facultyTable.setItems(facultyData);
        isInit = true;
    }

    private void initFacultyData() {
        ArrayList<Object[]> facultyArray = FacultyList.getList();
        facultyData = FXCollections.observableArrayList();

        for (int i = 0; i < facultyArray.size(); i++) {
            Object row[] = facultyArray.get(i);

            int id = (int) row[0];
            String facultyName = (String) row[1];

            facultyData.add(new Faculty(id, facultyName));
        }
    }

}
