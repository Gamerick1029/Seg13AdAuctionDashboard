package Backend.Model;

import Backend.FileIO.readCSVs;
import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
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
        clickData = readCSVs.readClicks(clickFile);
        impressionData = readCSVs.readImpressions(impressionFile);
        serverData = readCSVs.readServerLogs(serverFile);
    }

    /*
    TODO: Fill these in with proper mathematical functions
     */

    /*
         Return the List of ClickLogs of a Campaign
     */
    @Override
    public List<ClickLog> getClickData() {
        return clickData;
    }

    /*
         Return the List of ImpressionLogs of a Campaign
    */
    @Override
    public List<ImpressionLog> getImpressionData() {
        return impressionData;
    }

    /*
         Return the List of ServerLogs of a Campaign
    */
    @Override
    public List<ServerLog> getServerData() {
        return serverData;
    }

    /*
        Returns the number of all Impressions of a Campaign
     */
    @Override
    public int getImpressionsNumber() {
        return impressionData.size();
    }

    /*
        Returns the number of all Clicks of a Campaign
     */
    @Override
    public int getClicksNumber() {
        return clickData.size();
    }

    /*
        Returns the number of all Uniques of a Campaign
     */
    @Override
    public int getUniquesNumber() {
        return getUsersFromClickLog().size();
    }

    /*
        Returns the number of all Bounces of a Campaign
     */
    @Override
    public int getBouncesNumber() {
        int bouncesNumber = 0;
        for (ServerLog sd : serverData) {
            if (sd.getPagesViewed() == 1 || (sd.getExitDate() != null) || ((sd.getExitDate().getTime() - sd.getEntryDate().getTime() <= 120000))) {
                bouncesNumber++;
            }
        }
        return bouncesNumber;
    }

    /*
        Returns the number of Conversions of a Campaign
    */
    @Override
    public int getConversionsNumber() {
        int conversionsNumber = 0;
        for (ServerLog sd : serverData)
            if (sd.getConverted()) {
                conversionsNumber++;
            }

        return conversionsNumber;
    }

    /*
        Returns the Total Cost of a Campaign
    */
    @Override
    public float getTotalCost() {
        float totalCost = 0;
        for (ClickLog cl : clickData) {
            totalCost += cl.getCost();
        }
        return totalCost;
    }

    /*
        Returns the average number of clicks per impression.
     */
    @Override
    public float getCTR() {
        return (float) (getClicksNumber() / getImpressionsNumber());
    }

    /*
        Returns the average amount of money spent on an advertising campaign
         for each acquisition (i.e., conversion).
     */
    @Override
    public float getCPA() {
        return (float) (getTotalCost() / getConversionsNumber());
    }

    /*
        Returns the average amount of money spent on an advertising campaign for each
         click.
     */
    @Override
    public float getCPC() {
        return (float) (getTotalCost() / getClicksNumber());
    }

    /*
        CPM is calculated by taking the cost of the advertising and dividing by the total
         number of impressions, then multiplying the total by 1000 (CPM = cost/impressions x 1000).
         More commonly, a CPM rate is set by a platform for its advertising space and used
         to calculate the total cost of an ad campaign.
     */
    @Override
    public float getCPM() {
        return (float) (((getTotalCost() / getImpressionsNumber()) * 1000));
    }

    /*
        The average number of bounces per click.
     */
    @Override
    public float getBounceRate() {
        return (float) (getBouncesNumber() / getClicksNumber());
    }

    /*
        Returns a Set of the Unique Users from the ClickData.
     */
    private Set<String> getUsersFromClickLog() {
        Set<String> userSet = new HashSet<String>();
        for (ClickLog cl : clickData) {
                userSet.add(cl.getID());
        }
        return userSet;
    }
}
