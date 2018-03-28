package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import Backend.Model.Stubs.DataModelStub;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExampleController implements ScreenInterface {

    private ScreensController myController;
    private DataModel dataModel = new DataModelStub();
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

    private String currentMetricDisplayed = "Impressions";
    private List<Campaign> campaigns = new ArrayList<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(campaignName, campaignOne, impressionsF, clicksF, bouncesF, conversionsF, totalCostF, clickRateF, aquisitionF, costPerClickF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart, barChart, pieChart, areaChart));
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
        campaignsTable.setPrefSize(265, 150);
        campaignsTable.setPlaceholder(new Label("No campaigns loaded!"));

        //TODO: Get the name of the loaded campaign
        campaigns.add(new Campaign(dataModel.getName()));
        campaignOne.setOnAction(t -> {
            for (MenuItem menuItem : campaignName.getItems()) {
                if (menuItem instanceof CheckMenuItem) {
                    ((CheckMenuItem) menuItem).setSelected(false);
                }
            }
            campaignOne.setSelected(true);
            setMetrics(campaignOne.getText());
        });
        campaigns.stream().forEach((campaign) -> {
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
            } else { // If a campaign is loaded correctly:
                Campaign campaign = new Campaign(campaignNameF.getText());
                //Adding the new campaign to the Campaigns table
                campaigns.add(campaign);
                //Adding a new CheckMenuItem for the new campaign
                campaignsTable.getItems().add(campaign);
                // For each campaign in the Campaigns table,
                // add EventHandlers for the Displayed CheckBox button and the Remove button
                campaigns.stream().forEach((c) -> {
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
                                // remove the campaign from the list of campaigns and from the table
                                campaigns.remove(c);
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
                    setMetrics(checkMenuItem.getText());
                });
                //Adding the new CheckMenuItem to the MenuButton for the current campaigns
                campaignName.getItems().add(checkMenuItem);
            }
        }
    }

    /*
    Sets the TextFields with the selected campaign's metrics
     */
    private void setMetrics(String name) {
        campaignName.setText(name);

        //TODO: Find campaign by name, then set fields with metrics

        impressionsF.setText(String.valueOf(56));
        clicksF.setText(String.valueOf(3.6));
        bouncesF.setText(String.valueOf(6));
        conversionsF.setText(String.valueOf(16));
        totalCostF.setText(String.valueOf(8));
        clickRateF.setText(String.valueOf(0.2));
        aquisitionF.setText(String.valueOf(0.9));
        costPerClickF.setText(String.valueOf(1));
    }

    /*
    Displays a selected campaign on the graph
     */
    private void showCampaignOnGraph(String name) {
        // Find campaign from list of campaigns
        switch (currentMetricDisplayed) {
            case "Impressions":
                showImpressions(name);
            case "Clicks":
                showClicks(name);
            case "Bounces":
                showBounces(name);
            case "Conversions":
                showConversion(name);
            case "TotalCost":
                showTotalCost(name);
            case "ClickRate":
                showClickRate(name);
            case "Aquisition":
                showAquisition(name);
            case "CostPerClick":
                showCostPerClick(name);
        }

    }

    private void hideCampaignFromGraph(String name) {
        Iterator<Campaign> iter = null;
        iter = campaigns.iterator();
        while (iter.hasNext()) {
            if (iter.next().getName().equals(name)) {

            }
        }
        lineChart.getData().remove(name);
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

    private void showImpressions(String name) {
        currentMetricDisplayed = "Impressions";
        //Show on all types of graphs
        XYChart.Series campaign1 = new XYChart.Series();
        campaign1.getData().add(new XYChart.Data("1", 123));
        campaign1.getData().add(new XYChart.Data("2", 145));
        campaign1.getData().add(new XYChart.Data("3", 162));
        campaign1.getData().add(new XYChart.Data("4", 111));
        lineChart.setTitle("Impressions");
        campaign1.setName(name);
        lineChart.getData().add(campaign1);
    }

    private void showClicks(String name) {
        currentMetricDisplayed = "Clicks";
        //Show on all types of graphs
    }

    private void showBounces(String name) {
        currentMetricDisplayed = "Bounces";
        //Show on all types of graphs
    }

    private void showConversion(String name) {
        currentMetricDisplayed = "Conversions";
        //Show on all types of graphs
    }

    private void showTotalCost(String name) {
        currentMetricDisplayed = "TotalCost";
        //Show on all types of graphs
    }

    private void showClickRate(String name) {
        currentMetricDisplayed = "ClickRate";
        //Show on all types of graphs
    }

    private void showAquisition(String name) {
        currentMetricDisplayed = "Aquisition";
        //Show on all types of graphs
    }

    private void showCostPerClick(String name) {
        currentMetricDisplayed = "CostPerClick";
        //Show on all types of graphs
    }
}