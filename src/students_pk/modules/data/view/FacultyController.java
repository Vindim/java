package students_pk.modules.data.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import students_pk.Main;
import students_pk.lib.Events;
import students_pk.modules.data.classes.Faculty;
import students_pk.modules.data.classes.Student;
import students_pk.modules.data.model.FacultyList;

import java.io.IOException;
import java.util.ArrayList;

public class FacultyController {
    private ObservableList<Faculty> facultyData;

    private FilteredList<Faculty> filteredFacultyData;
    private SortedList<Faculty> sortedFacultyData;

    @FXML
    private TableView<Faculty> facultyTable;

    @FXML
    private TableColumn<Faculty, String> facultyName;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchField;

    @FXML
    public void clearSearch() {
        searchField.clear();
    }

    @FXML
    public void addClicked() throws IOException {
        initFacultyData();
        Faculty fake = new Faculty(0, "");
        FacultyModalWindow modal = new FacultyModalWindow(fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initFacultyData();
        Faculty selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();
        FacultyModalWindow modal = new FacultyModalWindow(selectedFaculty);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
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
            String res = selectedFaculty.delete();
            if (!res.equals("ok")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.initOwner(Main.primaryStage);
                error.setTitle("Ошибка");
                error.setHeaderText("Удаление факультета");
                error.setContentText(res);
                error.showAndWait();
            }
            reloadTable();
        }
    }

    @FXML
    public void fullReload() {
        reloadTable();
    }

    public void initialize() {

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredFacultyData.setPredicate(Faculty -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                String name = Faculty.getName().toLowerCase();
                if (String.valueOf(name).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        Events.setButtonDisable(facultyTable, editButton, deleteButton);
        facultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        reloadTable();
    }

    private void reloadTable() {
        initFacultyData();

        facultyTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        filteredFacultyData = new FilteredList<>(facultyData, p -> true);
        sortedFacultyData = new SortedList<>(filteredFacultyData);
        sortedFacultyData.comparatorProperty().bind(facultyTable.comparatorProperty());
        facultyTable.setItems(sortedFacultyData);
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
