package students_pk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

import students_pk.modules.ModulesManager;

public class Main extends Application {

    public static Stage primaryStage;
    private TabPane rootLayout;


    @Override
    public void start(Stage primaryStage){
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("ПК приёмной комиссии");

        initRootLayout();
        new ModulesManager(rootLayout).run();
    }

    private void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("modules/main/template/mainWindow.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

/* ССылкота

https://stackoverflow.com/questions/44317837/create-search-textfield-field-to-search-in-a-javafx-tableview

http://o7planning.org/ru/11087/javafx-choicebox-tutorial

http://code.i-harness.com/ru/q/11c1c02
http://www.java2s.com/Code/Java/JavaFX/AddTabtoTabPane.htm
http://www.quizful.net/post/javafx-inception-001


https://habrahabr.ru/post/104229/

 */