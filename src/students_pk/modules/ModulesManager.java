package students_pk.modules;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import students_pk.modules.data.controller.DataModuleController;
import students_pk.modules.pass_exam.controller.PassExamModuleController;
import students_pk.modules.reports.controller.ReportsModuleController;

public class ModulesManager {

    private TabPane rootLayout;

    public ModulesManager(TabPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void run() {
        Tab dataTab = new DataModuleController().init();
        Tab passExamTab = new PassExamModuleController().init();
        Tab reportsTab = new ReportsModuleController().init();
        rootLayout.getTabs().addAll(dataTab, passExamTab, reportsTab);
    }
}
