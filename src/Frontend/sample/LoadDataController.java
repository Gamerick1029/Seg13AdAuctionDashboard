package Frontend.sample;

import Backend.Model.CampaignModel;
import Backend.Model.Interfaces.DataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LoadDataController implements ScreenInterface {

    private ScreensController myController;
    @FXML
    private javafx.scene.control.TextField impressionsLogField;
    @FXML
    private javafx.scene.control.TextField clickLogField;
    @FXML
    private javafx.scene.control.TextField serverLogField;


    private File impressions;
    private File clicks;
    private File server;
    private File currentImpressions;
    private File currentClick;
    private File currentServer;
    private HashMap<String, File> files = new HashMap<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
    }

    @FXML
    private void goToViewDataScreen(ActionEvent event) {
        if (files.size() < 3) {
            JOptionPane.showMessageDialog(null, "You need to select 3 files in order to load a campaign!"
                    , "Warning", 1);
        } else {
            DataModel dataModel = null;
            try {
                dataModel = new CampaignModel(clicks, impressions, server);
                myController.setDataModel(dataModel);
                myController.setScreen(Main.viewDataScreenID);
                myController.getDataFieldPopulator().populateFields();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage()
                        , "Warning", 1);
            }
        }
    }

    @FXML
    private void loadImpressionsLogButton(ActionEvent event) {
        String title = "Select Impressions Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentImpressions == null) {
                currentImpressions = file;
                impressionsLogField.setText(file.getName());
                impressions = file;
            } else {
                files.remove(currentImpressions.getName());
                currentImpressions = file;
                impressionsLogField.setText(file.getName());
                impressions = file;
            }
        }
    }

    @FXML
    private void loadImpressionsLogField(MouseEvent event) {
        String title = "Select Impressions Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentImpressions == null) {
                currentImpressions = file;
                impressionsLogField.setText(file.getName());
                impressions = file;
            } else {
                files.remove(currentImpressions.getName());
                currentImpressions = file;
                impressionsLogField.setText(file.getName());
                impressions = file;
            }
        }
    }

    @FXML
    private void loadClickLogButton(ActionEvent event) {
        String title = "Select Click Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentClick == null) {
                currentClick = file;
                clickLogField.setText(file.getName());
                clicks = file;
            } else {
                files.remove(currentClick.getName());
                currentClick = file;
                clickLogField.setText(file.getName());
                clicks = file;
            }
        }
    }

    @FXML
    private void loadClickLogField(MouseEvent event) {
        String title = "Select Click Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentClick == null) {
                currentClick = file;
                clickLogField.setText(file.getName());
                clicks = file;
            } else {
                files.remove(currentClick.getName());
                currentClick = file;
                clickLogField.setText(file.getName());
                clicks = file;
            }
        }
    }

    @FXML
    private void loadServerLogButton(ActionEvent event) {
        String title = "Select Server Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentServer == null) {
                currentServer = file;
                serverLogField.setText(file.getName());
                server = file;
            } else {
                files.remove(currentServer.getName());
                currentServer = file;
                serverLogField.setText(file.getName());
                server = file;
            }
        }
    }

    @FXML
    private void loadServerLogField(MouseEvent event) {
        String title = "Select Server Log";
        Node node = (Node) event.getSource();
        File file = loadData(node, title);
        if (file != null) {
            if (currentServer == null) {
                currentServer = file;
                serverLogField.setText(file.getName());
                server = file;
            } else {
                files.remove(currentServer.getName());
                currentServer = file;
                serverLogField.setText(file.getName());
                server = file;
            }
        }
    }

    private File loadData(Node node, String title) {
        String fileName = null;
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(node.getScene().getWindow());
        if (file != null) {
            fileName = file.getName();
            if (!files.containsKey(fileName)) {
                files.put(fileName, file);
                String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
                System.out.println("File extension: " + fileExtension);
                String requiredExtension = "csv";
                if (!requiredExtension.equals(fileExtension)) {
                    JOptionPane.showMessageDialog(null, "Selected file is not in the required format! " +
                            "Please select a CSV file!", "Warning", 1);
                    chooser.showOpenDialog(node.getScene().getWindow());
                }
                return file;
            } else {
                JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                        , "Warning", 1);
                return null;
            }
        }
        return null;
    }

    public void resetFields() {
        for (Map.Entry<String, File> fileEntry : files.entrySet()) {
            files.remove(fileEntry.getKey(), fileEntry.getValue());
        }
        impressionsLogField.setText("Load Impressions Log...");
        clickLogField.setText("Load Click Log...");
        serverLogField.setText("Load Server Log...");
    }

}