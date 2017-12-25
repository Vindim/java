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
import students_pk.modules.data.classes.Room;
import students_pk.modules.data.model.RoomList;

import java.io.IOException;
import java.util.ArrayList;

public class RoomController {

    private ObservableList<Room> roomData;

    private FilteredList<Room> filteredRoomData;
    private SortedList<Room> sortedRoomData;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomNumber;

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
        initRoomData();
        Room fake = new Room(0, "");
        RoomModalWindow modal = new RoomModalWindow(fake);
        boolean saveClicked = modal.showAddWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void editClicked() throws IOException {
        initRoomData();
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        RoomModalWindow modal = new RoomModalWindow(selectedRoom);
        boolean saveClicked = modal.showEditWindow(Main.primaryStage);
        if (saveClicked) {
            reloadTable();
        }
    }

    @FXML
    public void deleteClicked() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.primaryStage);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Удаление аудитории");
        alert.setContentText("Вы уверены, что хотите удалить данную аудиторию?");
        alert.showAndWait();
        if (!alert.getResult().getButtonData().isCancelButton()) {
            String res = selectedRoom.delete();
            if (!res.equals("ok")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.initOwner(Main.primaryStage);
                error.setTitle("Ошибка");
                error.setHeaderText("Удаление аудитории");
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
            filteredRoomData.setPredicate(Room -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                String number = Room.getNumber().toLowerCase();
                if (String.valueOf(number).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        Events.setButtonDisable(roomTable, editButton, deleteButton);
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        reloadTable();
    }

    private void reloadTable() {
        initRoomData();

        roomTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        filteredRoomData = new FilteredList<>(roomData, p -> true);
        sortedRoomData = new SortedList<>(filteredRoomData);
        sortedRoomData.comparatorProperty().bind(roomTable.comparatorProperty());
        roomTable.setItems(sortedRoomData);
    }

    private void initRoomData() {
        ArrayList<Object[]> roomArray = RoomList.getList();
        roomData = FXCollections.observableArrayList();

        for (int i = 0; i < roomArray.size(); i++) {
            Object row[] = roomArray.get(i);
            int id = (int) row[0];
            String roomNumber = (String) row[1];
            roomData.add(new Room(id, roomNumber));
        }
    }
}
