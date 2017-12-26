package students_pk.modules.reports.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import students_pk.modules.data.classes.Exam;
import students_pk.modules.data.model.ExamList;
import students_pk.modules.reports.model.CounterStudents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentsReportController {
    private ObservableList<Exam> examData;
    private Map<String, Integer> examMap;
    private int selectedExam;

    @FXML
    ComboBox examSelector;

    @FXML
    TextField dateField;

    @FXML
    TextField roomField;

    @FXML
    BarChart gist;

    @FXML
    CategoryAxis balls;

    @FXML
    NumberAxis percent;

    @FXML
    AnchorPane wrapper;

    @FXML
    public void fullReload() {
        reloadExamFilter();
    }


    @FXML
    public void clearSelectedExam() {
        examSelector.getSelectionModel().clearSelection();
        this.selectedExam = 0;
        reloadExamFilter();
    }

    public void initialize() {
        reloadExamFilter();
        examSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.equals("Выберите экзамен")) {
                Exam selected = (Exam) examSelector.getSelectionModel().getSelectedItem();

                selectedExam = getIdByExamName(newSelection.toString());
                dateField.setText(selected.getDate());
                roomField.setText(selected.getRoom());
                initGist();
            }

        });
    }



    private void initGist() {

        if (wrapper.getChildren().size() > 2) {
            wrapper.getChildren().remove(2);
        }

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Баллы за экзамен");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Процент от общего количества на предмет");

        // Create a BarChart
        gist = new BarChart<>(xAxis, yAxis);
        gist.setTitle("Распределение оценок и студентов");
        gist.setLayoutX(29.0);
        gist.setLayoutY(86.0);
        gist.setPrefHeight(458.0);
        gist.setPrefWidth(711.0);
        gist.setId("gist");

        wrapper.getChildren().add(gist);


        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("0-25");
        Double dataSeries1val = CounterStudents.getPercent(selectedExam, 0);
        dataSeries1.getData().add(new XYChart.Data<>("0-25", dataSeries1val));

        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<>();
        dataSeries2.setName("26-50");
        Double dataSeries2val = CounterStudents.getPercent(selectedExam, 1);
        dataSeries2.getData().add(new XYChart.Data<>("26-50", dataSeries2val));

        XYChart.Series<String, Number> dataSeries3 = new XYChart.Series<>();
        dataSeries3.setName("51-75");
        Double dataSeries3val = CounterStudents.getPercent(selectedExam, 2);
        dataSeries3.getData().add(new XYChart.Data<>("51-75", dataSeries3val));

        XYChart.Series<String, Number> dataSeries4 = new XYChart.Series<>();
        dataSeries4.setName("76-100");
        Double dataSeries4val = CounterStudents.getPercent(selectedExam, 3);
        dataSeries4.getData().add(new XYChart.Data<>("76-100", dataSeries4val));

        gist.getData().addAll(dataSeries1, dataSeries2, dataSeries3, dataSeries4);
    }

    private void reloadExamFilter() {
        initExamData();
        examSelector.setItems(examData);
        examSelector.getSelectionModel().select(0);
    }

    private void initExamData() {
        ArrayList<Object[]> examArray = null;
        examArray = ExamList.getList();
        examData = FXCollections.observableArrayList();
        examMap = new HashMap<>();
        examData.add(new Exam(0, 0, 0, "Выберите экзамен", "", ""));
        examMap.put("Выберите экзамен", 0);

        for (int i = 0; i < examArray.size(); i++) {
            Object row[] = examArray.get(i);
            int id = (int) row[0];
            int disciplineId = (int) row[1];
            String exam = (String) row[3];
            String room = (String) row[4];
            String date = (String) row[5];
            examMap.put(exam, id);
            examData.add(new Exam(id, disciplineId, 0, exam, room, date));
        }
    }

    private int getIdByExamName(String name) {
        return examMap.get(name);
    }


}
