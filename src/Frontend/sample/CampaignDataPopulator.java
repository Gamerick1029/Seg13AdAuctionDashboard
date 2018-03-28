package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CampaignDataPopulator {

    private CategoryAxis x;
    private NumberAxis y;
    private javafx.scene.chart.LineChart<?, ?> lineChart;
    private javafx.scene.chart.BarChart<?, ?> barChart;
    private javafx.scene.chart.AreaChart<?, ?> areaChart;
    private javafx.scene.chart.PieChart pieChart;
    private DataModel dataModel;

    public CampaignDataPopulator(CategoryAxis x, NumberAxis y, javafx.scene.chart.LineChart lineChart, javafx.scene.chart.BarChart barChart, javafx.scene.chart.PieChart pieChart, javafx.scene.chart.AreaChart areaChart) {
        this.x = x;
        this.y = y;
        this.lineChart = lineChart;
        this.barChart = barChart;
        this.areaChart = areaChart;
        this.pieChart = pieChart;
    }

    public void setDataModel(DataModel dm) {
        this.dataModel = dm;
    }

    public void populateGraph() {

        XYChart.Series campaignImpressionsLC = new XYChart.Series();
        XYChart.Series campaignImpressionsBC = new XYChart.Series();
        XYChart.Series campaignImpressionsAC = new XYChart.Series();
        ObservableList<PieChart.Data> campaignImpressionsPC = FXCollections.observableArrayList();

        campaignImpressionsLC.setName(dataModel.getName() + " Impressions");
        campaignImpressionsBC.setName(dataModel.getName() + " Impressions");
        campaignImpressionsAC.setName(dataModel.getName() + " Impressions");

        //Step by Day
        ObservableList<XYChart.Data> fullData = sortMap(dataModel.getFullImpressions(1000*60*60*24));
        campaignImpressionsLC.setData(fullData);
        campaignImpressionsAC.setData(fullData);
        campaignImpressionsBC.setData(fullData);

        for (Map.Entry<Date, Integer> entry : dataModel.getFullImpressions(1000*60*60*24).entrySet()) {
            Date key = entry.getKey();
            Integer value = entry.getValue();
            campaignImpressionsPC.add(new PieChart.Data(String.valueOf(key), value));
        }
        lineChart.getData().add(campaignImpressionsLC);
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        barChart.getData().add(campaignImpressionsBC);
        areaChart.getData().add(campaignImpressionsAC);
        pieChart.setData(campaignImpressionsPC);
    }

    private static <T> ObservableList<XYChart.Data> sortMap(Map<Date, T> in)
    {
        List<Date> unsortedDates = new ArrayList<>();
        for(Date d : in.keySet())
        {
            unsortedDates.add(d);
        }
        SortedList<Date> sortedDates = new SortedList<Date>(FXCollections.observableList(unsortedDates)).sorted();
        //Need to sort the data here
        List<XYChart.Data> output = new ArrayList<>();
        for(Date d : sortedDates)
        {
            output.add(new XYChart.Data(d.toString(), in.get(d)));
        }

        ObservableList<XYChart.Data> myout = FXCollections.observableList(output);
        return myout;
    }

}