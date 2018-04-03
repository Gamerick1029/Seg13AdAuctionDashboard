package Frontend.sample;

import Backend.DBHelper;
import Backend.Model.CampaignModel;
import Backend.Model.Interfaces.DataModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Yoana on 12/03/2018.
 * This class is the controller for the Campaign Screen
 * and implements all the functionality for the FXML file.
 */
public class ExampleController implements ScreenInterface {

    private ScreensController myController;
    private DBHelper dbHelper;
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
    private BarChart<?, ?> barChart;
    @FXML
    private AreaChart<?, ?> areaChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private TableView campaignsTable;
    @FXML
    private MenuButton graphType;
    @FXML
    private MenuButton campaignName;
    @FXML
    private CheckMenuItem campaignOne;
    @FXML
    private CheckMenuItem lineType;
    @FXML
    private CheckMenuItem barType;
    @FXML
    private CheckMenuItem pieType;
    @FXML
    private CheckMenuItem areaType;
    @FXML
    private RadioButton byDay;
    @FXML
    private RadioButton byWeek;
    @FXML
    private RadioButton byMonth;
    @FXML
    private ProgressIndicator progressIndicator;

    private XYChart.Series campaignMetricLC;
    private XYChart.Series campaignMetricBC;
    private XYChart.Series campaignMetricAC;
    private ObservableList<PieChart.Data> campaignMetricPC;

    private String currentMetricDisplayed = "Impressions";
    private List<Campaign> campaignsLoaded = new ArrayList<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(campaignName, campaignOne, impressionsF, clicksF, bouncesF, conversionsF, totalCostF, clickRateF, aquisitionF, costPerClickF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart, barChart, pieChart, areaChart));
        impressions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showImpressions(myController.getDataModel(campaignName.getText()).getName());
                });
        clicks.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showClicks(myController.getDataModel(campaignName.getText()).getName());
                });
        bounces.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showBounces(myController.getDataModel(campaignName.getText()).getName());
                });
        conversions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showConversion(myController.getDataModel(campaignName.getText()).getName());
                });
        totalCost.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showTotalCost(myController.getDataModel(campaignName.getText()).getName());
                });
        clickRate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showClickRate(myController.getDataModel(campaignName.getText()).getName());
                });
        aquisition.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showAquisition(myController.getDataModel(campaignName.getText()).getName());
                });
        costPerClick.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showCostPerClick(myController.getDataModel(campaignName.getText()).getName());
                });
        lineType.setOnAction(t -> {
            changeToLineChart();
        });
        barType.setOnAction(t -> {
            changeToBarChart();
        });
        areaType.setOnAction(t -> {
            changeToAreaChart();
        });
        pieType.setOnAction(t -> {
            changeToPieChart();
        });
        byDay.setOnAction(r -> {
            byDay.setSelected(true);
            byWeek.setSelected(false);
            byMonth.setSelected(false);
            if (campaignsLoaded.size() == 1) {
                campaignsLoaded.clear();
                campaignsLoaded.add(new Campaign((String) myController.getDataModelMap().keySet().toArray()[0]));
            }
            if (byDay.isSelected()) {
                groupByDay();
            }
        });
        byWeek.setOnAction(r -> {
            byWeek.setSelected(true);
            byDay.setSelected(false);
            byMonth.setSelected(false);
            if (campaignsLoaded.size() == 1) {
                campaignsLoaded.clear();
                campaignsLoaded.add(new Campaign((String) myController.getDataModelMap().keySet().toArray()[0]));
            }
            if (byWeek.isSelected()) {
                groupByWeek();
            }
        });
        byMonth.setOnAction(r -> {
            byMonth.setSelected(true);
            byWeek.setSelected(false);
            byDay.setSelected(false);
            if (campaignsLoaded.size() == 1) {
                campaignsLoaded.clear();
                campaignsLoaded.add(new Campaign((String) myController.getDataModelMap().keySet().toArray()[0]));
            }
            if (byMonth.isSelected()) {
                groupByMonth();
            }
        });
        campaignsTable.setPrefSize(265, 150);
        campaignsTable.setPlaceholder(new Label("No campaignsLoaded loaded!"));
        campaignsLoaded.add(new Campaign(campaignName.getText()));
        campaignOne.setOnAction(t -> {
            for (MenuItem menuItem : campaignName.getItems()) {
                if (menuItem instanceof CheckMenuItem) {
                    ((CheckMenuItem) menuItem).setSelected(false);
                }
            }
            campaignOne.setSelected(true);
            try {
                setMetrics(campaignOne.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        campaignsLoaded.stream().forEach((campaign) -> {
            campaignsTable.getItems().add(campaign);
            campaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> {
                        if (campaign.getDisplayed().isSelected()) {
                            showCampaignOnGraph(campaign.getName());
                        } else {
                            hideCampaignFromGraph(campaign.getName());
                        }
                    });
            campaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> {
                        campaignsLoaded.remove(campaign);
                        campaignsTable.getItems().remove(campaign);
                        myController.removeDataModel(campaign.getName());
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
            } else if (myController.getDataModelMap().containsKey(campaignNameF.getText())) {
                JOptionPane.showMessageDialog(null, "This campaign already exists!"
                        , "Warning", 1);
                addNewCampaign(event);
            } else { // If a campaign is loaded correctly:
                Campaign campaign = new Campaign(campaignNameF.getText());
                //Adding the new campaign to the Campaigns table
                campaignsLoaded.add(campaign);
                //Adding a new CheckMenuItem for the new campaign
                campaignsTable.getItems().add(campaign);
                //Adding a new Data Model to ScreensController
                try {
                    myController.addDataModel(campaign.getName(), new CampaignModel(campaign.getName(),currentClick, currentImpressions, currentServer));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // For each campaign in the Campaigns table,
                // add EventHandlers for the Displayed CheckBox button and the Remove button
                campaignsLoaded.stream().forEach((c) -> {
                    c.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> {
                                if (c.getDisplayed().isSelected()) {
                                    showCampaignOnGraph(c.getName());
                                } else {
                                    hideCampaignFromGraph(c.getName());
                                }
                            });
                    c.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> {
                                //OnClick on Remove Campaign button,
                                // remove the campaign from the list of campaignsLoaded and from the table
                                campaignsLoaded.remove(c);
                                campaignsTable.getItems().remove(c);
                            });
                });
                //Creating a new CheckMenuItem for the new campaign
                CheckMenuItem checkMenuItem = new CheckMenuItem(campaignNameF.getText());
                //Adding an EventHandler for the new CheckMenuItem
                //OnSelected, set the rest of the CheckMenuItems to false and the selected one to true
                //and set the metric fields with the selected campaign's data
                checkMenuItem.setOnAction(t -> {
                    for (MenuItem menuItem : campaignName.getItems()) {
                        ((CheckMenuItem) menuItem).setSelected(false);
                    }
                    checkMenuItem.setSelected(true);
                    try {
                        setMetrics(checkMenuItem.getText());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                //Adding the new CheckMenuItem to the MenuButton for the current campaignsLoaded
                campaignName.getItems().add(checkMenuItem);
            }
        }
    }

    /*
    Sets the TextFields with the selected campaign's metrics
     */
    private void setMetrics(String name) throws SQLException {
        campaignName.setText(name);
        DataModel dm = myController.getDataModel(name);
        impressionsF.setText(String.valueOf(dm.getImpressionsNumber()));
        clicksF.setText(String.valueOf(dm.getClicksNumber()));
        bouncesF.setText(String.valueOf(dm.getBouncesNumber()));
        conversionsF.setText(String.valueOf(dm.getConversionsNumber()));
        totalCostF.setText(String.valueOf(dm.getTotalCost()));
        clickRateF.setText(String.valueOf(dm.getCTR()));
        aquisitionF.setText(String.valueOf(dm.getCPA()));
        costPerClickF.setText(String.valueOf(dm.getCPC()));
    }

    /*
    Displays a selected campaign on the graph
     */
    private void showCampaignOnGraph(String campaignName) {
        switch (this.currentMetricDisplayed) {
            case "Impressions":
                showImpressions(campaignName);
                break;
            case "Clicks":
                showClicks(campaignName);
                break;
            case "Bounces":
                showBounces(campaignName);
                break;
            case "Conversions":
                showConversion(campaignName);
                break;
            case "TotalCost":
                showTotalCost(campaignName);
                break;
            case "ClickRate":
                showClickRate(campaignName);
                break;
            case "Aquisition":
                showAquisition(campaignName);
                break;
            case "CostPerClick":
                showCostPerClick(campaignName);
                break;
        }
    }

    //TODO: hide campaign from graph somehow...
    private void hideCampaignFromGraph(String name) {
        Iterator<Campaign> iter = null;
        iter = campaignsLoaded.iterator();
        while (iter.hasNext()) {
            if (iter.next().getName().equals(name)) {

            }
        }
        lineChart.getData();
        barChart.getData().remove(name);
        areaChart.getData().remove(name);
        pieChart.getData().remove(name);
    }

    private void groupByDay() {
        Integer step = 1000 * 60 * 60 * 24;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, campaign.getName(), step);
            }
        }
    }

    private void groupByWeek() {
        Integer step = 1000 * 60 * 60 * 54;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, campaign.getName(), step);
            }
        }
    }

    private void groupByMonth() {
        Integer step = 1000 * 60 * 50 * 12;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, campaign.getName(), step);
            }
        }
    }

    private void changeToLineChart() {
        graphType.setText("Line Chart");
        lineChart.setVisible(true);
        pieChart.setVisible(false);
        areaChart.setVisible(false);
        barChart.setVisible(false);
        lineType.setSelected(true);
        barType.setSelected(false);
        areaType.setSelected(false);
        pieType.setSelected(false);
    }

    private void changeToBarChart() {
        graphType.setText("Bar Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        areaChart.setVisible(false);
        barChart.setVisible(true);
        lineType.setSelected(false);
        barType.setSelected(true);
        areaType.setSelected(false);
        pieType.setSelected(false);
    }

    private void changeToAreaChart() {
        graphType.setText("Area Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        barChart.setVisible(false);
        areaChart.setVisible(true);
        lineType.setSelected(false);
        barType.setSelected(false);
        areaType.setSelected(true);
        pieType.setSelected(false);
    }

    private void changeToPieChart() {
        graphType.setText("Pie Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(true);
        areaChart.setVisible(false);
        barChart.setVisible(false);
        lineType.setSelected(false);
        barType.setSelected(false);
        areaType.setSelected(false);
        pieType.setSelected(true);
    }

    private void showImpressions(String campaign) {
        setStyleToMetric("Impressions");
        currentMetricDisplayed = "Impressions";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60;
        else step = 1000 * 60;
        populateMetric("Impressions", campaign, step);
    }

    private void showClicks(String campaign) {
        setStyleToMetric("Clicks");
        currentMetricDisplayed = "Clicks";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60;
        else step = 1000 * 60;
        populateMetric("Clicks", campaign, step);
    }

    private void showBounces(String campaign) {
        setStyleToMetric("Bounces");
        currentMetricDisplayed = "Bounces";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60;
        else step = 1000 * 60;
        populateMetric("Bounces", campaign, step);
    }

    private void showConversion(String campaign) {
        setStyleToMetric("Conversions");
        currentMetricDisplayed = "Conversions";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60;
        else step = 1000 * 60;
        populateMetric("Conversions", campaign, step);
    }

    private void showTotalCost(String campaign) {
        setStyleToMetric("TotalCost");
        currentMetricDisplayed = "TotalCost";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60;
        else step = 1000 * 60;
        populateMetric("TotalCost", campaign, step);
    }

    private void showClickRate(String campaign) {
        setStyleToMetric("ClickRate");
        currentMetricDisplayed = "ClickRate";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60 * 24 * 7;
        else step = 1000 * 60 * 60 * 24;
        populateMetric("ClickRate", campaign, step);
    }

    private void showAquisition(String campaign) {
        setStyleToMetric("Aquisition");
        currentMetricDisplayed = "Aquisition";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60 * 24 * 7;
        else step = 1000 * 60 * 60 * 24;
        populateMetric("Aquisition", campaign, step);
    }

    private void showCostPerClick(String campaign) {
        setStyleToMetric("CostPerClick");
        currentMetricDisplayed = "CostPerClick";
        Integer step = 0;
        if (byDay.isSelected()) step = 1000 * 60 * 60 * 24;
        else if (byWeek.isSelected()) step = 1000 * 60 * 60 * 24 * 7;
        else step = 1000 * 60 * 60 * 24;
        populateMetric("CostPerClick", campaign, step);
    }

    private void setStyleToMetric(String metric) {
        impressions.setStyle(null);
        clicks.setStyle(null);
        bounces.setStyle(null);
        conversions.setStyle(null);
        totalCost.setStyle(null);
        clickRate.setStyle(null);
        aquisition.setStyle(null);
        costPerClick.setStyle(null);
        switch (metric) {
            case "Impressions":
                impressions.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "Clicks":
                clicks.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "Bounces":
                bounces.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "Conversions":
                conversions.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "TotalCost":
                totalCost.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "ClickRate":
                clickRate.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "Aquisition":
                aquisition.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
            case "CostPerClick":
                costPerClick.setStyle("-fx-fill: #948e8e; -fx-font-size: 20;");
                break;
        }
    }

    private void populateMetric(String metric, String campaign, Integer step) {
        DataModel dm = myController.getDataModel(campaign);
        lineChart.getData().clear();
        barChart.getData().clear();
        areaChart.getData().clear();
        pieChart.getData().clear();

        campaignMetricLC = new XYChart.Series();
        campaignMetricBC = new XYChart.Series();
        campaignMetricAC = new XYChart.Series();
        campaignMetricPC = FXCollections.observableArrayList();

        campaignMetricLC.setName(dm.getName() + " " + metric);
        campaignMetricBC.setName(dm.getName() + " " + metric);
        campaignMetricAC.setName(dm.getName() + " " + metric);

        switch (metric) {
            case "Impressions":
                for (Map.Entry<Date, Integer> entry : dm.getFullImpressions(step).entrySet()) {
                    Date key = entry.getKey();
                    Integer value = entry.getValue();
                    addData(key, value);
                }
                break;
            case "Clicks":
                for (Map.Entry<Date, Integer> entry : dm.getFullClicks(step).entrySet()) {
                    Date key = entry.getKey();
                    Integer value = entry.getValue();
                    addData(key, value);
                }
                break;
            case "Bounces":
                for (Map.Entry<Date, Integer> entry : dm.getFullBounces(step).entrySet()) {
                    Date key = entry.getKey();
                    Integer value = entry.getValue();
                    addData(key, value);
                }
                break;
            case "Conversions":
                for (Map.Entry<Date, Integer> entry : dm.getFullConversions(step).entrySet()) {
                    Date key = entry.getKey();
                    Integer value = entry.getValue();
                    addData(key, value);
                }
                break;
            case "TotalCost":
                for (Map.Entry<Date, Float> entry : dm.getFullCost(step).entrySet()) {
                    Date key = entry.getKey();
                    Float value = entry.getValue();
                    addData(key, Math.round(value));
                }
                break;
            case "ClickRate":
                for (Map.Entry<Date, Float> entry : dm.getFullCTR(step).entrySet()) {
                    Date key = entry.getKey();
                    Float value = entry.getValue();
                    addData(key, Math.round(value));
                }
            case "Aquisition":
                for (Map.Entry<Date, Float> entry : dm.getFullCPA(step).entrySet()) {
                    Date key = entry.getKey();
                    Float value = entry.getValue();
                    addData(key, Math.round(value));
                }
                break;
            case "CostPerClick":
                for (Map.Entry<Date, Float> entry : dm.getFullCPC(step).entrySet()) {
                    Date key = entry.getKey();
                    Float value = entry.getValue();
                    addData(key, Math.round(value));
                }
                break;
        }
        lineChart.getData().add(campaignMetricLC);
        barChart.getData().add(campaignMetricBC);
        areaChart.getData().add(campaignMetricAC);
        pieChart.setData(campaignMetricPC);
    }

    private void addData(Date key, Integer value) {
        campaignMetricLC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricBC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricAC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricPC.add(new PieChart.Data(String.valueOf(key), value));
    }
}