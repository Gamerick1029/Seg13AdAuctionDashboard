package Frontend.FileIO.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoadDataController implements ScreenInterface {

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }

    @FXML
    private void goToViewDataScreen(ActionEvent event){
        myController.setScreen(Main.viewDataScreenID);
    }
}
