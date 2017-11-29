package students_pk.views.modules;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public class ViewLoader {

    private List<Tab> tabs;

    public ViewLoader(TabPane rootLayout) {
        tabs = rootLayout.getTabs();


    }

    public void load() {

    }

}
