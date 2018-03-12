package Frontend.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String welcomeScreenID = "welcomeScreen";
    public static String welcomeScreen = "WelcomeScreen.fxml";
    public static String loadDataScreenID = "loadDataScreen";
    public static String loadDataScreen = "LoadDataScreen.fxml";
    public static String viewDataScreenID = "viewDataScreen";
    public static String viewDataScreen = "ViewDataScreen.fxml";
    public static String campaignScreen = "CampaignScreen.fxml";
    public static String campaignScreenID = "campaignScreen";

    @Override
    public void start(Stage primaryStage) throws Exception{

        ScreensController mainContainer = new ScreensController(primaryStage);
        
        mainContainer.loadScreen(Main.welcomeScreenID, Main.welcomeScreen);
        mainContainer.loadScreen(Main.loadDataScreenID, Main.loadDataScreen);
        mainContainer.loadScreen(Main.viewDataScreenID, Main.viewDataScreen);
        mainContainer.loadScreen(Main.campaignScreenID, Main.campaignScreen);

        mainContainer.setScreen(Main.welcomeScreenID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ad Auction Dashboard");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
