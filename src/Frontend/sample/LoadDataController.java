package Frontend.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;

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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showOpenDialog(node.getScene().getWindow());

        if (file != null) {
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
            System.out.println("File extension: " + fileExtension);
            String requiredExtension = "csv";
            if (!requiredExtension.equals(fileExtension)) {
                JOptionPane.showMessageDialog(null, "Selected file is not in the required format! " +
                        "Please select a CSV file!", "Warning", 1);
                chooser.showOpenDialog(node.getScene().getWindow());
            }
        }
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