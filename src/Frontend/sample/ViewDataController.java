package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import Backend.Model.Stubs.DataModelStub;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class ViewDataController implements ScreenInterface {

    private ScreensController myController;
    private DataModel dataModel = new DataModelStub();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }

    @FXML
    private void goToLoadDataScreen(ActionEvent event){
        myController.setScreen(Main.loadDataScreenID);
    }

    @FXML
    private void closeApplication(ActionEvent event){
        Stage stage = (Stage) myController.getScene().getWindow();
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.close();
    }
}