package Frontend;

import Backend.DBHelper;
import Backend.Model.CampaignModelDB;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.Filter;
import Backend.Model.Interfaces.Step;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    private MenuButton loadExistingCampaign;
    @FXML
    private MenuButton filterDropDown;
    @FXML
    private CheckMenuItem filterOne;
    @FXML
    private Button applyFilters;
    @FXML
    private Button addFilter;
    @FXML
    private Button removeFilter;
    @FXML
    private MenuItem timeSpent;
    @FXML
    private MenuItem numberPages;
    @FXML
    private MenuItem exportPNG;
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private MenuItem print;
    @FXML
    private MenuItem darkThemeOption;
    @FXML
    private MenuItem lightThemeOption;
    @FXML
    private MenuItem mintThemeOption;

    private XYChart.Series campaignMetricLC;
    private XYChart.Series campaignMetricBC;
    private XYChart.Series campaignMetricAC;
    private XYChart.Series histogramSeries;
    private ObservableList<PieChart.Data> campaignMetricPC;

    private Node node;
    private Campaign currentCampaign = new Campaign("");
    private String currentMetricDisplayed = "Impressions";
    private String currentChartType = "LineChart";
    private Step currentStep = Step.DAY;
    private List<Campaign> campaignsLoaded = new ArrayList<>();
    private HashMap<String, File> files = new HashMap<>();
    private HashMap<String, Filter> filters = new HashMap<>();
    private Filter currentFilter;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setDataFieldPopulator(new DataFieldPopulator(currentCampaign, campaignsLoaded, campaignsTable, campaignName, campaignOne, impressionsF, clicksF,
                uniquesF, bouncesF, conversionsF, totalCostF, CTRF, CPAF, CPCF, CPMF, bounceRateF));
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart, barChart, pieChart, areaChart));
        impressions.setStyle("-fx-font-weight: bold;");
        currentFilter = new Filter();
        filters.put("Filter 1", currentFilter);
        currentCampaign.getDisplayed().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    showMetric(currentMetricDisplayed);
                });
        currentCampaign.getRemove().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
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

        dbHelper = new DBHelper();
        try {
            for (String s : dbHelper.getCampaigns()) {
                //if (campaignsLoaded.contains(s))
                MenuItem menuItem = new MenuItem(s);
                menuItem.setOnAction(e -> {
                    addCampaign(menuItem.getText(), false);
                });
                loadExistingCampaign.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                updateCurrentFilter("genderMale", true);
            } else {
                updateCurrentFilter("genderMale", false);
            }
        });
        genderFemale.setOnAction(r -> {
            if (genderFemale.isSelected()) {
                updateCurrentFilter("genderFemale", true);
            } else {
                updateCurrentFilter("genderFemale", false);
            }
        });
        ageBelow25.setOnAction(r -> {
            if (ageBelow25.isSelected()) {
                updateCurrentFilter("ageBelow25", true);
            } else {
                updateCurrentFilter("ageBelow25", false);
            }
        });
        age25to34.setOnAction(r -> {
            if (age25to34.isSelected()) {
                updateCurrentFilter("age25to34", true);
            } else {
                updateCurrentFilter("age25to34", false);
            }
        });
        age35to44.setOnAction(r -> {
            if (age35to44.isSelected()) {
                updateCurrentFilter("age35to44", true);
            } else {
                updateCurrentFilter("age35to44", false);
            }
        });
        age45to54.setOnAction(r -> {
            if (age45to54.isSelected()) {
                updateCurrentFilter("age45to54", true);
            } else {
                updateCurrentFilter("age45to54", false);
            }
        });
        ageAbove54.setOnAction(r -> {
            if (ageAbove54.isSelected()) {
                updateCurrentFilter("ageAbove54", true);
            } else {
                updateCurrentFilter("ageAbove54", false);
            }
        });
        incomeLow.setOnAction(r -> {
            if (incomeLow.isSelected()) {
                updateCurrentFilter("incomeLow", true);
            } else {
                updateCurrentFilter("incomeLow", false);
            }
        });
        incomeMedium.setOnAction(r -> {
            if (incomeMedium.isSelected()) {
                updateCurrentFilter("incomeMedium", true);
            } else {
                updateCurrentFilter("incomeMedium", false);
            }
        });
        incomeHigh.setOnAction(r -> {
            if (incomeHigh.isSelected()) {
                updateCurrentFilter("incomeHigh", true);
            } else {
                updateCurrentFilter("incomeHigh", false);
            }
        });
        contextNews.setOnAction(r -> {
            if (contextNews.isSelected()) {
                updateCurrentFilter("contextNews", true);
            } else {
                updateCurrentFilter("contextNews", false);
            }
        });
        contextShopping.setOnAction(r -> {
            if (contextShopping.isSelected()) {
                updateCurrentFilter("contextShopping", true);
            } else {
                updateCurrentFilter("contextShopping", false);
            }
        });
        contextMedia.setOnAction(r -> {
            if (contextMedia.isSelected()) {
                updateCurrentFilter("contextMedia", true);
            } else {
                updateCurrentFilter("contextMedia", false);
            }
        });
        contextBlog.setOnAction(r -> {
            if (contextBlog.isSelected()) {
                updateCurrentFilter("contextBlog", true);
            } else {
                updateCurrentFilter("contextBlog", false);
            }
        });
        contextHobbies.setOnAction(r -> {
            if (contextHobbies.isSelected()) {
                updateCurrentFilter("contextHobbies", true);
            } else {
                updateCurrentFilter("contextHobbies", false);
            }
        });
        contextTravel.setOnAction(r -> {
            if (contextTravel.isSelected()) {
                updateCurrentFilter("contextTravel", true);
            } else {
                updateCurrentFilter("contextTravel", false);
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

        // Remove alphabetical and symbol input for numerical Fields
        startDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                startDay.setText(newValue.replaceAll("[^\\d]", ""));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                endDay.setText(newValue.replaceAll("[^\\d]", ""));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        startYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                startYear.setText(newValue.replaceAll("[^\\d]", ""));
            }

        });
        endYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                endYear.setText(newValue.replaceAll("[^\\d]", ""));
            }

        });


        // Remove alphabetical and symbol input for numerical Fields
        startDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2 || newValue == "00") {
                startDay.setText("01");
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2 || newValue == "00") {
                endDay.setText("01");
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        startYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                startYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        endYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                endYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        startYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) > Calendar.getInstance().get(Calendar.YEAR)) {
                startYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        endYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) > Calendar.getInstance().get(Calendar.YEAR)) {
                endYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        startDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) > 31) {
                startDay.setText("01");
            }
        });
        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) > 31) {
                endDay.setText("01");
            }
        });
        startDay.textProperty().addListener((observable, oldValue, newValue) -> {

            if (Integer.valueOf(newValue) > 30 && (startMonth.textProperty().getValue() == "April" || startMonth.textProperty().getValue() == "June" || startMonth.textProperty().getValue() == "September" || startMonth.textProperty().getValue() == "November")) {
                startDay.setText("30");
            } else if (Integer.valueOf(startYear.getText()) % 4 == 0 && Integer.valueOf(newValue) > 29 && (startMonth.textProperty().getValue() == "February")) {
                startDay.setText("29");
            } else if (Integer.valueOf(startYear.getText()) % 4 != 0 && Integer.valueOf(newValue) > 28 && (startMonth.textProperty().getValue() == "February")) {
                startDay.setText("28");
            }

        });

        endDay.textProperty().addListener((observable, oldValue, newValue) -> {

            if (Integer.valueOf(newValue) > 30 && (endMonth.textProperty().getValue() == "April" || endMonth.textProperty().getValue() == "June" || endMonth.textProperty().getValue() == "September" || endMonth.textProperty().getValue() == "November")) {
                endDay.setText("30");
            } else if (Integer.valueOf(endYear.getText()) % 4 == 0 && Integer.valueOf(newValue) > 29 && (endMonth.textProperty().getValue() == "February")) {
                endDay.setText("29");
            } else if (Integer.valueOf(endYear.getText()) % 4 != 0 && Integer.valueOf(newValue) > 28 && (endMonth.textProperty().getValue() == "February")) {
                endDay.setText("28");
            }

        });

        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) > 31) {
                endDay.setText("30");
            }
        });

        startDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("00")) {
                startDay.setText("01");
            }

        });
        endDay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("00")) {
                endDay.setText("01");
            }
        });

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

        mintThemeOption.setOnAction(event -> {
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/mintTheme.css");
        });
        lightThemeOption.setOnAction(event -> {
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/lightTheme.css");
        });
        darkThemeOption.setOnAction(event -> {
            Main.scene.getStylesheets().clear();
            Main.scene.getStylesheets().add("Frontend/darkTheme.css");
        });

        timeSpent.setOnAction(event -> {
            chooseTimeSpent("");
        });
        numberPages.setOnAction(event -> {
            chooseNumberOfPages("");
        });
        exportPNG.setOnAction(event -> {
            exportToPNG(event);
        });
        print.setOnAction(event -> {
            printPDF();
        });

        myMenuBar.setOnMouseEntered(event -> {
            setScene(event);
        });
        filters.put(filterOne.getText(), new Filter());
        filterOne.setOnAction(t -> {
            changeFilter(filterOne);
        });

        filterDropDown.setText(filterOne.getText());
        applyFilters.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    applyFilters();
                });
        removeFilter.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (filterDropDown.getItems().size() > 1) {
                        removeFilterFromMap(filterDropDown.getText());
                    }
                });
        addFilter.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    addNewFilter();
                });

        campaignsTable.setPrefSize(250, 225);
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

    private void applyFilters() {
        populateMetric(currentMetricDisplayed, currentStep);
        setMetrics(campaignName.getText());
    }

    private void addNewFilter() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Add New Filter");
        alert.setHeaderText("");
        GridPane content = new GridPane();
        content.setPrefSize(200, 50);
        Label label = new Label("Input Filter Name:");
        TextField textField = new TextField();
        textField.setPromptText("Filter Name...");
        content.add(label, 0, 0);
        content.add(textField, 0, 1);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            String s;
            if (Objects.equals(textField.getText(), "")) {
                s = String.valueOf(new Date().getTime());
            } else {

                s = "Filter " + textField.getText();
                addFilterToMap(s);
                CheckMenuItem newFilter = new CheckMenuItem(s);
                newFilter.setOnAction(t -> {
                    changeFilter(newFilter);
                });


                filterDropDown.setText(newFilter.getText());
                currentFilter = filters.get(newFilter.getText());
                setFilter(newFilter.getText());
                filterDropDown.getItems().add(newFilter);

            }
        }
    }

    private void changeFilter(CheckMenuItem newFilter) {
        for (MenuItem menuItem : filterDropDown.getItems()) {
            ((CheckMenuItem) menuItem).setSelected(false);
        }
        newFilter.setSelected(true);
        filterDropDown.setText(newFilter.getText());
        currentFilter = filters.get(newFilter.getText());
        setFilter(newFilter.getText());
    }

    private void addFilterToMap(String filterName) {
        Filter filter = new Filter();
        filters.put(filterName, filter);
    }

    private void removeFilterFromMap(String s) {
        filters.remove(s);
        filterDropDown.getItems().clear();
        for (String filterName : filters.keySet()) {
            CheckMenuItem cmi = new CheckMenuItem(filterName);
            cmi.setOnAction(a -> {
                changeFilter(cmi);
            });
            filterDropDown.getItems().add(cmi);
            filterDropDown.setText(cmi.getText());
        }
    }

    private void setFilter(String f) {
        Filter filter = filters.get(f);
        genderMale.setSelected(filter.genderMale);
        genderFemale.setSelected(filter.genderFemale);
        ageBelow25.setSelected(filter.ageBelow25);
        age25to34.setSelected(filter.age25to34);
        age35to44.setSelected(filter.age35to44);
        age45to54.setSelected(filter.age45to54);
        ageAbove54.setSelected(filter.ageAbove54);
        incomeLow.setSelected(filter.incomeLow);
        incomeMedium.setSelected(filter.incomeMedium);
        incomeHigh.setSelected(filter.incomeHigh);
        contextNews.setSelected(filter.contextNews);
        contextShopping.setSelected(filter.contextShopping);
        contextMedia.setSelected(filter.contextMedia);
        contextBlog.setSelected(filter.contextBlog);
        contextHobbies.setSelected(filter.contextHobbies);
        contextTravel.setSelected(filter.contextTravel);

        setMetrics(campaignName.getText());
    }

    private void exportToPNG(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Exporting Chart Image");
        alert.setHeaderText("");
        GridPane content = new GridPane();
        content.setPrefSize(200, 50);
        Label label = new Label("Input Image Name:");
        TextField textField = new TextField();
        textField.setPromptText("Image Name...");
        content.add(label, 0, 0);
        content.add(textField, 0, 1);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            String s;
            if (Objects.equals(textField.getText(), "")) {
                s = String.valueOf(new Date().getTime());
            } else {
                s = "Filter " + textField.getText();

                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Select Directory");
                File defaultDirectory = new File("c://");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(node.getScene().getWindow());

                SnapshotParameters snap = new SnapshotParameters();
                WritableImage writableImage = new WritableImage(100, 100);
                WritableImage snapshot;
                File output = new File(selectedDirectory.getPath() + "/" + s + ".png");

                switch (currentChartType) {
                    case "BarChart":
                        snapshot = barChart.snapshot(new SnapshotParameters(), null);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                            showUpdate();
                        } catch (IOException e) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        break;
                    case "LineChart":
                        snapshot = lineChart.snapshot(new SnapshotParameters(), null);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                            showUpdate();
                        } catch (IOException e) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        break;
                    case "AreaChart":
                        snapshot = areaChart.snapshot(new SnapshotParameters(), null);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                            showUpdate();
                        } catch (IOException e) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        break;
                    case "PieChart":
                        snapshot = pieChart.snapshot(new SnapshotParameters(), null);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                            showUpdate();
                        } catch (IOException e) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        break;
                    case "Histogram":
                        snapshot = barChart.snapshot(new SnapshotParameters(), null);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                            showUpdate();
                        } catch (IOException e) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
                        }
                        break;
                }
            }
        }
    }

    private void setScene(MouseEvent event) {
        this.node = (Node) event.getSource();
    }

    private void showUpdate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Update");
        alert.setHeaderText("");
        GridPane content = new GridPane();
        content.setPrefSize(300, 50);
        Label label = new Label("Image saved!");
        content.add(label, 0, 0);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void printPDF() {

        /*PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            job.showPrintDialog(node.getScene().getWindow());
            job.printPage(node);
            job.endJob();
        }*/

        Writer bw = null;
        String fileName = "file" + new Date().getTime() + ".txt";
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            bw.write("Current campaign: " + campaignName.getText() + "\n");
            bw.write("Impressions: " + impressionsF.getText() + "\n");
            bw.write("Clicks: " + clicksF.getText() + "\n");
            bw.write("Uniques: " + uniquesF.getText() + "\n");
            bw.write("Bounces: " + bouncesF.getText() + "\n");
            bw.write("Conversions: " + conversionsF.getText() + "\n");
            bw.write("Total Cost: " + totalCostF.getText() + "\n");
            bw.write("Click through Rate: " + CTRF.getText() + "\n");
            bw.write("Cost per Acquisition: " + CPAF.getText() + "\n");
            bw.write("Cost per Click: " + CPCF.getText() + "\n");
            bw.write("Click per Thousand Impressions: " + CPMF.getText() + "\n");
            bw.write("Bounce Rate: " + bounceRateF.getText() + "\n");

            FileInputStream textStream;
            textStream = new FileInputStream(fileName);

            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc mydoc = new SimpleDoc(textStream, flavor, null);

            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(new Copies(5));
            //aset.add(MediaSize.A4);
            aset.add(Sides.DUPLEX);

            PrintService[] services = PrintServiceLookup.lookupPrintServices(
                    flavor, aset);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

            if (services.length == 0) {
                System.out.println("No services...");
                if (defaultService == null) {
                    System.out.println("Cannot find printer!");
                } else {
                    System.out.println("Printing...");

                    DocPrintJob job = defaultService.createPrintJob();
                    job.print(mydoc, aset);
                }
            } else {
                PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultService, flavor, aset);

                if (service != null) {
                    DocPrintJob job = service.createPrintJob();
                    job.print(mydoc, null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseTimeSpent(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(s);
        alert.setTitle("Bounce Rate Settings");
        GridPane content = new GridPane();
        content.setPrefSize(300, 50);
        content.add(new Label("Please specify time spent on website: "), 0, 0);
        TextField time = new TextField();
        time.setPromptText("Seconds");
        content.add(time, 0, 1);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            if (time.getText().equals("")) {
                chooseTimeSpent("Please input a time!");
            } else if (!time.getText().matches("[0-9]+")) {
                chooseNumberOfPages("Field can contain digits only!");
            } else {
                Filter.bounceRateByPages = false;
                Filter.timeOnSiteForBounce = Integer.parseInt(time.getText());
            }
        }
    }

    private void chooseNumberOfPages(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(s);
        alert.setTitle("Bounce Rate Settings");
        GridPane content = new GridPane();
        content.setPrefSize(300, 50);
        content.add(new Label("Please specify number of pages: "), 0, 0);
        TextField number = new TextField();
        number.setPromptText("Number of pages");
        content.add(number, 0, 1);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            if (number.getText().equals("")) {
                chooseNumberOfPages("Please input a number!");
            } else if (!number.getText().matches("[0-9]+")) {
                chooseNumberOfPages("Field can contain digits only!");
            } else {
                Filter.bounceRateByPages = true;
                Filter.pagesViewedForBounce = Integer.parseInt(number.getText());
            }
        }
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
                addCampaign(campaignNameF.getText(), true);
            }
        }
    }

    private void addCampaign(String name, Boolean source) {
        Campaign campaign = new Campaign(name);
        campaign.getDisplayed().setSelected(true);
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
        //Source - if a new campaign is being created - True
        // if an existing one is loaded - False
        if (source) {
            DataModel dataModel = null;
            try {
                dataModel = new CampaignModelDB(campaign.getName(), currentImpressions, currentClick, currentServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            myController.addDataModel(campaign.getName(), dataModel);
        } else {
            DataModel dataModel = null;
            try {
                dataModel = new CampaignModelDB(name);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            myController.addDataModel(name, dataModel);
        }
        populateMetric(currentMetricDisplayed, currentStep);
        //Creating a new CheckMenuItem for the new campaign
        CheckMenuItem checkMenuItem = new CheckMenuItem(name);
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
        setMetrics(name);
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Warning");
        GridPane content = new GridPane();
        content.setPrefSize(350, 50);
        Label label = new Label("Are you sure you want to delete this campaign permanently?");
        content.add(label, 0, 0);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            campaignsLoaded.remove(currentCampaign);
            campaignsTable.getItems().remove(currentCampaign);
            myController.removeDataModel(currentCampaign.getName());
            campaignName.getItems().clear();

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
        } else if (Integer.valueOf(startYear.getText()) < 1975) {
            alert.setHeaderText("Invalid start date!");
            label = new Label("Please input a valid start date.");
            Label l = new Label("Year should not be below 1975.");
            content.add(label, 0, 0);
            content.add(l, 0, 1);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        } else if (Integer.valueOf(endYear.getText()) < 1975) {
            alert.setHeaderText("Invalid end date!");
            label = new Label("Please input a valid end date.");
            Label l = new Label("Year should not be below 1975.");
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

        currentFilter.genderMale = false;
        currentFilter.genderFemale = false;
        currentFilter.genderOther = false;
        currentFilter.ageBelow25 = false;
        currentFilter.age25to34 = false;
        currentFilter.age35to44 = false;
        currentFilter.age45to54 = false;
        currentFilter.ageAbove54 = false;
        currentFilter.incomeLow = false;
        currentFilter.incomeMedium = false;
        currentFilter.incomeHigh = false;
        currentFilter.contextNews = false;
        currentFilter.contextShopping = false;
        currentFilter.contextMedia = false;
        currentFilter.contextBlog = false;
        currentFilter.contextHobbies = false;
        currentFilter.contextTravel = false;

        populateMetric(currentMetricDisplayed, currentStep);
    }

    private void selectAll() {
        genderMale.setSelected(true);
        genderFemale.setSelected(true);
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

        currentFilter.genderMale = true;
        currentFilter.genderFemale = true;
        currentFilter.genderOther = true;
        currentFilter.ageBelow25 = true;
        currentFilter.age25to34 = true;
        currentFilter.age35to44 = true;
        currentFilter.age45to54 = true;
        currentFilter.ageAbove54 = true;
        currentFilter.incomeLow = true;
        currentFilter.incomeMedium = true;
        currentFilter.incomeHigh = true;
        currentFilter.contextNews = true;
        currentFilter.contextShopping = true;
        currentFilter.contextMedia = true;
        currentFilter.contextBlog = true;
        currentFilter.contextHobbies = true;
        currentFilter.contextTravel = true;

        populateMetric(currentMetricDisplayed, currentStep);
    }

    private void updateCurrentFilter(String name, Boolean filter) {
        System.out.println("Filtering...");
        System.out.println(name);
        switch (name) {
            case "age25to34":
                currentFilter.age25to34 = filter;
                break;
            case "age35to44":
                currentFilter.age35to44 = filter;
                break;
            case "age45to54":
                currentFilter.age45to54 = filter;
                break;
            case "ageBelow20":
                currentFilter.ageBelow25 = filter;
                break;
            case "ageAbove54":
                currentFilter.ageAbove54 = filter;
                break;
            case "genderFemale":
                currentFilter.genderFemale = filter;
                break;
            case "genderMale":
                currentFilter.genderMale = filter;
                break;
            case "genderOther":
                currentFilter.genderOther = filter;
                break;
            case "incomeLow":
                currentFilter.incomeLow = filter;
                break;
            case "incomeMedium":
                currentFilter.incomeMedium = filter;
                break;
            case "incomeHigh":
                currentFilter.incomeHigh = filter;
                break;
            case "contextNews":
                currentFilter.contextNews = filter;
                break;
            case "contextShopping":
                currentFilter.contextShopping = filter;
                break;
            case "contextMedia":
                currentFilter.contextMedia = filter;
                break;
            case "contextBlog":
                currentFilter.contextBlog = filter;
                break;
            case "contextHobbies":
                currentFilter.contextHobbies = filter;
                break;
            case "contextTravel":
                currentFilter.contextTravel = filter;
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
        Task<String> tsk = new Task<>()
        {
            @Override protected String call() throws Exception{
                try

                {
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
                } catch(
                        SQLException e)

                {
                    reportError(e);
                }
                return null;
            }
        };

        impressionsF.setText("Waiting...");
        clicksF.setText("Waiting...");
        uniquesF.setText("Waiting...");
        bouncesF.setText("Waiting...");
        conversionsF.setText("Waiting...");
        totalCostF.setText("Waiting...");
        CTRF.setText("Waiting...");
        CPAF.setText("Waiting...");
        CPCF.setText("Waiting...");
        CPMF.setText("Waiting...");
        bounceRateF.setText("Waiting...");
            Thread th = new Thread(tsk);
            th.setDaemon(true);
            th.start();
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
        pieChart.setLabelsVisible(true);
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
                    setStyleToMetric("CTR");
                    currentMetricDisplayed = "CTR";
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
        e.printStackTrace();
    }

    public void populateMetric(String metric, Step step) {

        lineChart.getData().clear();
        barChart.getData().clear();
        areaChart.getData().clear();
        pieChart.getData().clear();

        x.setAnimated(false);
        y.setAnimated(false);

        for (Campaign campaign : this.campaignsLoaded) {
            if (campaign.getDisplayed().isSelected()) {
                DataModel dataModel = myController.getDataModel(campaign.getName());
                dataModel.getFilter().step = step;

                for(String key : filters.keySet())
                {
                campaignMetricLC = new XYChart.Series();
                campaignMetricBC = new XYChart.Series();
                campaignMetricAC = new XYChart.Series();
                campaignMetricPC = FXCollections.observableArrayList();

                campaignMetricLC.setName(dataModel.getName() + " " + metric + " - " + key);
                campaignMetricBC.setName(dataModel.getName() + " " + metric + " - " + key);
                campaignMetricAC.setName(dataModel.getName() + " " + metric + " - " + key);


                    dataModel.setFilter(filters.get(key));
                    try
                    {
                        switch (metric)
                        {
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
                    } catch (SQLException e)
                    {
                        reportError(e);
                    }

                    lineChart.getData().add(campaignMetricLC);
                    barChart.getData().add(campaignMetricBC);
                    areaChart.getData().add(campaignMetricAC);
                    pieChart.getData().addAll(campaignMetricPC);
                }
            }
        }
    }

    private void setData_I(List<Map.Entry<Date, Integer>> sortedList) {
        for (Map.Entry<Date, Integer> e : sortedList) {
            campaignMetricLC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricAC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricBC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));

            campaignMetricPC.add(new PieChart.Data(simpleDateRep(e.getKey()), e.getValue()));
        }
    }

    private void setData_F(List<Map.Entry<Date, Float>> sortedList) {
        for (Map.Entry<Date, Float> e : sortedList) {
            campaignMetricLC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricAC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));
            campaignMetricBC.getData().add(new XYChart.Data(simpleDateRep(e.getKey()), e.getValue()));

            campaignMetricPC.add(new PieChart.Data(simpleDateRep(e.getKey()), e.getValue()));
        }
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