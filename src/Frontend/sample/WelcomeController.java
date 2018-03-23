package Frontend.sample;


import Backend.Model.CampaignModel;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Stubs.DataModelStub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        myController.setDataModel(dm);
        myController.getDataFieldPopulator().populateFields();
        myController.getCampaignDataPopulator().populateGraph();
        myController.setScreen(Main.campaignScreenID);
    }
}