package students_pk.modules.data.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DateStringConverter;
import students_pk.Main;
import students_pk.lib.Events;
import students_pk.modules.data.classes.Discipline;
import students_pk.modules.data.classes.Exam;
import students_pk.modules.data.classes.Room;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExamModalWindow {

    private ObservableList <Discipline> discipline;
    private ObservableList <Room> room;
    public static Stage stg;

    private Exam exam;

    public ExamModalWindow(ObservableList<Discipline> discipline, ObservableList<Room> room, Exam exam) {
        this.discipline = discipline;
        this.room = room;
        this.exam = exam;
    }

    @SuppressWarnings("unchecked")
    public boolean showAddWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/examModal.fxml"));

        Pane root = modalLoader.load();

        ComboBox disciplineList = (ComboBox) root.getChildren().get(0);
        disciplineList.setItems(this.discipline);

        ComboBox roomList = (ComboBox) root.getChildren().get(1);
        roomList.setItems(this.room);

        TextField timeField = (TextField) root.getChildren().get(3);
        Button saveButton = (Button) root.getChildren().get(4);

        Events.setTimeFormatter(timeField, saveButton);

        Scene scene = new Scene(root);

        ExamModalController controller = modalLoader.getController();
        controller.setExamId(0);

        stg.setScene(scene);
        stg.setTitle("Добавление экзамена");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }

    @SuppressWarnings("unchecked")
    public boolean showEditWindow(Window modal) throws IOException {
        stg = new Stage();
        FXMLLoader modalLoader = new FXMLLoader();
        modalLoader.setLocation(Main.class.getResource("modules/data/template/examModal.fxml"));

        Pane root = modalLoader.load();


        ComboBox disciplineList = (ComboBox) root.getChildren().get(0);
        ComboBox roomList = (ComboBox) root.getChildren().get(1);
        DatePicker datePicker = (DatePicker) root.getChildren().get(2);
        TextField timeField = (TextField) root.getChildren().get(3);
        Button saveButton = (Button) root.getChildren().get(4);

        Events.setTimeFormatter(timeField, saveButton);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dateTime = this.exam.getDate();
        LocalDate localDate = LocalDate.parse(dateTime, formatter);

        datePicker.setValue(localDate);

        String time = dateTime.substring(11, 16);
        timeField.setText(time);

        disciplineList.setItems(this.discipline);
        roomList.setItems(this.room);
        disciplineList.getSelectionModel().select(this.exam.getDiscipline());
        roomList.getSelectionModel().select(this.exam.getRoom());


        Scene scene = new Scene(root);

        ExamModalController controller = modalLoader.getController();
        controller.setExamId(this.exam.getId());

        stg.setScene(scene);
        stg.setTitle("Редактирование экзамена");
        stg.initModality(Modality.WINDOW_MODAL);
        stg.initOwner(modal);
        stg.showAndWait();
        return controller.isSaveClicked();
    }


}
