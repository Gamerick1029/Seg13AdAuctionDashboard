package Frontend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Yoana on 25/02/2018.
 * This class is the controller for the View Data Screen
 * and implements all the functionality for the FXML file.
 */
public class ViewDataController implements ScreenInterface {

    private ScreensController myController;

    @FXML
    private javafx.scene.control.TextField impressions;
    @FXML
    private javafx.scene.control.TextField clicks;
    @FXML
    private javafx.scene.control.TextField bounces;
    @FXML
    private javafx.scene.control.TextField conversions;
    @FXML
    private javafx.scene.control.TextField cost;
    @FXML
    private javafx.scene.control.TextField clickRate;
    @FXML
    private javafx.scene.control.TextField costAquisition;
    @FXML
    private javafx.scene.control.TextField costConversion;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
//        myController.setDataFieldPopulator(new DataFieldPopulator(impressions, clicks, bounces, conversions, cost, clickRate, costAquisition, costConversion));
    }

    @FXML
    private void goToLoadDataScreen(ActionEvent event) {
        myController.setScreen(Main.loadDataScreenID);
    }

    @FXML
    private void closeApplication(ActionEvent event) {
        Stage stage = (Stage) myController.getScene().getWindow();
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.close();
    }

    @FXML
    private void goToCampaignScreen(){
        myController.setScreen(Main.campaignScreenID);
        myController.getDataFieldPopulator().populateFields();
        myController.getCampaignDataPopulator().populateGraph();
    }

}