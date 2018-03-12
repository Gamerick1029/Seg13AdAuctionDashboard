package Frontend.sample;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class CampaignController implements ScreenInterface {

    private ScreensController myController;
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> lineChart;

    @Override
    public void setScreenParent(ScreensController parent) {
        this.myController = parent;
        myController.setCampaignDataPopulator(new CampaignDataPopulator(x, y, lineChart));
    }

    @FXML
    private void goToProgressBarScreen() {

    }
}