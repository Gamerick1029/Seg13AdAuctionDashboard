package Frontend;

import Backend.DBHelper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Created by Yoana on 25/02/2018.
 * This class is used to run the application and
 * connect all of the screen controllers to the FXML files.
 */
public class Main extends Application {

    public static final Alert loadBox = new Alert(Alert.AlertType.INFORMATION);

    static {
        loadBox.setContentText("Change me");
        loadBox.setHeaderText("Change me");
        loadBox.setTitle("Loading...");

        loadBox.getButtonTypes().clear();
        loadBox.getDialogPane().getButtonTypes().clear();
        loadBox.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = loadBox.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    public static String welcomeScreenID = "welcomeScreen";
    public static String welcomeScreen = "WelcomeScreen.fxml";
    public static String loadDataScreenID = "loadDataScreen";
    public static String loadDataScreen = "LoadDataScreen.fxml";
    public static String viewDataScreenID = "viewDataScreen";
    public static String viewDataScreen = "ViewDataScreen.fxml";
    public static String campaignScreen = "CampaignScreen.fxml";
    public static String campaignScreenID = "campaignScreen";
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        DBHelper.initConnection("seg", "seg13");

        ScreensController mainContainer = new ScreensController(primaryStage);
        mainContainer.loadScreen(Main.welcomeScreenID, Main.welcomeScreen);

        mainContainer.loadScreen(Main.loadDataScreenID, Main.loadDataScreen);
        mainContainer.loadScreen(Main.viewDataScreenID, Main.viewDataScreen);
        mainContainer.loadScreen(Main.campaignScreenID, Main.campaignScreen);
       
        
        mainContainer.sceneProperty().addListener((observable, oldValue, newValue) -> {
            mainContainer.prefWidthProperty().bind(newValue.widthProperty());
            mainContainer.prefHeightProperty().bind(newValue.heightProperty());
        });

        mainContainer.setScreen(Main.welcomeScreenID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        scene = new Scene(root);
        scene.getStylesheets().add("Frontend/darkTheme.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ad Auction Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}