package students_pk.modules;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import students_pk.modules.data.controller.DataModuleController;

public class ModulesManager {

    private TabPane rootLayout;

    public ModulesManager(TabPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void run() {
        Tab dataTab = new DataModuleController().init();
        rootLayout.getTabs().add(dataTab);
    }
}
