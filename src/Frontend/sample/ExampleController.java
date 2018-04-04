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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private CheckBox age90;
    @FXML
    private CheckBox age50to69;
    @FXML
    private CheckBox age70to89;
    @FXML
    private CheckBox age30to49;
    @FXML
    private CheckBox age19to29;
    @FXML
    private CheckBox age18;
    @FXML
    private CheckBox income29;
    @FXML
    private CheckBox income19;
    @FXML
    private CheckBox income59;
    @FXML
    private CheckBox income100;
    @FXML
    private CheckBox income89;
    @FXML
    private CheckBox income100more;
    @FXML
    private CheckBox female;
    @FXML
    private CheckBox male;
    @FXML
    private CheckBox other;
    @FXML
    private CheckBox shopping;
    @FXML
    private CheckBox social;
    @FXML
    private CheckBox food;
    @FXML
    private CheckBox healthcare;
    @FXML
    private CheckBox sport;
    @FXML
    private CheckBox entertainment;
    @FXML
    private TextField startDay;
    @FXML
    private TextField endDay;
    @FXML
    private MenuButton startMonth;
    @FXML
    private MenuButton endMonth;
    @FXML
    private TextField startYear;
    @FXML
    private TextField endYear;
    @FXML
    private Text searchDate;

    private XYChart.Series campaignMetricLC;
    private XYChart.Series campaignMetricBC;
    private XYChart.Series campaignMetricAC;
    private ObservableList<PieChart.Data> campaignMetricPC;

    private Campaign currentCampaign = new Campaign("");
    private String currentMetricDisplayed = "Impressions";
    private long currentStep = 1000 * 60 * 60 * 24;
    private final long DAY_STEP = 1000 * 60 * 60 * 24;
    private final long WEEK_STEP = 1000 * 60 * 60 * 24 * 7;
    private final long MONTH_STEP = 1000 * 60 * 60 * 24 * 30;
    private List<Campaign> campaignsLoaded = new ArrayList<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(currentCampaign, campaignsLoaded, campaignsTable, campaignName, campaignOne, impressionsF, clicksF, bouncesF, conversionsF, totalCostF, clickRateF, aquisitionF, costPerClickF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart, barChart, pieChart, areaChart));

        currentCampaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(currentCampaign.getName(), currentMetricDisplayed);
                });
        currentCampaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    campaignsLoaded.remove(currentCampaign);
                    campaignsTable.getItems().remove(currentCampaign);
                    myController.removeDataModel(currentCampaign.getName());
                    populateMetric(currentMetricDisplayed, currentStep);
                });
        impressions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "Impressions");
                });
        clicks.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "Clicks");
                });
        bounces.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "Bounces");
                });
        conversions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "Conversions");
                });
        totalCost.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "TotalCost");
                });
        clickRate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "ClickRate");
                });
        aquisition.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "Aquisition");
                });
        costPerClick.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(myController.getDataModel(campaignName.getText()).getName(), "CostPerClick");
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
            if (byDay.isSelected()) {
                groupByDay();
            }
        });
        byWeek.setOnAction(r -> {
            byWeek.setSelected(true);
            byDay.setSelected(false);
            byMonth.setSelected(false);
            if (byWeek.isSelected()) {
                groupByWeek();
            }
        });
        byMonth.setOnAction(r -> {
            byMonth.setSelected(true);
            byWeek.setSelected(false);
            byDay.setSelected(false);
            if (byMonth.isSelected()) {
                groupByMonth();
            }
        });

        age90.setOnAction(r -> {
            if (age90.isSelected()) {
                filterGraph(age90.getText());
            } else {
                filterUnselected(age90.getText());
            }
        });
        age50to69.setOnAction(r -> {
            if (age50to69.isSelected()) {
                filterGraph(age50to69.getText());
            }
        });
        age70to89.setOnAction(r -> {
            if (age70to89.isSelected()) {
                filterGraph(age70to89.getText());
            } else {
                filterUnselected(age70to89.getText());
            }
        });
        age30to49.setOnAction(r -> {
            if (age30to49.isSelected()) {
                filterGraph(age30to49.getText());
            } else {
                filterUnselected(age30to49.getText());
            }
        });
        age19to29.setOnAction(r -> {
            if (age19to29.isSelected()) {
                filterGraph(age19to29.getText());
            } else {
                filterUnselected(age19to29.getText());
            }
        });
        age18.setOnAction(r -> {
            if (age18.isSelected()) {
                filterGraph(age18.getText());
            } else {
                filterUnselected(age18.getText());
            }
        });
        income29.setOnAction(r -> {
            if (income29.isSelected()) {
                filterGraph(income29.getText());
            } else {
                filterUnselected(income29.getText());
            }
        });
        income19.setOnAction(r -> {
            if (income19.isSelected()) {
                filterGraph(income19.getText());
            } else {
                filterUnselected(income19.getText());
            }
        });
        income59.setOnAction(r -> {
            if (income59.isSelected()) {
                filterGraph(income59.getText());
            } else {
                filterUnselected(income59.getText());
            }
        });
        income100.setOnAction(r -> {
            if (income100.isSelected()) {
                filterGraph(income100.getText());
            } else {
                filterUnselected(income100.getText());
            }
        });
        income89.setOnAction(r -> {
            if (income89.isSelected()) {
                filterGraph(income89.getText());
            } else {
                filterUnselected(income89.getText());
            }
        });
        income100more.setOnAction(r -> {
            if (income100more.isSelected()) {
                filterGraph(income100more.getText());
            } else {
                filterUnselected(income100more.getText());
            }
        });
        female.setOnAction(r -> {
            if (female.isSelected()) {
                filterGraph(female.getText());
            } else {
                filterUnselected(female.getText());
            }
        });
        male.setOnAction(r -> {
            if (male.isSelected()) {
                filterGraph(male.getText());
            } else {
                filterUnselected(male.getText());
            }
        });
        other.setOnAction(r -> {
            if (other.isSelected()) {
                filterGraph(other.getText());
            } else {
                filterUnselected(other.getText());
            }
        });
        shopping.setOnAction(r -> {
            if (shopping.isSelected()) {
                filterGraph(shopping.getText());
            } else {
                filterUnselected(shopping.getText());
            }
        });
        social.setOnAction(r -> {
            if (social.isSelected()) {
                filterGraph(social.getText());
            } else {
                filterUnselected(social.getText());
            }
        });
        food.setOnAction(r -> {
            if (food.isSelected()) {
                filterGraph(food.getText());
            } else {
                filterUnselected(food.getText());
            }
        });
        healthcare.setOnAction(r -> {
            if (healthcare.isSelected()) {
                filterGraph(healthcare.getText());
            } else {
                filterUnselected(healthcare.getText());
            }
        });
        sport.setOnAction(r -> {
            if (sport.isSelected()) {
                filterGraph(sport.getText());
            } else {
                filterUnselected(sport.getText());
            }
        });
        entertainment.setOnAction(r -> {
            if (entertainment.isSelected()) {
                filterGraph(entertainment.getText());
            } else {
                filterUnselected(entertainment.getText());
            }
        });
        searchDate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    applyDateFilter();
                });
        startMonth.getItems().add(new CheckMenuItem("January"));
        endMonth.getItems().add(new CheckMenuItem("January"));
        startMonth.getItems().add(new CheckMenuItem("February"));
        endMonth.getItems().add(new CheckMenuItem("February"));
        startMonth.getItems().add(new CheckMenuItem("March"));
        endMonth.getItems().add(new CheckMenuItem("March"));
        startMonth.getItems().add(new CheckMenuItem("April"));
        endMonth.getItems().add(new CheckMenuItem("April"));
        startMonth.getItems().add(new CheckMenuItem("May"));
        endMonth.getItems().add(new CheckMenuItem("May"));
        startMonth.getItems().add(new CheckMenuItem("June"));
        endMonth.getItems().add(new CheckMenuItem("June"));
        startMonth.getItems().add(new CheckMenuItem("July"));
        endMonth.getItems().add(new CheckMenuItem("July"));
        startMonth.getItems().add(new CheckMenuItem("August"));
        endMonth.getItems().add(new CheckMenuItem("August"));
        startMonth.getItems().add(new CheckMenuItem("September"));
        endMonth.getItems().add(new CheckMenuItem("September"));
        startMonth.getItems().add(new CheckMenuItem("October"));
        endMonth.getItems().add(new CheckMenuItem("October"));
        startMonth.getItems().add(new CheckMenuItem("November"));
        endMonth.getItems().add(new CheckMenuItem("November"));
        startMonth.getItems().add(new CheckMenuItem("December"));
        endMonth.getItems().add(new CheckMenuItem("December"));
        for (MenuItem month : startMonth.getItems()) {
            month.setOnAction(r -> {
                for (MenuItem startMonth : startMonth.getItems()) {
                    ((CheckMenuItem) startMonth).setSelected(false);
                }
                ((CheckMenuItem) month).setSelected(true);
            });
        }
        for (MenuItem month : endMonth.getItems()) {
            month.setOnAction(r -> {
                for (MenuItem endMonth : endMonth.getItems()) {
                    ((CheckMenuItem) endMonth).setSelected(false);
                }
                ((CheckMenuItem) month).setSelected(true);
            });
        }

        campaignsTable.setPrefSize(265, 150);
        campaignsTable.setPlaceholder(new Label("No campaigns loaded!"));

        campaignOne.setOnAction(t -> {
            for (MenuItem menuItem : campaignName.getItems()) {
                if (menuItem instanceof CheckMenuItem) {
                    ((CheckMenuItem) menuItem).setSelected(false);
                }
            }
            campaignOne.setSelected(true);
            setMetrics(campaignOne.getText());
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
                // add EventHandlers for the Displayed CheckBox button and the Remove button
                campaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e -> {
                            showMetric(campaign.getName(), currentMetricDisplayed);
                        });
                campaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e -> {
                            campaignsLoaded.remove(campaign);
                            campaignsTable.getItems().remove(campaign);
                            myController.removeDataModel(campaign.getName());
                            populateMetric(currentMetricDisplayed, currentStep);
                        });
                //Adding the new campaign to the Campaigns table
                campaignsLoaded.add(campaign);
                //Adding a new CheckMenuItem for the new campaign
                campaignsTable.getItems().add(campaign);
                //Adding a new Data Model to ScreensController
                DataModel dataModel = null;
                try {
                    dataModel = new CampaignModel(campaign.getName(), currentClick, currentImpressions, currentServer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myController.addDataModel(campaign.getName(), dataModel);
                populateMetric(currentMetricDisplayed, currentStep);
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
                //Adding the new CheckMenuItem to the MenuButton for the current campaignsLoaded
                campaignName.getItems().add(checkMenuItem);
            }
        }
    }

    @FXML
    private void applyDateFilter() {
        System.out.println("Date selected");
    }

    private void filterGraph(String filter) {
        System.out.println("Filter selected: " + filter);
    }

    private void filterUnselected(String filter) {
        System.out.println("Filter unselected: " + filter);
    }

    /*
    Sets the TextFields with the selected campaign's metrics
     */
    private void setMetrics(String name) {
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

    private void groupByDay() {
        currentStep = DAY_STEP;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, currentStep);
            }
        }
    }

    private void groupByWeek() {
        currentStep = WEEK_STEP;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, currentStep);
            }
        }
    }

    private void groupByMonth() {
        currentStep = MONTH_STEP;
        for (Campaign campaign : campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                populateMetric(this.currentMetricDisplayed, currentStep);
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

    private void showMetric(String campaign, String metric) {
        switch (metric) {
            case "Impressions":
                setStyleToMetric("Impressions");
                currentMetricDisplayed = "Impressions";
                populateMetric(metric, currentStep);
                break;
            case "Clicks":
                setStyleToMetric("Clicks");
                currentMetricDisplayed = "Clicks";
                populateMetric(metric, currentStep);
                break;
            case "Bounces":
                setStyleToMetric("Bounces");
                currentMetricDisplayed = "Bounces";
                populateMetric(metric, currentStep);
                break;
            case "Conversions":
                setStyleToMetric("Conversions");
                currentMetricDisplayed = "Conversions";
                populateMetric(metric, currentStep);
                break;
            case "TotalCost":
                setStyleToMetric("TotalCost");
                currentMetricDisplayed = "TotalCost";
                populateMetric(metric, currentStep);
                break;
            case "ClickRate":
                setStyleToMetric("ClickRate");
                currentMetricDisplayed = "ClickRate";
                populateMetric(metric, currentStep);
                break;
            case "Aquisition":
                setStyleToMetric("Aquisition");
                currentMetricDisplayed = "Aquisition";
                populateMetric(metric, currentStep);
                break;
            case "CostPerClick":
                setStyleToMetric("CostPerClick");
                currentMetricDisplayed = "CostPerClick";
                populateMetric(metric, currentStep);
                break;
        }
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

    public void populateMetric(String metric, long step) {
        lineChart.getData().clear();
        barChart.getData().clear();
        areaChart.getData().clear();
        pieChart.getData().clear();

        for (Campaign campaign : this.campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                DataModel dataModel = myController.getDataModel(campaign.getName());

                campaignMetricLC = new XYChart.Series();
                campaignMetricBC = new XYChart.Series();
                campaignMetricAC = new XYChart.Series();
                campaignMetricPC = FXCollections.observableArrayList();

                campaignMetricLC.setName(dataModel.getName() + " " + metric);
                campaignMetricBC.setName(dataModel.getName() + " " + metric);
                campaignMetricAC.setName(dataModel.getName() + " " + metric);

                switch (metric) {
                    case "Impressions":
                        for (Map.Entry<Date, Integer> entry : dataModel.getFullImpressions(step).entrySet()) {
                            Date key = entry.getKey();
                            Integer value = entry.getValue();
                            addData(key, value);
                        }
                        break;
                    case "Clicks":
                        for (Map.Entry<Date, Integer> entry : dataModel.getFullClicks(step).entrySet()) {
                            Date key = entry.getKey();
                            Integer value = entry.getValue();
                            addData(key, value);
                        }
                        break;
                    case "Bounces":
                        for (Map.Entry<Date, Integer> entry : dataModel.getFullBounces(step).entrySet()) {
                            Date key = entry.getKey();
                            Integer value = entry.getValue();
                            addData(key, value);
                        }
                        break;
                    case "Conversions":
                        for (Map.Entry<Date, Integer> entry : dataModel.getFullConversions(step).entrySet()) {
                            Date key = entry.getKey();
                            Integer value = entry.getValue();
                            addData(key, value);
                        }
                        break;
                    case "TotalCost":
                        for (Map.Entry<Date, Float> entry : dataModel.getFullCost(step).entrySet()) {
                            Date key = entry.getKey();
                            Float value = entry.getValue();
                            addData(key, Math.round(value));
                        }
                        break;
                    case "ClickRate":
                        for (Map.Entry<Date, Float> entry : dataModel.getFullCTR(step).entrySet()) {
                            Date key = entry.getKey();
                            Float value = entry.getValue();
                            addData(key, Math.round(value));
                        }
                    case "Aquisition":
                        for (Map.Entry<Date, Float> entry : dataModel.getFullCPA(step).entrySet()) {
                            Date key = entry.getKey();
                            Float value = entry.getValue();
                            addData(key, Math.round(value));
                        }
                        break;
                    case "CostPerClick":
                        for (Map.Entry<Date, Float> entry : dataModel.getFullCPC(step).entrySet()) {
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
        }
    }

    private void addData(Date key, Integer value) {
        campaignMetricLC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricBC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricAC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricPC.add(new PieChart.Data(String.valueOf(key), value));
    }

}