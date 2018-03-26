package Frontend.sample;

import Backend.Model.CampaignModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExampleController implements ScreenInterface {

    private ScreensController myController;
    private CampaignModel campaignModel;
    private File currentImpressions;
    private File currentClick;
    private File currentServer;
    @FXML
    private javafx.scene.control.TextField impressionsF;
    @FXML
    private javafx.scene.control.TextField clicksF;
    @FXML
    private javafx.scene.control.TextField bouncesF;
    @FXML
    private javafx.scene.control.TextField conversionsF;
    @FXML
    private javafx.scene.control.TextField totalCostF;
    @FXML
    private javafx.scene.control.TextField clickRateF;
    @FXML
    private javafx.scene.control.TextField aquisitionF;
    @FXML
    private javafx.scene.control.TextField costPerClickF;
    @FXML
    private javafx.scene.text.Text impressions;
    @FXML
    private javafx.scene.text.Text clicks;
    @FXML
    private javafx.scene.text.Text bounces;
    @FXML
    private javafx.scene.text.Text conversions;
    @FXML
    private javafx.scene.text.Text totalCost;
    @FXML
    private javafx.scene.text.Text clickRate;
    @FXML
    private javafx.scene.text.Text aquisition;
    @FXML
    private javafx.scene.text.Text costPerClick;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private TableView campaignsTable;
    @FXML
    private MenuButton campaignName;
    @FXML
    private CheckMenuItem campaignOne;

    private List<Campaign> campaigns = new ArrayList<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(campaignName, campaignOne, impressionsF, clicksF, bouncesF, conversionsF, totalCostF, clickRateF, aquisitionF, costPerClickF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart));
        impressions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showImpressions(campaignName.getText());
                });
        clicks.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showClicks(campaignName.getText());
                });
        bounces.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showBounces(campaignName.getText());
                });
        conversions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showConversion(campaignName.getText());
                });
        totalCost.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showTotalCost(campaignName.getText());
                });
        clickRate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showClickRate(campaignName.getText());
                });
        aquisition.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showAquisition(campaignName.getText());
                });
        costPerClick.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showCostPerClick(campaignName.getText());
                });


        campaignsTable.setPrefSize(265, 150);
        campaignsTable.setPlaceholder(new Label("No campaigns loaded!"));
        campaigns.add(new Campaign("Campaign 1"));
        campaignOne.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                for (MenuItem menuItem : campaignName.getItems()) {
                    if (menuItem instanceof CheckMenuItem) {
                        ((CheckMenuItem) menuItem).setSelected(false);
                    }
                }
                campaignOne.setSelected(true);
                setMetrics(campaignOne.getText());
            }
        });
        campaigns.stream().forEach((campaign) -> {
            campaignsTable.getItems().add(campaign);
            campaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> {
                        campaigns.remove(campaign);
                        campaignsTable.getItems().remove(campaign);
                    });
        });

        TableColumn<Campaign, String> nameColumn =
                new TableColumn<>("Campaign");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory(
                (TableColumn.CellDataFeatures<Campaign, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getName())
        );
        TableColumn<Campaign, String> displayColumn =
                new TableColumn<>("Display");
        displayColumn.setPrefWidth(50);
        displayColumn.setCellValueFactory(
                new PropertyValueFactory<>("displayed")
        );

        TableColumn<Campaign, String> removeColumn =
                new TableColumn<>("Display");
        removeColumn.setPrefWidth(50);
        removeColumn.setCellValueFactory(
                new PropertyValueFactory<>("remove")
        );
        campaignsTable.getColumns().setAll(nameColumn, displayColumn, removeColumn);
    }

    @FXML
    private void addNewCampaign(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Load New Campaign");
        alert.setContentText("Input Campaign Details:");

        Label campaignNameL = new Label("Campaign Name");
        TextField campaignNameF = new TextField();
        campaignNameF.setPrefWidth(300);
        campaignNameF.setPromptText("Input Campaign Name...");

        Label impressionsL = new Label("Impressions Log");
        TextField impressionsF = new TextField();
        impressionsF.setPrefWidth(300);
        impressionsF.setPromptText("Select Impressions Log...");
        impressionsF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Impressions Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentClick == file || currentServer == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentImpressions = file;
                            String fileName = file.getName();
                            impressionsF.setText(fileName);
                        }
                    }
                });
        Button impressionsB = new Button("Browse");
        impressionsB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Impressions Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentClick == file || currentServer == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentImpressions = file;
                            String fileName = file.getName();
                            impressionsF.setText(fileName);
                        }
                    }
                });


        Label clicksL = new Label("Select Click Log");
        TextField clicksF = new TextField();
        clicksF.setPrefWidth(300);
        clicksF.setPromptText("Select Click Log...");
        clicksF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Click Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentImpressions == file || currentServer == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentClick = file;
                            String fileName = file.getName();
                            clicksF.setText(fileName);
                        }
                    }
                });
        Button clicksB = new Button("Browse");
        clicksB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Click Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentImpressions == file || currentServer == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentClick = file;
                            String fileName = file.getName();
                            clicksF.setText(fileName);
                        }
                    }
                });


        Label serverL = new Label("Select Server Log");
        TextField serverF = new TextField();
        serverF.setPrefWidth(300);
        serverF.setPromptText("Select Server Log...");
        serverF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Server Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentImpressions == file || currentClick == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentServer = file;
                            String fileName = file.getName();
                            serverF.setText(fileName);
                        }
                    }
                });

        Button serverB = new Button("Browse");
        serverB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    Node node = (Node) e.getSource();
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Select Server Log");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
                    chooser.getExtensionFilters().add(extFilter);
                    File file = chooser.showOpenDialog(node.getScene().getWindow());
                    if (file != null) {
                        if (currentImpressions == file || currentClick == file) {
                            JOptionPane.showMessageDialog(null, "This file has already been loaded!"
                                    , "Warning", 1);
                        } else {
                            currentServer = file;
                            String fileName = file.getName();
                            serverF.setText(fileName);
                        }
                    }
                });

        GridPane content = new GridPane();
        content.setPrefSize(400, 200);

        content.add(campaignNameL, 0, 0);
        content.add(campaignNameF, 0, 1);

        content.add(impressionsL, 0, 3);
        content.add(impressionsF, 0, 4);
        content.add(impressionsB, 2, 4);

        content.add(clicksL, 0, 5);
        content.add(clicksF, 0, 6);
        content.add(clicksB, 2, 6);

        content.add(serverL, 0, 7);
        content.add(serverF, 0, 8);
        content.add(serverB, 2, 8);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (currentImpressions == null || currentClick == null || currentServer == null) {
                JOptionPane.showMessageDialog(null, "You need to select 3 files in order to load a campaign!"
                        , "Warning", 1);
                addNewCampaign(event);
            } else if (campaignNameF.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "You need to input a campaign name before continuing!"
                        , "Warning", 1);
                addNewCampaign(event);
            } else {
                Campaign campaign = new Campaign(campaignNameF.getText());
                campaigns.add(campaign);
                campaignsTable.getItems().add(campaign);
                campaigns.stream().forEach((c) -> {
                    c.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> {
                                campaigns.remove(c);
                                campaignsTable.getItems().remove(c);
                            });
                });
                CheckMenuItem checkMenuItem = new CheckMenuItem(campaignNameF.getText());
                checkMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        for (MenuItem menuItem : campaignName.getItems()) {
                            if (menuItem instanceof CheckMenuItem) {
                                ((CheckMenuItem) menuItem).setSelected(false);
                            }
                        }
                        checkMenuItem.setSelected(true);
                        setMetrics(checkMenuItem.getText());
                    }
                });
                campaignName.getItems().add(checkMenuItem);
                System.out.println("Number of campaigns: " + campaigns.size());
            }
        }
    }

    private void setMetrics(String name) {
        campaignName.setText(name);
        System.out.println(name);
    }

    private void showImpressions(String name) {
        //Just for testing
        lineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Something", 3));
        series.getData().add(new XYChart.Data("2", 15));
        series.getData().add(new XYChart.Data("3", 32));
        series.getData().add(new XYChart.Data("4", 1));
        series.getData().add(new XYChart.Data("5", 8));
        series.getData().add(new XYChart.Data("Else", 30));
        series.getData().add(new XYChart.Data("7", 7));
        series.getData().add(new XYChart.Data("8", 4));
        series.setName(name);

        lineChart.getData().add(series);
    }

    private void showClicks(String name) {

    }

    private void showBounces(String name) {

    }

    private void showConversion(String name) {

    }

    private void showTotalCost(String name) {

    }

    private void showClickRate(String name) {

    }

    private void showAquisition(String name) {

    }

    private void showCostPerClick(String name) {

    }
}