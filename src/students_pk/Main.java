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
        //Создаем главный слой и устанавливаем название
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("ПК приёмной комиссии");

        //инициализируем корневой слой
        initRootLayout();
        //запускаем менеджер модулей
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
