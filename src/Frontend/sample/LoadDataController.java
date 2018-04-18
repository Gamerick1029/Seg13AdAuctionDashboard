package Frontend.sample;

import Backend.DBHelper;
import Backend.Model.CampaignModelDB;
import Backend.Model.Interfaces.DataModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Yoana on 25/02/2018.
 * This class is the controller for the Load Data Screen
 * and implements all the functionality for the FXML file.
 */
public class LoadDataController implements ScreenInterface {

    private ScreensController myController;
    @FXML
    private javafx.scene.control.TextField impressionsLogField;
    @FXML
    private javafx.scene.control.TextField clickLogField;
    @FXML
    private javafx.scene.control.TextField serverLogField;
    @FXML
    private javafx.scene.control.TextField campaignName;

    private String currentName = null;
    private File impressions;
    private File clicks;
    private File server;
    private File currentImpressions;
    private File currentClick;
    private File currentServer;
    private HashMap<String, File> files = new HashMap<>();
    private DBHelper dbHelper;

    @Override
    public void setScreenParent(ScreensController parent) {
        dbHelper = new DBHelper();
        this.myController = parent;
    }

    @FXML
    private void goToViewDataScreen(ActionEvent event) {
        currentName = campaignName.getText();
        if (files.size() < 3) {
            JOptionPane.showMessageDialog(null, "You need to select 3 files in order to load a campaign!"
                    , "Warning", 1);
        } else if (currentName.equals("")) {
            JOptionPane.showMessageDialog(null, "You need to input a campaign name before continuing!"
                    , "Warning", 1);
        } else try {
            if (dbHelper.getCampaigns().contains(currentName)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Warning");
                GridPane content = new GridPane();
                content.setPrefSize(300, 50);
                Label label = new Label("This campaign already exists!");
                content.add(label, 0, 0);
                alert.getDialogPane().setContent(content);
                alert.showAndWait();
            } else {
                final DataModel[] dataModel = new DataModel[1];

                Task<DataModel> task = new Task<>() {
                    @Override
                    protected DataModel call() throws Exception {
                        return new CampaignModelDB(campaignName.getText(), impressions, clicks, server);
                    }
                };

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creating Campaign");

                task.setOnRunning(e -> alert.show());

                task.setOnSucceeded(e -> {
                    dataModel[0] = task.getValue();
                    myController.setCurrentModel(dataModel[0]);
                    myController.setDataModelMap(new HashMap<>());
                    myController.addDataModel(currentName, dataModel[0]);
                    myController.getDataFieldPopulator().populateFields();
                    myController.getCampaignDataPopulator().populateGraph();
                    alert.close();
                    myController.setScreen(Main.campaignScreenID);
                });

                task.setOnFailed(e -> JOptionPane.showMessageDialog(null, "Cannot load campaign! Please try again."
                        , "Warning", 1));

                new Thread(task).start();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Warning");
            GridPane content = new GridPane();
            content.setPrefSize(500, 50);
            Label label = new Label(e.getMessage());
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
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
        files.clear();
        impressionsLogField.setPromptText("Load Impressions Log...");
        clickLogField.setPromptText("Load Click Log...");
        serverLogField.setPromptText("Load Server Log...");
        campaignName.setPromptText("Input Campaign Name...");
        campaignName.setText("");

        //Blank out current files to correctly reload new ones
        currentClick = null;
        currentImpressions = null;
        currentServer = null;
        currentName = null;
    }

}