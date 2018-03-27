package Frontend.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class CampaignDataPopulator {

    private CategoryAxis x;
    private NumberAxis y;
    private javafx.scene.chart.LineChart<?, ?> lineChart;
    private javafx.scene.chart.BarChart<?, ?> barChart;
    private javafx.scene.chart.PieChart pieChart;

    public CampaignDataPopulator(CategoryAxis x, NumberAxis y, javafx.scene.chart.LineChart lineChart, javafx.scene.chart.BarChart barChart, javafx.scene.chart.PieChart pieChart) {
        this.x = x;
        this.y = y;
        this.lineChart = lineChart;
        this.barChart = barChart;
        this.pieChart = pieChart;
    }

    public void populateGraph() {

        //TODO: Get Impressions for loaded campaign and dispay for LineChart, BarChart, PieChart

        XYChart.Series campaign1 = new XYChart.Series();
        campaign1.getData().add(new XYChart.Data("1", 23));
        campaign1.getData().add(new XYChart.Data("2", 45));
        campaign1.getData().add(new XYChart.Data("3", 62));
        campaign1.getData().add(new XYChart.Data("4", 11));
        campaign1.getData().add(new XYChart.Data("5", 78));
        campaign1.getData().add(new XYChart.Data("6", 36));
        campaign1.getData().add(new XYChart.Data("7", 27));
        campaign1.getData().add(new XYChart.Data("8", 44));
        campaign1.setName("Campaign 1");

        lineChart.getData().add(campaign1);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("Austria", 25601.34));
        series1.getData().add(new XYChart.Data("USA", 20148.82));
        series1.getData().add(new XYChart.Data("France", 10000));

        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        barChart.getData().add(series1);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        pieChart.setData(pieChartData);

    }

}