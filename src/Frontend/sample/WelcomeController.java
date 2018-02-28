package Frontend.sample;


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
}