package Frontend.sample;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CampaignDataPopulator {

    private CategoryAxis x;
    private NumberAxis y;
    private javafx.scene.chart.LineChart<?, ?> lineChart;

    public CampaignDataPopulator(CategoryAxis x, NumberAxis y, javafx.scene.chart.LineChart LineChart) {
        this.x = x;
        this.y = y;
        this.lineChart = LineChart;
    }

    public void populateGraph() {

        XYChart.Series campaign1 = new XYChart.Series();
        campaign1.getData().add(new XYChart.Data("1", 23));
        campaign1.getData().add(new XYChart.Data("2", 45));
        campaign1.getData().add(new XYChart.Data("3", 62));
        campaign1.getData().add(new XYChart.Data("4", 11));
        campaign1.getData().add(new XYChart.Data("5", 78));
        campaign1.getData().add(new XYChart.Data("6", 36));
        campaign1.getData().add(new XYChart.Data("7", 27));
        campaign1.getData().add(new XYChart.Data("8", 44));

        XYChart.Series campaign2 = new XYChart.Series();
        campaign2.getData().add(new XYChart.Data("1", 123));
        campaign2.getData().add(new XYChart.Data("2", 145));
        campaign2.getData().add(new XYChart.Data("3", 162));
        campaign2.getData().add(new XYChart.Data("4", 111));
        campaign2.getData().add(new XYChart.Data("5", 178));
        campaign2.getData().add(new XYChart.Data("6", 136));
        campaign2.getData().add(new XYChart.Data("7", 127));
        campaign2.getData().add(new XYChart.Data("8", 144));
        lineChart.getData().addAll(campaign1, campaign2);
    }

}