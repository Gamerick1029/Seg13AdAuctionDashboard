package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import Backend.Model.Stubs.DataModelStub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.HashMap;

/**
 * Created by Yoana on 25/02/2018.
 * This class is the controller for the Welcome Screen
 * and implements all the functionality for the FXML file.
 */
public class WelcomeController implements ScreenInterface {

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }

    @FXML
    private void goToLoadDataScreen(ActionEvent event){
        myController.setScreen(Main.loadDataScreenID);
    }

    /*
    Load a debug view from the stub data
     */
    @FXML
    private void goToStubView(ActionEvent e)
    {
        DataModel dm = new DataModelStub();
        myController.setCurrentModel(dm);
        myController.setDataModelMap(new HashMap<>());
        myController.addDataModel(dm.getName(), dm);
        myController.getDataFieldPopulator().populateFields();
        myController.getCampaignDataPopulator().populateGraph();
        myController.setScreen(Main.campaignScreenID);
    }
}