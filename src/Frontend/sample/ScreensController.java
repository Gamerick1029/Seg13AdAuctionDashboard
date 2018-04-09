package Frontend.sample;

import Backend.Model.Interfaces.DataModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Yoana on 25/02/2018.
 * This class loads all of the screens when the application is started.
 */
public class ScreensController extends StackPane {

    private DataFieldPopulator dataFieldPopulator;
    private CampaignDataPopulator campaignDataPopulator;
    private HashMap<String, Node> screens = new HashMap<>();
    private Stage stage;
    public DataModel currentModel;
    private HashMap<String, DataModel> dataModelMap;


    public ScreensController(Stage stage) {

        super();

        this.stage = stage;
        stage.setResizable(true);
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ScreenInterface myScreenController = ((ScreenInterface) myLoader.getController());
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //if the screen is loaded
            final DoubleProperty opacity = opacityProperty();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            if (name == "viewDataScreen") {
                stage.setHeight(650);
                stage.setWidth(758);
            }
            if (name == "loadDataScreen") {
                stage.setWidth(758);
                stage.setHeight(630);
            }
            if (name == "campaignScreen") {
                stage.setMaximized(true);
                //TODO: PROPERLY RESIZE APPLICATION!!!
                //stage.getScene().getWindow().setLocation(screenSize.width/2-stage.getScene().getWindow().getSize().width/2, dim.height/2-this.getSize().height/2);
            }

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(200), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);
                                getChildren().add(0, screens.get(name));

                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("No screen loaded! \n");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    public HashMap<String, DataModel> getDataModelMap() {
        return dataModelMap;
    }

    public void setDataModelMap(HashMap<String, DataModel> dataModelMap) {
        this.dataModelMap = dataModelMap;
        dataFieldPopulator.setCurrentDataModel(currentModel);
        campaignDataPopulator.setCurrentDataModel(currentModel);
    }

    public DataModel getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(DataModel currentModel) {
        this.currentModel = currentModel;
    }

    public void addDataModel(String name, DataModel dataModel) {
        dataModelMap.put(name, dataModel);
    }

    public void removeDataModel(String name) {
        dataModelMap.remove(name);
    }

    public DataModel getDataModel(String name) {
        return dataModelMap.get(name);
    }

    public DataFieldPopulator getDataFieldPopulator() {
        return dataFieldPopulator;
    }

    public void setDataFieldPopulator(DataFieldPopulator dataFieldPopulator) {
        this.dataFieldPopulator = dataFieldPopulator;
    }

    public CampaignDataPopulator getCampaignDataPopulator() {
        return campaignDataPopulator;
    }

    public void setCampaignDataPopulator(CampaignDataPopulator campaignDataPopulator) {
        this.campaignDataPopulator = campaignDataPopulator;
    }
}