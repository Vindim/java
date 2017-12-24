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
import students_pk.modules.data.classes.*;
import students_pk.modules.data.model.DisciplineList;
import students_pk.modules.data.model.ExamList;
import students_pk.modules.data.model.RoomList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExamController {

    private ObservableList<Exam> examData;
    private ObservableList<Discipline> disciplineData;
    private ObservableList<Room> roomData;
    private FilteredList<Exam> filteredExamData;
    private SortedList<Exam> sortedExamData;

    private static Map<String, Integer> disciplineMap;
    private static Map<String, Integer> roomMap;

    @FXML
    private TableView<Exam> examTable;

    @FXML
    private TableColumn<Exam, String> disciplineNameColumn;

    @FXML
    private TableColumn<Exam, String> roomNumberColumn;

    @FXML
    private TableColumn<Exam, String> dateColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchField;

    @FXML
    private void fullReload() {
        reloadTable();
    }

    @FXML
    private void clearSearch() {
        searchField.clear();
    }

    @FXML
    public void addClicked() throws IOException {
        initDiscipline();
        initRoom();
        Exam fake = new Exam(0, 0, 0, "", "", "");
        ExamModalWindow modal = new ExamModalWindow(disciplineData, roomData, fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initDiscipline();
        initRoom();
        Exam selectedExam = examTable.getSelectionModel().getSelectedItem();
        ExamModalWindow modal = new ExamModalWindow(disciplineData, roomData, selectedExam);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void deleteClicked() {
        Exam selectedExam = examTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Удаление экзамена");
        alert.setContentText("Вы уверены, что хотите удалить данный экзамен?");
        alert.showAndWait();
        if (!alert.getResult().getButtonData().isCancelButton()) {
            selectedExam.delete();
            reloadTable();
        }
    }

    public void initialize() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredExamData.setPredicate(Exam -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                String discipline = Exam.getDiscipline().toLowerCase();
                String room = Exam.getRoom().toLowerCase();
                String date = Exam.getDate();
                if (String.valueOf(discipline).contains(lowerCaseFilter)) {
                    return true;
                }
                if (String.valueOf(room).contains(lowerCaseFilter)) {
                    return true;
                }
                if (String.valueOf(date).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        Events.setButtonDisable(examTable, editButton, deleteButton);
        disciplineNameColumn.setCellValueFactory(new PropertyValueFactory<>("discipline"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reloadTable();
    }

    private void reloadTable() {
        initExamData();

        examTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        filteredExamData = new FilteredList<>(examData, p -> true);
        sortedExamData = new SortedList<>(filteredExamData);
        sortedExamData.comparatorProperty().bind(examTable.comparatorProperty());
        examTable.setItems(sortedExamData);
    }

    private void initExamData() {
        ArrayList<Object[]> examArray = ExamList.getList();
        examData = FXCollections.observableArrayList();

        for (int i = 0; i < examArray.size(); i++) {
            Object row[] = examArray.get(i);
            int id = (int) row[0];
            int disciplineId = (int) row[1];
            int roomId = (int) row[2];
            String disciplineName = (String) row[3];
            String roomNumber = (String) row[4];
            String date = (String) row[5];
            System.out.print(row[5]);
            examData.add(new Exam(id, disciplineId, roomId, disciplineName, roomNumber, date));
        }

    }

    private void initDiscipline() {
        ArrayList<Object[]> disciplineArray = null;
        disciplineArray = DisciplineList.getList();
        disciplineData = FXCollections.observableArrayList();
        disciplineMap = new HashMap<>();
        disciplineData.add(new Discipline(0, "Предмет"));
        disciplineMap.put("Предмет", 0);

        for (int i = 0; i < disciplineArray.size(); i++) {
            Object row[] = disciplineArray.get(i);
            String name = (String) row[1];
            int id = (int) row[0];
            disciplineMap.put(name, id);
            disciplineData.add(new Discipline(id, name));
        }
    }

    private void initRoom() {
        ArrayList<Object[]> roomArray = null;
        roomArray = RoomList.getList();
        roomData = FXCollections.observableArrayList();
        roomMap = new HashMap<>();
        roomData.add(new Room(0, "Аудитория"));
        roomMap.put("Аудитория", 0);

        for (int i = 0; i < roomArray.size(); i++) {
            Object row[] = roomArray.get(i);
            String number = (String) row[1];
            int id = (int) row[0];
            roomMap.put(number, id);
            roomData.add(new Room(id, number));
        }
    }

    public static int getIdByDisciplineName(String name) {
        return disciplineMap.get(name);
    }

    public static int getIdByRoomNumber(String number) {
        return roomMap.get(number);
    }



}
