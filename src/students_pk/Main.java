package students_pk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("test many controllers");

        initRootLayout();
        showContent();
        //showTab();
    }

    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/mainWindow.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showContent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/content.fxml"));
            TabPane personOverview = loader.load();

            Tab tab =  new Tab("Студенты");

            Node contentOnTab;
            contentOnTab = FXMLLoader.load(Main.class.getResource("views/contentOnTab.fxml"));

            tab.setContent(contentOnTab);

            //rootLayout.getChildren().add(tab);

            personOverview.getTabs().add(tab);

            rootLayout.setCenter(personOverview);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTab() {
        try {
            Parent contentOnTab;
            contentOnTab = FXMLLoader.load(Main.class.getResource("views/contentOnTab.fxml"));

            rootLayout.getChildren().add(contentOnTab);
            System.out.print(rootLayout.getChildren());
            //this.primaryStage.getScene().setRoot(contentOnTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}

/*     @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    } */


/* ССылкота

http://code.i-harness.com/ru/q/11c1c02
http://www.java2s.com/Code/Java/JavaFX/AddTabtoTabPane.htm
http://www.quizful.net/post/javafx-inception-001

 */