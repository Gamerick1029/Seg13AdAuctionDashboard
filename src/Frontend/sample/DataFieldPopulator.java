package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import javafx.scene.control.TextField;

public class DataFieldPopulator {

    private TextField impressions;
    private TextField clicks;
    private TextField bounces;
    private TextField conversions;
    private TextField cost;
    private TextField clickRate;
    private TextField costAquisition;
    private TextField costConversion;
    private DataModel dataModel;

    public DataFieldPopulator(TextField impressions, TextField clicks, TextField bounces, TextField conversions, TextField cost, TextField clickRate, TextField costAquisition, TextField costConversion) {
        this.impressions = impressions;
        this.clicks = clicks;
        this.bounces = bounces;
        this.conversions = conversions;
        this.cost = cost;
        this.clickRate = clickRate;
        this.costAquisition = costAquisition;
        this.costConversion = costConversion;
    }

    public void populateFields(){
        impressions.setText(String.valueOf(dataModel.getImpressionsNumber()));
        clicks.setText(String.valueOf(dataModel.getClicksNumber()));
        bounces.setText(String.valueOf(dataModel.getBouncesNumber()));
        conversions.setText(String.valueOf(dataModel.getConversionsNumber()));
        cost.setText(String.valueOf(dataModel.getTotalCost()));
        clickRate.setText(String.valueOf(dataModel.getCTR()));
        costAquisition.setText(String.valueOf(dataModel.getCPA()));
        costConversion.setText(String.valueOf(dataModel.getCPC()));
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

}