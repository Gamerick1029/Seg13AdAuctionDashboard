package Frontend.sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


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
    }

    @FXML
    private void goToLoadDataScreen(ActionEvent event) {
        myController.setScreen(Main.loadDataScreenID);
    }

    public void populateScreen() {
        impressions.setText(String.valueOf(myController.getDataModel().getImpressionsNumber()));
        clicks.setText(String.valueOf(myController.getDataModel().getClicksNumber()));
        bounces.setText(String.valueOf(myController.getDataModel().getBouncesNumber()));
        conversions.setText(String.valueOf(myController.getDataModel().getConversionsNumber()));
        cost.setText(String.valueOf(myController.getDataModel().getTotalCost()));
        clickRate.setText(String.valueOf(myController.getDataModel().getCTR()));
        costAquisition.setText(String.valueOf(myController.getDataModel().getCPA()));
        costConversion.setText(String.valueOf(myController.getDataModel().getCPC()));
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



}