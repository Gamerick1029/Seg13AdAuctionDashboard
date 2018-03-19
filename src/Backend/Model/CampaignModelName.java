package Backend.Model;

import Backend.FileIO.readCSVs;
import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Benedict on 28/02/2018.
 * This models stores all the data of the campaign as
 * internal lists
 */
public class CampaignModelName implements DataModel {


    private String campaignName;
    private List<ClickLog> clickData;
    private List<ImpressionLog> impressionData;
    private List<ServerLog> serverData;

    public CampaignModelName(String name,File clickFile, File impressionFile, File serverFile) throws Exception {
        try{
            impressionData = readCSVs.readImpressions(impressionFile);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | ParseException e) {

            throw new  Exception("Invalid Impression Log file format!");
        }
        catch (FileNotFoundException e){
            throw new  Exception("Impression Log file not found!");

        }
        try{
            clickData = readCSVs.readClicks(clickFile);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | ParseException e) {

            throw new  Exception("Invalid Clicks Log file format!");
        }
        catch (FileNotFoundException e){
            throw new  Exception("ClickLog file not found!");

        }

        try{
            serverData = readCSVs.readServerLogs(serverFile);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | ParseException e) {

            throw new  Exception("Invalid Server Log file format!");
        }
        catch (FileNotFoundException e){
            throw new  Exception("Server Log file not found!");

        }
        campaignName = name;


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

    @Override
    /*
    DEAD FUNCTION
     */
    public Map<Date, Integer> getImpressionsByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the number of all Clicks of a Campaign
     */
    @Override
    public int getClicksNumber() {
        return clickData.size();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getClicksByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the number of all Uniques of a Campaign
     */
    @Override
    public int getUniquesNumber() {
        return getUsersFromClickLog().size();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getUniquesByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the number of all Bounces of a Campaign
     */
    @Override
    public int getBouncesNumber() {
        int bouncesNumber = 0;
        for (ServerLog sd : serverData) {
            if (sd.getPagesViewed() == 1 || (sd.getExitDate() != null) && ((sd.getExitDate().getTime() - sd.getEntryDate().getTime() <= 120000))) {
                bouncesNumber++;
            }
        }
        return bouncesNumber;
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getBouncesByInterval(Date interval)
    {
        return null;
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
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getConversionsByInterval(Date interval)
    {
        return null;
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
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCostByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the average number of clicks per impression.
     */
    @Override
    public float getCTR() {
        return (float) getClicksNumber() / (float) getImpressionsNumber();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCTRByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the average amount of money spent on an advertising campaign
         for each acquisition (i.e., conversion).
     */
    @Override
    public float getCPA() {
        return (float) getTotalCost() / (float) getConversionsNumber();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPAByInterval(Date interval)
    {
        return null;
    }

    /*
        Returns the average amount of money spent on an advertising campaign for each
         click.
     */
    @Override
    public float getCPC() {
        return (float) getTotalCost() / (float) getClicksNumber();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPCByInterval(Date interval)
    {
        return null;
    }

    /*
        CPM is calculated by taking the cost of the advertising and dividing by the total
         number of impressions, then multiplying the total by 1000 (CPM = cost/impressions x 1000).
         More commonly, a CPM rate is set by a platform for its advertising space and used
         to calculate the total cost of an ad campaign.
     */
    @Override
    public float getCPM() {
        return (float) (getTotalCost() / (float) getImpressionsNumber()) * 1000;
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPMByInterval(Date interval)
    {
        return null;
    }

    /*
        The average number of bounces per click.
     */
    @Override
    public float getBounceRate() {
        if(getClicksNumber() != 0)
            return (float) (getBouncesNumber() / (float) getClicksNumber());
        else{
            return (float) 0.0;
        }
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getBounceRateByInterval(Date interval)
    {
        return null;
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

    private String getCampaignName() {
        return campaignName;
    }

    private void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
