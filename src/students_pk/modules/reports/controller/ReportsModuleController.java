package students_pk.modules.reports.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import students_pk.Main;

import java.io.IOException;

public class ReportsModuleController {
    private Tab tab;
    private TabPane reportWindow;

    public ReportsModuleController() {
        this.tab = new Tab();
        this.reportWindow = new TabPane();
        tab.setText("Отчеты");
    }

    public Tab init() {
        try {
            FXMLLoader studentsReportLoader = new FXMLLoader();
            studentsReportLoader.setLocation(Main.class.getResource("modules/reports/template/studentsReport.fxml"));

            Tab studentReports = studentsReportLoader.load();

            reportWindow.getTabs().addAll(studentReports);
            tab.setContent(reportWindow);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
}
