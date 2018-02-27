package Frontend.FileIO.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class LoadDataController implements ScreenInterface {

    private ScreensController myController;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }

    @FXML
    private void goToViewDataScreen(ActionEvent event) {
        myController.setScreen(Main.viewDataScreenID);
    }

    @FXML
    private void loadImpressionsLogButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Impressions Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

    @FXML
    private void loadImpressionsLogField(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Impressions Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

    @FXML
    private void loadClickLogButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Click Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

    @FXML
    private void loadClickLogField(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Impressions Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

    @FXML
    private void loadServerLogButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Server Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

    @FXML
    private void loadServerLogField(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Impressions Log");
        Node node = (Node) event.getSource();
        chooser.showOpenDialog(node.getScene().getWindow());
    }

}