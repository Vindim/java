package students_pk.modules;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import students_pk.Main;
import students_pk.modules.data.controller.DataModuleController;

import java.io.IOException;

public class ModulesManager {

    private TabPane rootLayout;

    public ModulesManager(TabPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void run() {
        Tab tab = new DataModuleController().init();
        rootLayout.getTabs().add(tab);
    }
}
