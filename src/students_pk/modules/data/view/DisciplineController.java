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
import students_pk.modules.data.classes.Discipline;
import students_pk.modules.data.model.DisciplineList;

import java.io.IOException;
import java.util.ArrayList;

public class DisciplineController {


    private ObservableList<Discipline> disciplineData;

    private FilteredList<Discipline> filteredDisciplineData;
    private SortedList<Discipline> sortedDisciplineData;

    @FXML
    private TableView<Discipline> disciplineTable;

    @FXML
    private TableColumn<Discipline, String> disciplineName;

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
        initDisciplineData();
        Discipline fake = new Discipline(0, "", 0);
        DisciplineModalWindow modal = new DisciplineModalWindow(fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initDisciplineData();
        Discipline selectedDiscipline = disciplineTable.getSelectionModel().getSelectedItem();
        DisciplineModalWindow modal = new DisciplineModalWindow(selectedDiscipline);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void deleteClicked() {
        Discipline selectedDiscipline = disciplineTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Удаление предмета");
        alert.setContentText("Вы уверены, что хотите удалить данный предмет?");
        alert.showAndWait();
        if (!alert.getResult().getButtonData().isCancelButton()) {
            String res = selectedDiscipline.delete();
            if (!res.equals("ok")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.initOwner(Main.primaryStage);
                error.setTitle("Ошибка");
                error.setHeaderText("Удаление предмета");
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
            filteredDisciplineData.setPredicate(Discipline -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                String name = Discipline.getName().toLowerCase();
                if (String.valueOf(name).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        Events.setButtonDisable(disciplineTable, editButton, deleteButton);
        disciplineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        reloadTable();
    }

    private void reloadTable() {
        initDisciplineData();

        disciplineTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        filteredDisciplineData = new FilteredList<>(disciplineData, p -> true);
        sortedDisciplineData = new SortedList<>(filteredDisciplineData);
        sortedDisciplineData.comparatorProperty().bind(disciplineTable.comparatorProperty());
        disciplineTable.setItems(sortedDisciplineData);
    }

    private void initDisciplineData() {
        ArrayList<Object[]> disciplineArray = DisciplineList.getList();
        disciplineData = FXCollections.observableArrayList();

        for (int i = 0; i < disciplineArray.size(); i++) {
            Object row[] = disciplineArray.get(i);
            int id = (int) row[0];
            String disciplineName = (String) row[1];
            Integer mask = (Integer) row[2];
            disciplineData.add(new Discipline(id, disciplineName, mask));
        }
    }
}
