package Frontend.sample;

import Backend.DBHelper;
import Backend.Model.CampaignModelDBTrimmed;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Stubs.DataModelStub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Yoana on 25/02/2018.
 * This class is the controller for the Welcome Screen
 * and implements all the functionality for the FXML file.
 */
public class WelcomeController implements ScreenInterface {

    private ScreensController myController;
    private DBHelper dbHelper;
    @FXML
    private MenuButton loadExistingCampaign;

    @Override
    public void setScreenParent(ScreensController parent) {
        dbHelper = new DBHelper();
        this.myController = parent;

        try {
            for (String s : dbHelper.getCampaigns()) {
                MenuItem menuItem = new MenuItem(s);
                menuItem.setOnAction(e -> {
                    goToViewDataScreen(e, menuItem.getText());
                });
                loadExistingCampaign.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Warning");
            GridPane content = new GridPane();
            content.setPrefSize(300, 50);
            Label label = new Label(e.getMessage());
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        }

    }


    private void goToViewDataScreen(ActionEvent event, String name) {
        DataModel dataModel = null;
        try {
            dataModel = new CampaignModelDBTrimmed(name);
            myController.setCurrentModel(dataModel);
            myController.setDataModelMap(new HashMap<>());
            myController.getDataFieldPopulator().populateFields();
            myController.getCampaignDataPopulator().populateGraph();
            myController.setScreen(Main.campaignScreenID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot load campaign! Please try again."
                    , "Warning", 1);
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLoadDataScreen(ActionEvent event) {
        myController.setScreen(Main.loadDataScreenID);
    }

    /*
    Load a debug view from the stub data
     */
    @FXML
    private void goToStubView(ActionEvent e) {
        DataModel dm = new DataModelStub();
        myController.setCurrentModel(dm);
        myController.setDataModelMap(new HashMap<>());
        myController.addDataModel(dm.getName(), dm);
        myController.getDataFieldPopulator().populateFields();
        myController.getCampaignDataPopulator().populateGraph();
        myController.setScreen(Main.campaignScreenID);
    }
}