package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class DataFieldPopulator {

    private Campaign currentCampaign;
    private TableView campaigns;
    private List<Campaign> campaignsLoaded;
    private MenuButton menuButton;
    private CheckMenuItem menuItem;
    private TextField impressions;
    private TextField clicks;
    private TextField bounces;
    private TextField conversions;
    private TextField cost;
    private TextField clickRate;
    private TextField costAquisition;
    private TextField costConversion;
    private DataModel dataModel;

    public DataFieldPopulator(Campaign currentCampaign, List<Campaign> campaignsLoaded, TableView campaigns, MenuButton menuButton, CheckMenuItem menuItem, TextField impressions, TextField clicks, TextField bounces, TextField conversions, TextField cost, TextField clickRate, TextField costAquisition, TextField costConversion) {
        this.currentCampaign = currentCampaign;
        this.campaignsLoaded = campaignsLoaded;
        this.campaigns = campaigns;
        this.menuButton = menuButton;
        this.menuItem = menuItem;
        this.impressions = impressions;
        this.clicks = clicks;
        this.bounces = bounces;
        this.conversions = conversions;
        this.cost = cost;
        this.clickRate = clickRate;
        this.costAquisition = costAquisition;
        this.costConversion = costConversion;
    }

    public void populateFields() {

        currentCampaign.setName(dataModel.getName());
        campaignsLoaded.add(currentCampaign);
        campaigns.getItems().add(currentCampaign);
        menuButton.setText(dataModel.getName());
        menuItem.setText(dataModel.getName());
        impressions.setText(String.valueOf(dataModel.getImpressionsNumber()));
        clicks.setText(String.valueOf(dataModel.getClicksNumber()));
        bounces.setText(String.valueOf(dataModel.getBouncesNumber()));
        conversions.setText(String.valueOf(dataModel.getConversionsNumber()));
        cost.setText(String.valueOf(dataModel.getTotalCost()));
        clickRate.setText(String.valueOf(dataModel.getCTR()));
        costAquisition.setText(String.valueOf(dataModel.getCPA()));
        costConversion.setText(String.valueOf(dataModel.getCPC()));
    }

    public void setCurrentDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

}