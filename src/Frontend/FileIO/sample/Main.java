package Frontend.FileIO.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String welcomeScreenID = "welcomeScreen";
    public static String welcomeScreen = "WelcomeScreen.fxml";
    public static String loadDataScreenID = "loadDataScreen";
    public static String loadDataScreen = "LoadDataScreen.fxml";
    public static String viewDataScreenID = "viewDataScreen";
    public static String viewDataScreen = "LoadDataScreen.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.welcomeScreenID, Main.welcomeScreen);
        mainContainer.loadScreen(Main.loadDataScreenID, Main.loadDataScreen);
        mainContainer.loadScreen(Main.viewDataScreenID, Main.viewDataScreen);

        mainContainer.setScreen(Main.welcomeScreenID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}