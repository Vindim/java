package students_pk;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private TabPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("test many controllers");

        initRootLayout();
        showContent();
        searchModules();
    }

    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/templates/mainWindow.fxml"));
            rootLayout = loader.load();

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
            FXMLLoader studentsLoader = new FXMLLoader();
            studentsLoader.setLocation(Main.class.getResource("views/templates/students.fxml"));

            FXMLLoader facultyLoader = new FXMLLoader();
            facultyLoader.setLocation(Main.class.getResource("views/templates/faculty.fxml"));

            Tab faculty = facultyLoader.load();

            Tab students = studentsLoader.load();
            Tab data = rootLayout.getTabs().get(0);

            TabPane dataTabs = new TabPane();

            dataTabs.getTabs().addAll(students, faculty);

            data.setContent(dataTabs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchModules() {
        List <String> searchedFiles;
        File file = new File("");

        //System.out.print();
    }

    public void loader() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* ССылкота

http://code.i-harness.com/ru/q/11c1c02
http://www.java2s.com/Code/Java/JavaFX/AddTabtoTabPane.htm
http://www.quizful.net/post/javafx-inception-001

 */