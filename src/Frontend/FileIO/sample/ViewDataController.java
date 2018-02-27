package Frontend.FileIO.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ViewDataController implements ScreenInterface {

    private ScreensController myController;
    @FXML private javafx.scene.control.Button closeButton;

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
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}