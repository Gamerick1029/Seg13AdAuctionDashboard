package Backend.Model;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.io.File;
import java.util.List;

/**
 * Created by Benedict on 28/02/2018.
 * This models stores all the data of the campaign as
 * internal lists
 */
public class CampaignModel implements DataModel {

    private List<ClickLog> clickData;
    private List<ImpressionLog> impressionData;
    private List<ServerLog> serverData;

    public CampaignModel(File clickFile, File impressionFile, File serverFile) {
    }


    //Population commands
    public void addClick(ClickLog cl) {
        clickData.add(cl);
    }

    public void addImpression(ImpressionLog il) {
        impressionData.add(il);
    }

    public void addServer(ServerLog sl) {
        serverData.add(sl);
    }


    /*
    TODO: Fill these in with proper mathematical functions
     */
    @Override
    public List<ClickLog> getClickData() {
        return clickData;
    }

    @Override
    public List<ImpressionLog> getImpressionData() {
        return impressionData;
    }

    @Override
    public List<ServerLog> getServerData() {
        return serverData;
    }

    @Override
    public int getImpressionsNumber() {
        return impressionData.size();
    }

    @Override
    public int getClicksNumber() {
        return clickData.size();
    }

    @Override
    public int getUniquesNumber() {
        return 0;
    }

    @Override
    public int getBouncesNumber() {
        return 0;
    }

    @Override
    public int getConversionsNumber() {
        return 0;
    }

    @Override
    public float getTotalCost() {
        return 0;
    }

    @Override
    public float getCTR() {
        return 0;
    }

    @Override
    public float getCPA() {
        return 0;
    }

    @Override
    public float getCPC() {
        return 0;
    }

    @Override
    public float getCPM() {
        return 0;
    }

    @Override
    public float getBounceRate() {
        return 0;
    }
}
