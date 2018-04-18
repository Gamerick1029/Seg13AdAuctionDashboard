package Frontend.sample;

import Backend.DBHelper;
import Backend.Model.CampaignModelDB;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.Step;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yoana on 12/03/2018.
 * This class is the controller for the Campaign Screen
 * and implements all the functionality for the FXML file.
 */
public class MainController implements ScreenInterface {

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
    private javafx.scene.control.TextField uniquesF;
    @FXML
    private javafx.scene.control.TextField bouncesF;
    @FXML
    private javafx.scene.control.TextField conversionsF;
    @FXML
    private javafx.scene.control.TextField totalCostF;
    @FXML
    private javafx.scene.control.TextField CTRF;
    @FXML
    private javafx.scene.control.TextField CPAF;
    @FXML
    private javafx.scene.control.TextField CPCF;
    @FXML
    private javafx.scene.control.TextField CPMF;
    @FXML
    private javafx.scene.control.TextField bounceRateF;


    @FXML
    private javafx.scene.text.Text impressions;
    @FXML
    private javafx.scene.text.Text clicks;
    @FXML
    private javafx.scene.text.Text uniques;
    @FXML
    private javafx.scene.text.Text bounces;
    @FXML
    private javafx.scene.text.Text conversions;
    @FXML
    private javafx.scene.text.Text totalCost;
    @FXML
    private javafx.scene.text.Text CTR;
    @FXML
    private javafx.scene.text.Text CPA;
    @FXML
    private javafx.scene.text.Text CPC;
    @FXML
    private javafx.scene.text.Text CPM;
    @FXML
    private javafx.scene.text.Text bounceRate;

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
    private CheckMenuItem histogramType;
    @FXML
    private MenuButton themes;
    @FXML
    private CheckMenuItem mintTheme;
    @FXML
    private CheckMenuItem lightTheme;
    @FXML
    private CheckMenuItem darkTheme;
    @FXML
    private RadioButton byDay;
    @FXML
    private RadioButton byWeek;
    @FXML
    private RadioButton byMonth;
    @FXML
    private CheckBox genderMale;
    @FXML
    private CheckBox genderFemale;
    @FXML
    private CheckBox genderOther;
    @FXML
    private CheckBox ageBelow25;
    @FXML
    private CheckBox age25to34;
    @FXML
    private CheckBox age35to44;
    @FXML
    private CheckBox age45to54;
    @FXML
    private CheckBox ageAbove54;
    @FXML
    private CheckBox incomeLow;
    @FXML
    private CheckBox incomeMedium;
    @FXML
    private CheckBox incomeHigh;
    @FXML
    private CheckBox contextNews;
    @FXML
    private CheckBox contextShopping;
    @FXML
    private CheckBox contextMedia;
    @FXML
    private CheckBox contextBlog;
    @FXML
    private CheckBox contextHobbies;
    @FXML
    private CheckBox contextTravel;
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
    private Button reset;
    @FXML
    private Button checkAll;
    @FXML
    private Text searchDate;

    private XYChart.Series campaignMetricLC;
    private XYChart.Series campaignMetricBC;
    private XYChart.Series campaignMetricAC;
    private XYChart.Series histogramSeries;
    private ObservableList<PieChart.Data> campaignMetricPC;
    private Scene scene;

    private Campaign currentCampaign = new Campaign("");
    private String currentMetricDisplayed = "Impressions";
    private String currentChartType = "LineChart";
    private Step currentStep = Step.DAY;
    private List<Campaign> campaignsLoaded = new ArrayList<>();
    private HashMap<String, File> files = new HashMap<>();

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(currentCampaign, campaignsLoaded, campaignsTable, campaignName, campaignOne, impressionsF, clicksF,
                uniquesF, bouncesF, conversionsF, totalCostF, CTRF, CPAF, CPCF, CPMF, bounceRateF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart, barChart, pieChart, areaChart));
        impressions.setStyle("-fx-font-weight: bold;");
        /*lineChart.animatedProperty().setValue(false);
        barChart.animatedProperty().setValue(false);
        pieChart.animatedProperty().setValue(false);
        areaChart.animatedProperty().setValue(false);*/
        currentCampaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    x.animatedProperty().setValue(false);
                    y.animatedProperty().setValue(false);
                    showMetric(currentMetricDisplayed);
                });
        currentCampaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    x.animatedProperty().setValue(false);
                    y.animatedProperty().setValue(false);
                    removeCampaign();
                });

        impressions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Impressions");
                });
        clicks.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Clicks");
                });
        uniques.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Uniques");
                });
        bounces.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Bounces");
                });
        conversions.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Conversions");
                });
        totalCost.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Total Cost");
                });
        CTR.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("CTR");
                });
        CPA.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("CPA");
                });
        CPC.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("CPC");
                });
        CPM.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("CPM");
                });
        bounceRate.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric("Bounce Rate");
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
        histogramType.setOnAction(t -> {
            changeToHistogram();
        });
        byDay.setOnAction(r -> {
            currentStep = Step.DAY;
            byDay.setSelected(true);
            byWeek.setSelected(false);
            byMonth.setSelected(false);
            groupByStep();
        });
        byWeek.setOnAction(r -> {
            currentStep = Step.WEEK;
            byWeek.setSelected(true);
            byDay.setSelected(false);
            byMonth.setSelected(false);
            groupByStep();
        });
        byMonth.setOnAction(r -> {
            currentStep = Step.MONTH;
            byMonth.setSelected(true);
            byWeek.setSelected(false);
            byDay.setSelected(false);
                groupByStep();
        });
        genderMale.setSelected(true);
        genderFemale.setSelected(true);
        genderOther.setSelected(true);
        ageBelow25.setSelected(true);
        age25to34.setSelected(true);
        age35to44.setSelected(true);
        age45to54.setSelected(true);
        ageAbove54.setSelected(true);
        incomeLow.setSelected(true);
        incomeMedium.setSelected(true);
        incomeHigh.setSelected(true);
        contextNews.setSelected(true);
        contextShopping.setSelected(true);
        contextMedia.setSelected(true);
        contextBlog.setSelected(true);
        contextHobbies.setSelected(true);
        contextTravel.setSelected(true);

        reset.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    resetAll();
                });
        checkAll.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    selectAll();
                });
        genderMale.setOnAction(r -> {
            if (genderMale.isSelected()) {
                filterGraph("genderMale", true);
            } else {
                filterGraph("genderMale", false);
            }
        });
        genderFemale.setOnAction(r -> {
            if (genderFemale.isSelected()) {
                filterGraph("genderFemale", true);
            } else {
                filterGraph("genderFemale", false);
            }
        });
        genderOther.setOnAction(r -> {
            if (genderOther.isSelected()) {
                filterGraph("genderOther", true);
            } else {
                filterGraph("genderOther", false);
            }
        });
        ageBelow25.setOnAction(r -> {
            if (ageBelow25.isSelected()) {
                filterGraph("ageBelow25", true);
            } else {
                filterGraph("ageBelow25", false);
            }
        });
        age25to34.setOnAction(r -> {
            if (age25to34.isSelected()) {
                filterGraph("age25to34", true);
            } else {
                filterGraph("age25to34", false);
            }
        });
        age35to44.setOnAction(r -> {
            if (age35to44.isSelected()) {
                filterGraph("age35to44", true);
            } else {
                filterGraph("age35to44", false);
            }
        });
        age45to54.setOnAction(r -> {
            if (age45to54.isSelected()) {
                filterGraph("age45to54", true);
            } else {
                filterGraph("age45to54", false);
            }
        });
        ageAbove54.setOnAction(r -> {
            if (ageAbove54.isSelected()) {
                filterGraph("ageAbove54", true);
            } else {
                filterGraph("ageAbove54", false);
            }
        });
        incomeLow.setOnAction(r -> {
            if (incomeLow.isSelected()) {
                filterGraph("incomeLow", true);
            } else {
                filterGraph("incomeLow", false);
            }
        });
        incomeMedium.setOnAction(r -> {
            if (incomeMedium.isSelected()) {
                filterGraph("incomeMedium", true);
            } else {
                filterGraph("incomeMedium", false);
            }
        });
        incomeHigh.setOnAction(r -> {
            if (incomeHigh.isSelected()) {
                filterGraph("incomeHigh", true);
            } else {
                filterGraph("incomeHigh", false);
            }
        });
        contextNews.setOnAction(r -> {
            if (contextNews.isSelected()) {
                filterGraph("contextNews", true);
            } else {
                filterGraph("contextNews", false);
            }
        });
        contextShopping.setOnAction(r -> {
            if (contextShopping.isSelected()) {
                filterGraph("contextShopping", true);
            } else {
                filterGraph("contextShopping", false);
            }
        });
        contextMedia.setOnAction(r -> {
            if (contextMedia.isSelected()) {
                filterGraph("contextMedia", true);
            } else {
                filterGraph("contextMedia", false);
            }
        });
        contextBlog.setOnAction(r -> {
            if (contextBlog.isSelected()) {
                filterGraph("contextBlog", true);
            } else {
                filterGraph("contextBlog", false);
            }
        });
        contextHobbies.setOnAction(r -> {
            if (contextHobbies.isSelected()) {
                filterGraph("contextHobbies", true);
            } else {
                filterGraph("contextHobbies", false);
            }
        });
        contextTravel.setOnAction(r -> {
            if (contextTravel.isSelected()) {
                filterGraph("contextTravel", true);
            } else {
                filterGraph("contextTravel", false);
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
                startMonth.setText(month.getText());
            });
        }
        for (MenuItem month : endMonth.getItems()) {
            month.setOnAction(r -> {
                for (MenuItem endMonth : endMonth.getItems()) {
                    ((CheckMenuItem) endMonth).setSelected(false);
                }
                ((CheckMenuItem) month).setSelected(true);
                endMonth.setText(month.getText());
            });
        }
        darkTheme.setSelected(true);
        mintTheme.setOnAction(event -> {
            mintTheme.setSelected(true);
            lightTheme.setSelected(false);
            darkTheme.setSelected(false);
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/sample/mintTheme.css");
            themes.setText("Mint Theme");
        });
        lightTheme.setOnAction(event -> {
            mintTheme.setSelected(false);
            lightTheme.setSelected(true);
            darkTheme.setSelected(false);
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/sample/lightTheme.css");
            themes.setText("Light Theme");
        });
        darkTheme.setOnAction(event -> {
            mintTheme.setSelected(false);
            lightTheme.setSelected(false);
            darkTheme.setSelected(true);
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/sample/darkTheme.css");
            themes.setText("Dark Theme");
        });
        campaignsTable.setPrefSize(250, 225);
        campaignsTable.setPlaceholder(new Label("No campaigns loaded!"));
        //campaignsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        nameColumn.setPrefWidth(145);
        nameColumn.setCellValueFactory(
                (TableColumn.CellDataFeatures<Campaign, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getName())
        );
        TableColumn<Campaign, String> displayColumn =
                new TableColumn<>("Show");
        displayColumn.setMaxWidth(50);
        displayColumn.setCellValueFactory(
                new PropertyValueFactory<>("displayed")
        );

        TableColumn<Campaign, String> removeColumn =
                new TableColumn<>("Remove");
        removeColumn.setMaxWidth(50);
        removeColumn.setCellValueFactory(
                new PropertyValueFactory<>("remove")
        );
        campaignsTable.getColumns().setAll(nameColumn, displayColumn, removeColumn);

    }

    @FXML
    private void addNewCampaign(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText("In order to load a campaign please input a name and 3 files below.");
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
                    String s = displayFileChooser(e, "Impressions");
                    if (s != null) {
                        impressionsF.setText(s);
                    }
                });
        Button impressionsB = new Button("Browse");
        impressionsB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    String s = displayFileChooser(e, "Impressions");
                    if (s != null) {
                        impressionsF.setText(s);
                    }
                });

        Label clicksL = new Label("Select Click Log");
        TextField clicksF = new TextField();
        clicksF.setPrefWidth(300);
        clicksF.setPromptText("Select Click Log...");
        clicksF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    String s = displayFileChooser(e, "Clicks");
                    if (s != null) {
                        clicksF.setText(s);
                    }
                });
        Button clicksB = new Button("Browse");
        clicksB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    String s = displayFileChooser(e, "Clicks");
                    if (s != null) {
                        clicksF.setText(s);
                    }
                });


        Label serverL = new Label("Select Server Log");
        TextField serverF = new TextField();
        serverF.setPrefWidth(300);
        serverF.setPromptText("Select Server Log...");
        serverF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    String s = displayFileChooser(e, "Server");
                    if (s != null) {
                        serverF.setText(s);
                    }
                });

        Button serverB = new Button("Browse");
        serverB.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    String s = displayFileChooser(e, "Server");
                    if (s != null) {
                        serverF.setText(s);
                    }
                });

        GridPane content = new GridPane();
        content.setPrefSize(400, 200);
        Button clearButton = new Button("Reset");
        clearButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    files.clear();
                    impressionsF.setPromptText("Select Impressions Log...");
                    clicksF.setPromptText("Select Click Log...");
                    serverF.setPromptText("Select Server Log...");
                    campaignNameF.setPromptText("Input Campaign Name...");
                    campaignNameF.setText("");

                    currentClick = null;
                    currentImpressions = null;
                    currentServer = null;
                });

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
        content.add(clearButton, 2, 10);

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
                files.clear();
                Campaign campaign = new Campaign(campaignNameF.getText());
                // add EventHandlers for the Displayed CheckBox button and the Remove button
                campaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e -> {
                            showMetric(currentMetricDisplayed);
                        });
                campaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e -> {
                            removeCampaign();
                        });
                //Adding the new campaign to the Campaigns table
                campaignsLoaded.add(campaign);
                //Adding a new CheckMenuItem for the new campaign
                campaignsTable.getItems().add(campaign);
                //Adding a new Data Model to ScreensController
                DataModel dataModel = null;
                try {
                    dataModel = new CampaignModelDB(campaign.getName(), currentImpressions, currentClick, currentServer);
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
                setMetrics(campaignNameF.getText());
            }
        }
    }

    private String displayFileChooser(MouseEvent e, String logType) {
        Node node = (Node) e.getSource();
        switch (logType) {
            case "Impressions":
                File impressionsFile = checkFile("Impressions", node, "Select Impressions Log");
                if (impressionsFile != null) {
                    if (currentImpressions == null) {
                        currentImpressions = impressionsFile;
                    } else {
                        files.remove(currentImpressions.getName());
                        currentImpressions = impressionsFile;
                    }
                    return impressionsFile.getName();
                }
                return null;
            case "Clicks":
                File clicksFile = checkFile("Clicks", node, "Select Click Log");
                if (clicksFile != null) {
                    if (currentClick == null) {
                        currentClick = clicksFile;
                    } else {
                        files.remove(currentClick.getName());
                        currentClick = clicksFile;
                    }
                    return clicksFile.getName();
                }
                return null;
            case "Server":
                File serverFile = checkFile("Server", node, "Select Server Log");
                if (serverFile != null) {
                    if (currentServer == null) {
                        currentServer = serverFile;
                    } else {
                        files.remove(currentServer.getName());
                        currentServer = serverFile;
                    }
                    return serverFile.getName();
                }
                return null;
        }
        return null;
    }

    private File checkFile(String logType, Node node, String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(node.getScene().getWindow());

        switch (logType) {
            case "Impressions":
                if (file != null) {
                    if (!files.containsKey(file.getName())) {
                        System.out.println("Adding impressions file...");
                        files.put(file.getName(), file);
                        return file;
                    } else {
                        showFileAlert();
                    }
                }
                return null;
            case "Clicks":
                if (file != null) {
                    if (!files.containsKey(file.getName())) {
                        System.out.println("Adding clicks file...");
                        files.put(file.getName(), file);
                        return file;
                    } else {
                        showFileAlert();
                    }
                }
                return null;
            case "Server":
                if (file != null) {
                    if (!files.containsKey(file.getName())) {
                        System.out.println("Adding server file...");
                        files.put(file.getName(), file);
                        return file;
                    } else {
                        showFileAlert();
                    }
                }
                return null;
        }
        return null;
    }

    private void showFileAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Warning");
        GridPane content = new GridPane();
        content.setPrefSize(300, 50);
        Label label = new Label("This file has already been selected!");
        content.add(label, 0, 0);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void removeCampaign() {
        campaignsLoaded.remove(currentCampaign);
        campaignsTable.getItems().remove(currentCampaign);
        myController.removeDataModel(currentCampaign.getName());
        campaignName.getItems().clear();
        x.animatedProperty().setValue(false);
        y.animatedProperty().setValue(false);
        if (campaignsLoaded.size() == 0) {
            campaignName.setText("-");
            impressionsF.setText("-");
            clicksF.setText("-");
            uniquesF.setText("-");
            bouncesF.setText("-");
            conversionsF.setText("-");
            totalCostF.setText("-");
            CTRF.setText("-");
            CPAF.setText("-");
            CPCF.setText("-");
            CPMF.setText("-");
            bounceRateF.setText("-");
        } else {
            campaignName.setText(campaignsLoaded.get(0).getName());
            setMetrics(campaignsLoaded.get(0).getName());
            for (Campaign c : campaignsLoaded) {
                CheckMenuItem checkMenuItem = new CheckMenuItem(c.getName());
                checkMenuItem.setOnAction(t -> {
                    for (MenuItem menuItem : campaignName.getItems()) {
                        if (menuItem instanceof CheckMenuItem) {
                            ((CheckMenuItem) menuItem).setSelected(false);
                        }
                    }
                    checkMenuItem.setSelected(true);
                    setMetrics(checkMenuItem.getText());
                });
                campaignName.getItems().add(checkMenuItem);
            }
        }
        populateMetric(currentMetricDisplayed, currentStep);
    }

    @FXML
    private void applyDateFilter() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Warning");
        GridPane content = new GridPane();
        content.setPrefSize(300, 50);
        Label label;
        if (startDay.getText().equals("") || startMonth.getText().equals("Month") || startYear.getText().equals("")) {
            alert.setHeaderText("Invalid start date!");
            label = new Label("Please select a full start date.");
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (endDay.getText().equals("") || endMonth.getText().equals("Month") || endYear.getText().equals("")) {
            alert.setHeaderText("Invalid end date!");
            label = new Label("Please select a full end date.");
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (!startDay.getText().matches("[0-9]+") || !startYear.getText().matches("[0-9]+")) {
            alert.setHeaderText("Invalid start date!");
            label = new Label("Please input a valid start date.");
            Label l = new Label("Fields can only contain digits.");
            content.add(label, 0, 0);
            content.add(l, 0, 1);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (!endDay.getText().matches("[0-9]+") || !endYear.getText().matches("[0-9]+")) {
            alert.setHeaderText("Invalid end date!");
            label = new Label("Please input a valid end date.");
            Label l = new Label("Fields can only contain digits.");
            content.add(label, 0, 0);
            content.add(l, 0, 1);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (startDay.getText().length() != 2 || startYear.getText().length() != 4) {
            alert.setHeaderText("Invalid start date!");
            label = new Label("Please input a valid start date.");
            Label l = new Label("Number of required digits for day: 2.");
            Label ll = new Label("Number of required digits for year: 4.");
            content.add(label, 0, 0);
            content.add(l, 0, 1);
            content.add(ll, 0, 2);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (endDay.getText().length() != 2 || endYear.getText().length() != 4) {
            alert.setHeaderText("Invalid end date!");
            label = new Label("Please input a valid end date.");
            Label l = new Label("Number of required digits for day: 2.");
            Label ll = new Label("Number of required digits for year: 4.");
            content.add(label, 0, 0);
            content.add(l, 0, 1);
            content.add(ll, 0, 2);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (!isDateValid(startDay.getText() + "-" + convertMonth(startMonth.getText()) + "-" + startYear.getText())) {
            alert.setHeaderText("Invalid start date!");
            label = new Label("The date " + startDay.getText() + "-" + convertMonth(startMonth.getText()) + "-" + startYear.getText() + " is not valid.");
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (!isDateValid(endDay.getText() + "-" + convertMonth(endMonth.getText()) + "-" + endYear.getText())) {
            alert.setHeaderText("Invalid end date!");
            label = new Label("The date " + endDay.getText() + "-" + endMonth.getText() + "-" + endYear.getText() + " is not valid.");
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder sbS = new StringBuilder();
            sbS.append(startDay.getText());
            sbS.append("/");
            sbS.append(convertMonth(startMonth.getText()));
            sbS.append("/");
            sbS.append(startYear.getText());
            StringBuilder sbE = new StringBuilder();
            sbE.append(endDay.getText());
            sbE.append("/");
            sbE.append(convertMonth(endMonth.getText()));
            sbE.append("/");
            sbE.append(endYear.getText());
            for (DataModel dataModel : myController.getDataModelMap().values()) {
                try {
                    Date start = sdf.parse(sbS.toString());
                    Date end = sdf.parse(sbE.toString());
                    dataModel.getFilter().setStartDate(start);
                    dataModel.getFilter().setEndDate(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(sbE.toString());
            populateMetric(currentMetricDisplayed, currentStep);
        }
    }

    private String convertMonth(String month) {
        String m = null;
        switch (month) {
            case "January":
                m = "01";
                return m;
            case "February":
                m = "02";
                return m;
            case "March":
                m = "03";
                return m;
            case "April":
                m = "04";
                return m;
            case "May":
                m = "05";
                return m;
            case "June":
                m = "06";
                return m;
            case "July":
                m = "07";
                return m;
            case "August":
                m = "08";
                return m;
            case "September":
                m = "09";
                return m;
            case "October":
                m = "10";
                return m;
            case "November":
                m = "11";
                return m;
            case "December":
                m = "12";
                return m;
        }
        return null;
    }

    private boolean isDateValid(String date) {
        String DATE_FORMAT = "dd-MM-yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void resetAll() {
        genderMale.setSelected(false);
        genderFemale.setSelected(false);
        genderOther.setSelected(false);
        ageBelow25.setSelected(false);
        age25to34.setSelected(false);
        age35to44.setSelected(false);
        age45to54.setSelected(false);
        ageAbove54.setSelected(false);
        incomeLow.setSelected(false);
        incomeMedium.setSelected(false);
        incomeHigh.setSelected(false);
        contextNews.setSelected(false);
        contextShopping.setSelected(false);
        contextMedia.setSelected(false);
        contextBlog.setSelected(false);
        contextHobbies.setSelected(false);
        contextTravel.setSelected(false);
        for (DataModel dataModel : myController.getDataModelMap().values()) {
            dataModel.getFilter().genderMale = false;
            dataModel.getFilter().genderFemale = false;
            dataModel.getFilter().genderOther = false;
            dataModel.getFilter().ageBelow25 = false;
            dataModel.getFilter().age25to34 = false;
            dataModel.getFilter().age35to44 = false;
            dataModel.getFilter().age45to54 = false;
            dataModel.getFilter().ageAbove54 = false;
            dataModel.getFilter().incomeLow = false;
            dataModel.getFilter().incomeMedium = false;
            dataModel.getFilter().incomeHigh = false;
            dataModel.getFilter().contextNews = false;
            dataModel.getFilter().contextShopping = false;
            dataModel.getFilter().contextMedia = false;
            dataModel.getFilter().contextBlog = false;
            dataModel.getFilter().contextHobbies = false;
            dataModel.getFilter().contextTravel = false;
        }
        populateMetric(currentMetricDisplayed, currentStep);
    }

    private void selectAll() {
        genderMale.setSelected(true);
        genderFemale.setSelected(true);
        genderOther.setSelected(true);
        ageBelow25.setSelected(true);
        age25to34.setSelected(true);
        age35to44.setSelected(true);
        age45to54.setSelected(true);
        ageAbove54.setSelected(true);
        incomeLow.setSelected(true);
        incomeMedium.setSelected(true);
        incomeHigh.setSelected(true);
        contextNews.setSelected(true);
        contextShopping.setSelected(true);
        contextMedia.setSelected(true);
        contextBlog.setSelected(true);
        contextHobbies.setSelected(true);
        contextTravel.setSelected(true);
        for (DataModel dataModel : myController.getDataModelMap().values()) {
            dataModel.getFilter().genderMale = true;
            dataModel.getFilter().genderFemale = true;
            dataModel.getFilter().genderOther = true;
            dataModel.getFilter().ageBelow25 = true;
            dataModel.getFilter().age25to34 = true;
            dataModel.getFilter().age35to44 = true;
            dataModel.getFilter().age45to54 = true;
            dataModel.getFilter().ageAbove54 = true;
            dataModel.getFilter().incomeLow = true;
            dataModel.getFilter().incomeMedium = true;
            dataModel.getFilter().incomeHigh = true;
            dataModel.getFilter().contextNews = true;
            dataModel.getFilter().contextShopping = true;
            dataModel.getFilter().contextMedia = true;
            dataModel.getFilter().contextBlog = true;
            dataModel.getFilter().contextHobbies = true;
            dataModel.getFilter().contextTravel = true;
        }
        populateMetric(currentMetricDisplayed, currentStep);
    }

    private void filterGraph(String name, Boolean filter) {
        System.out.println("Filtering...");
        System.out.println(name);
        switch (name) {
            case "age25to34":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age25to34 = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age25to34 = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "age35to44":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age35to44 = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age35to44 = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "age45to54":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age45to54 = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().age45to54 = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "ageBelow20":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().ageBelow25 = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().ageBelow25 = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "ageAbove54":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().ageAbove54 = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().ageAbove54 = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "genderFemale":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderFemale = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderFemale = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "genderMale":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderMale = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderMale = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "genderOther":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderOther = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().genderOther = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "incomeLow":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeLow = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeLow = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "incomeMedium":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeMedium = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeMedium = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "incomeHigh":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeHigh = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().incomeHigh = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextNews":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextNews = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextNews = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextShopping":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextShopping = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextShopping = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextMedia":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextMedia = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextMedia = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextBlog":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextBlog = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextBlog = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextHobbies":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextHobbies = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextHobbies = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
            case "contextTravel":
                if (filter) {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextTravel = true;
                    }
                } else {
                    for (DataModel dataModel : myController.getDataModelMap().values()) {
                        dataModel.getFilter().contextTravel = false;
                    }
                }
                populateMetric(currentMetricDisplayed, currentStep);
                break;
        }
    }

    /*
    Sets the TextFields with the selected campaign's metrics
     */
    private void setMetrics(String name) {
        campaignName.setText(name);
        DataModel dm = myController.getDataModel(name);
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            impressionsF.setText(String.valueOf(dm.getImpressionsNumber()));
            clicksF.setText(String.valueOf(dm.getClicksNumber()));
            uniquesF.setText(String.valueOf(dm.getUniquesNumber()));
            bouncesF.setText(String.valueOf(dm.getBouncesNumber()));
            conversionsF.setText(String.valueOf(dm.getConversionsNumber()));
            totalCostF.setText(String.valueOf(df.format(dm.getTotalCost())));
            CTRF.setText(String.valueOf(df.format(dm.getCTR())));
            CPAF.setText(String.valueOf(df.format(dm.getCPA())));
            CPCF.setText(String.valueOf(df.format(dm.getCPC())));
            CPMF.setText(String.valueOf(df.format(dm.getCPM())));
            bounceRateF.setText(String.valueOf(df.format(dm.getBounceRate())));
        } catch (SQLException e) {
            reportError(e);
        }
    }

    private void groupByStep() {
        populateMetric(this.currentMetricDisplayed, currentStep);
    }

    private void changeToLineChart() {
        currentChartType = "LineChart";
        x.animatedProperty().setValue(false);
        y.animatedProperty().setValue(false);
        graphType.setText("Line Chart");
        lineChart.setVisible(true);
        pieChart.setVisible(false);
        areaChart.setVisible(false);
        barChart.setVisible(false);
        lineType.setSelected(true);
        barType.setSelected(false);
        areaType.setSelected(false);
        pieType.setSelected(false);
        histogramType.setSelected(false);
    }

    private void changeToBarChart() {
        currentChartType = "BarChart";
        x.animatedProperty().setValue(false);
        y.animatedProperty().setValue(false);
        graphType.setText("Bar Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        areaChart.setVisible(false);
        barChart.setVisible(true);
        barChart.setTitle("Bar Chart");
        lineType.setSelected(false);
        barType.setSelected(true);
        areaType.setSelected(false);
        pieType.setSelected(false);
        histogramType.setSelected(false);
        barChart.setCategoryGap(1);
        barChart.setBarGap(1);
        populateMetric(currentMetricDisplayed, currentStep);
    }

    private void changeToAreaChart() {
        currentChartType = "AreaChart";
        x.animatedProperty().setValue(false);
        y.animatedProperty().setValue(false);
        graphType.setText("Area Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        areaChart.setVisible(true);
        barChart.setVisible(false);
        lineType.setSelected(false);
        barType.setSelected(false);
        areaType.setSelected(true);
        pieType.setSelected(false);
        histogramType.setSelected(false);
    }

    private void changeToPieChart() {
        currentChartType = "PieChart";
        graphType.setText("Pie Chart");
        lineChart.setVisible(false);
        pieChart.setVisible(true);
        areaChart.setVisible(false);
        barChart.setVisible(false);
        lineType.setSelected(false);
        barType.setSelected(false);
        areaType.setSelected(false);
        pieType.setSelected(true);
        histogramType.setSelected(false);
    }

    private void changeToHistogram() {
        currentChartType = "Histogram";
        x.animatedProperty().setValue(false);
        y.animatedProperty().setValue(false);
        graphType.setText("Histogram");
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        areaChart.setVisible(false);
        barChart.setVisible(true);
        lineType.setSelected(false);
        barType.setSelected(false);
        areaType.setSelected(false);
        pieType.setSelected(false);
        histogramType.setSelected(true);
        barChart.getData().clear();
        barChart.setTitle("Click Cost Histogram");
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);
        for (Campaign campaign : this.campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                DataModel dataModel = myController.getDataModel(campaign.getName());
                histogramSeries = new XYChart.Series();
                try {
                    int i = 0;
                    List<Map.Entry<Date, Float>> entries = new ArrayList<>(dataModel.getFullCost(currentStep).entrySet());
                    for (; i < entries.size() - 2; i++) {
                        String key = simpleDateRep(entries.get(i).getKey()) + " - " + simpleDateRep(entries.get(i + 1).getKey());
                        Float value = entries.get(i).getValue();
                        histogramSeries.getData().add(new XYChart.Data(key, value));
                    }
                    histogramSeries.getData().add(new XYChart.Data(simpleDateRep(entries.get(i).getKey()) + " onwards", entries.get(i).getValue()));
//                    for (Map.Entry<Date, Float> entry : dataModel.getFullCost(currentStep).entrySet()) {
//                        Date key = entry.getKey();
//                        Float value = entry.getValue();
//                        histogramSeries.getData().add(new XYChart.Data(String.valueOf(key), value));
//                    }
                    barChart.getData().add(histogramSeries);
                    histogramSeries.setName(dataModel.getName() + "Click Cost Histogram");
                } catch (SQLException e) {
                    reportError(e);
                }
            }
        }
    }

    private static String simpleDateRep(Date d) {
        String[] s = d.toString().split(" ");

        return s[2] + " " + s[1] + " " + s[5];
    }

    private void showMetric(String metric) {
        if (currentChartType != "Histogram") {
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
                case "Uniques":
                    setStyleToMetric("Uniques");
                    currentMetricDisplayed = "Uniques";
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
                case "Total Cost":
                    setStyleToMetric("Total Cost");
                    currentMetricDisplayed = "Total Cost";
                    populateMetric(metric, currentStep);
                    break;
                case "CTR":
                    setStyleToMetric("Impressions");
                    currentMetricDisplayed = "Impressions";
                    populateMetric(metric, currentStep);
                    break;
                case "CPA":
                    setStyleToMetric("CPA");
                    currentMetricDisplayed = "CPA";
                    populateMetric(metric, currentStep);
                    break;
                case "CPC":
                    setStyleToMetric("CPC");
                    currentMetricDisplayed = "CPC";
                    populateMetric(metric, currentStep);
                    break;
                case "CPM":
                    setStyleToMetric("CPM");
                    currentMetricDisplayed = "CPM";
                    populateMetric(metric, currentStep);
                    break;
                case "Bounce Rate":
                    setStyleToMetric("Bounce Rate");
                    currentMetricDisplayed = "Bounce Rate";
                    populateMetric(metric, currentStep);
                    break;
            }
        }
    }

    private void setStyleToMetric(String metric) {
        impressions.setStyle(null);
        clicks.setStyle(null);
        uniques.setStyle(null);
        bounces.setStyle(null);
        conversions.setStyle(null);
        totalCost.setStyle(null);
        CTR.setStyle(null);
        CPA.setStyle(null);
        CPC.setStyle(null);
        CPM.setStyle(null);
        bounceRate.setStyle(null);

        switch (metric) {
            case "Impressions":
                impressions.setStyle("-fx-font-weight: bold;");
                setMetricText();
                impressions.setText("▶ " + impressions.getText());
                break;
            case "Clicks":
                clicks.setStyle("-fx-font-weight: bold;");
                setMetricText();
                clicks.setText("▶ " + clicks.getText());
                break;
            case "Uniques":
                uniques.setStyle("-fx-font-weight: bold;");
                setMetricText();
                uniques.setText("▶ " + uniques.getText());
                break;
            case "Bounces":
                bounces.setStyle("-fx-font-weight: bold;");
                setMetricText();
                bounces.setText("▶ " + bounces.getText());
                break;
            case "Conversions":
                conversions.setStyle("-fx-font-weight: bold;");
                setMetricText();
                conversions.setText("▶ " + conversions.getText());
                break;
            case "Total Cost":
                totalCost.setStyle("-fx-font-weight: bold;");
                setMetricText();
                totalCost.setText("▶ " + totalCost.getText());
                break;
            case "CTR":
                CTR.setStyle("-fx-font-weight: bold;");
                setMetricText();
                CTR.setText("▶ " + CTR.getText());
                break;
            case "CPA":
                CPA.setStyle("-fx-font-weight: bold;");
                setMetricText();
                CPA.setText("▶ " + CPA.getText());
                break;
            case "CPC":
                CPC.setStyle("-fx-font-weight: bold;");
                setMetricText();
                CPC.setText("▶ " + CPC.getText());
                break;
            case "CPM":
                CPM.setStyle("-fx-font-weight: bold;");
                setMetricText();
                CPM.setText("▶ " + CPM.getText());
                break;
            case "Bounce Rate":
                bounceRate.setStyle("-fx-font-weight: bold;");
                setMetricText();
                bounceRate.setText("▶ " + bounceRate.getText());
                break;
        }
    }

    private void setMetricText() {
        impressions.setText("Impressions");
        clicks.setText("Clicks");
        uniques.setText("Uniques");
        bounces.setText("Bounces");
        conversions.setText("Conversions");
        totalCost.setText("Total Cost");
        CTR.setText("CTR");
        CPA.setText("CPA");
        CPC.setText("CPC");
        CPM.setText("CPM");
        bounceRate.setText("Bounce Rate");
    }

    public void populateMetric(String metric, Step step) {

        lineChart.getData().clear();
        barChart.getData().clear();
        areaChart.getData().clear();
        pieChart.getData().clear();

        for (Campaign campaign : this.campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                DataModel dataModel = myController.getDataModel(campaign.getName());
                dataModel.getFilter().step = step;

                campaignMetricLC = new XYChart.Series();
                campaignMetricBC = new XYChart.Series();
                campaignMetricAC = new XYChart.Series();
                campaignMetricPC = FXCollections.observableArrayList();

                campaignMetricLC.setName(dataModel.getName() + " " + metric);
                campaignMetricBC.setName(dataModel.getName() + " " + metric);
                campaignMetricAC.setName(dataModel.getName() + " " + metric);


                try {
                    switch (metric) {
                        case "Impressions":
                            setData_I(sortMap(dataModel.getFullImpressions(step)));
                            break;
                        case "Clicks":
                            setData_I(sortMap(dataModel.getFullClicks(step)));
                            break;
                        case "Uniques":
                            setData_I(sortMap(dataModel.getFullUniques(step)));
                            break;
                        case "Bounces":
                            setData_I(sortMap(dataModel.getFullBounces(step)));
                            break;
                        case "Conversions":
                            setData_I(sortMap(dataModel.getFullConversions(step)));
                            break;
                        case "Total Cost":
                            setData_F(sortMap(dataModel.getFullCost(step)));
                            break;
                        case "CTR":
                            setData_F(sortMap(dataModel.getFullCTR(step)));
                            break;
                        case "CPA":
                            setData_F(sortMap(dataModel.getFullCPA(step)));
                            break;
                        case "CPC":
                            setData_F(sortMap(dataModel.getFullCPC(step)));
                            break;
                        case "CPM":
                            setData_F(sortMap(dataModel.getFullCPM(step)));
                            break;
                        case "Bounce Rate":
                            setData_F(sortMap(dataModel.getFullBounceRate(step)));
                            break;
                    }
                } catch (SQLException e) {
                    reportError(e);
                }
                lineChart.getData().add(campaignMetricLC);
                barChart.getData().add(campaignMetricBC);
                areaChart.getData().add(campaignMetricAC);
                pieChart.setData(campaignMetricPC);
            }
        }
    }

    private void reportError(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Warning");
        GridPane content = new GridPane();
        content.setPrefSize(600, 50);
        Label label = new Label(e.getMessage());
        content.add(label, 0, 0);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void setData_I(List<Map.Entry<Date, Integer>> sortedList) {
        for (Map.Entry<Date, Integer> e : sortedList) {
            campaignMetricLC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricAC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricBC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));

            campaignMetricPC.add(new PieChart.Data(e.getKey().toString(), e.getValue()));
        }
    }

    private void setData_F(List<Map.Entry<Date, Float>> sortedList) {
        for (Map.Entry<Date, Float> e : sortedList) {
            campaignMetricLC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricAC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricBC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));

            campaignMetricPC.add(new PieChart.Data(e.getKey().toString(), e.getValue()));
        }
    }

    private void addData(Date key, Integer value) {
        campaignMetricLC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricBC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricAC.getData().add(new XYChart.Data(String.valueOf(key), value));
        campaignMetricPC.add(new PieChart.Data(String.valueOf(key), value));
    }

    private static <T> List<Map.Entry<Date, T>> sortMap(Map<Date, T> in) {
        List<Date> unsortedDates = new ArrayList<>();
        for (Date d : in.keySet()) {
            unsortedDates.add(d);
        }
        SortedList<Date> sortedDates = new SortedList<Date>(FXCollections.observableList(unsortedDates)).sorted();
        List<Map.Entry<Date, T>> output = new ArrayList<>();
        for (Date d : sortedDates) {
            output.add(Map.entry(d, in.get(d)));
        }
        //ObservableList<XYChart.Data> myout = FXCollections.observableList(output);
        return output;
    }
}