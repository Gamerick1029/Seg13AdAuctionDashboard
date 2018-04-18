package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.List;

public class DataFieldPopulator {

    private Campaign currentCampaign;
    private TableView campaigns;
    private List<Campaign> campaignsLoaded;
    private MenuButton menuButton;
    private CheckMenuItem menuItem;
    private TextField impressions;
    private TextField clicks;
    private TextField uniques;
    private TextField bounces;
    private TextField conversions;
    private TextField totalCost;
    private TextField CTR;
    private TextField CPA;
    private TextField CPC;
    private TextField CPM;
    private TextField bounceRate;
    private DataModel dataModel;


    public DataFieldPopulator(Campaign currentCampaign, List<Campaign> campaignsLoaded, TableView campaigns,
                              MenuButton menuButton, CheckMenuItem menuItem, TextField impressions,
                              TextField clicks, TextField uniques, TextField bounces, TextField conversions,
                              TextField totalCost, TextField CTR, TextField CPA, TextField CPC, TextField CPM, TextField bounceRate) {
        this.currentCampaign = currentCampaign;
        this.campaignsLoaded = campaignsLoaded;
        this.campaigns = campaigns;
        this.menuButton = menuButton;
        this.menuItem = menuItem;
        this.impressions = impressions;
        this.clicks = clicks;
        this.uniques = uniques;
        this.bounces = bounces;
        this.conversions = conversions;
        this.totalCost = totalCost;
        this.CTR = CTR;
        this.CPA = CPA;
        this.CPC = CPC;
        this.CPM = CPM;
        this.bounceRate = bounceRate;
    }

    public void populateFields() {

        currentCampaign.setName(dataModel.getName());
        campaignsLoaded.add(currentCampaign);
        campaigns.getItems().add(currentCampaign);
        menuButton.setText(dataModel.getName());
        menuItem.setText(dataModel.getName());
        try {
            impressions.setText(String.valueOf(dataModel.getImpressionsNumber()));
            clicks.setText(String.valueOf(dataModel.getClicksNumber()));
            uniques.setText(String.valueOf(dataModel.getUniquesNumber()));
            bounces.setText(String.valueOf(dataModel.getBouncesNumber()));
            conversions.setText(String.valueOf(dataModel.getConversionsNumber()));
            totalCost.setText(String.valueOf(Math.round(dataModel.getTotalCost())));
            CTR.setText(String.valueOf(Math.round(dataModel.getCTR())));
            CPA.setText(String.valueOf(Math.round(dataModel.getCPA())));
            CPC.setText(String.valueOf(Math.round(dataModel.getCPC())));
            CPM.setText(String.valueOf(Math.round(dataModel.getCPM())));
            bounceRate.setText(String.valueOf(Math.round(dataModel.getBounceRate())));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Warning");
            GridPane content = new GridPane();
            content.setPrefSize(300, 50);
            Label label = new Label(e.getMessage());
            content.add(label, 0, 0);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        }
    }

    public void setCurrentDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

}