package students_pk.modules.data.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import students_pk.modules.data.classes.Room;
import students_pk.modules.main.controller.ErrorModal;

import java.io.IOException;

public class RoomModalController {


    private boolean isSaveClicked;
    private int roomId;

    @FXML
    private Button saveButton;

    @FXML
    private TextField roomNumberInput;

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    public void setRoomId(int id) {
        this.roomId = id;
    }



    @FXML
    private void saveRoomData() throws IOException {
        String roomNumber = roomNumberInput.getText();

        if (roomNumber.equals("")) {
            Stage stg = RoomModalWindow.stg;
            ErrorModal modal = new ErrorModal("Введите номер аудитории");
            modal.showWindow(stg);
        }
        else {
            if (this.roomId == 0) {
                new Room(0, roomNumber).save();
            }
            else {
                new Room(this.roomId, roomNumber).update();
            }
            isSaveClicked = true;
            RoomModalWindow.stg.close();
        }
    }
}
