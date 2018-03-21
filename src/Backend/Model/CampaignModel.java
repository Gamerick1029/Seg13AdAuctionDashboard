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
public class CampaignModel implements DataModel {


    private List<ClickLog> clickData;
    private List<ImpressionLog> impressionData;
    private List<ServerLog> serverData;

    public CampaignModel(File clickFile, File impressionFile, File serverFile) throws Exception {
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
    public Map<Date, Integer> getImpressionsByInterval(Date startInterval, Date endInterval)
    {   Map<Date,Integer> tempImpressInterv = new HashMap<>();
        for(ImpressionLog il: impressionData){
            Date logDate = il.getDate();
            int impressionNo = 0;
            if(logDate.after(startInterval)&&logDate.before(endInterval)){
                impressionNo ++;
                if(tempImpressInterv.keySet().contains(logDate)){
                    impressionNo += tempImpressInterv.get(logDate);
                }

            }
            tempImpressInterv.put(logDate,impressionNo);
        }
        return tempImpressInterv;
    }

    @Override
    public int getOverallImpressionsByInterval(Date startInterval, Date endInterval)
    {   Map<Date,Integer> tempImpressInterv = getImpressionsByInterval(startInterval,endInterval);
        int overallImpressions = 0;
        for(Date dt: tempImpressInterv.keySet()){
            overallImpressions += tempImpressInterv.get(dt);
        }
        return overallImpressions;
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
    public Map<Date, Integer> getClicksByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempClicksInterv = new HashMap<>();
        for(ClickLog cl: clickData){
            int clickNo = 0;
            Date logDate = cl.getDate();
            if(logDate.after(startInterval)&&logDate.before(endInterval)){
                clickNo++;
                if(tempClicksInterv.keySet().contains(logDate)){
                    clickNo += tempClicksInterv.get(logDate);
                }
                tempClicksInterv.put(logDate,clickNo);
            }
        }
        return tempClicksInterv;
    }

    @Override
    public int getOverallClicksByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempClicksInterv = getClicksByInterval(startInterval,endInterval);
        int overallClicks = 0;
        for(Date dt: tempClicksInterv.keySet()){
            overallClicks += tempClicksInterv.get(dt);
        }
        return overallClicks;
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
    public Map<Date, Integer> getUniquesByInterval(Date startInterval, Date endInterval)
    {
        Map<Date, Set<String>> usersMap = getUsersByInterval(startInterval,endInterval);
        Map<Date,Integer> tempUniquesInterv = new HashMap<>();
        Set<Date> dateSet = usersMap.keySet();
        for(Date dt: dateSet){

            tempUniquesInterv.put(dt,usersMap.get(dt).size());
        }
        return tempUniquesInterv;
    }

    @Override
    public int getOverallUniquesByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempUniquesInterv = getUniquesByInterval(startInterval,endInterval);
        int overallUniques = 0;
        for(Date dt: tempUniquesInterv.keySet()){
            overallUniques += tempUniquesInterv.get(dt);
        }
        return overallUniques;
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
    public Map<Date, Integer> getBouncesByInterval(Date startInterval,Date endInterval)
    {
        Map<Date,Integer> tempBouncesInterv = new HashMap<>();
        for(ServerLog sl: serverData){
            int bouncesNumber = 0;
            Date entryLogDate = sl.getEntryDate();
            Date exitLogDate = sl.getEntryDate();
            if((entryLogDate.after(startInterval)&&entryLogDate.before(endInterval)) && (exitLogDate.after(startInterval)&&exitLogDate.before(endInterval)) ){
                if (sl.getPagesViewed() == 1 || (exitLogDate != null) && ((entryLogDate.getTime() - exitLogDate.getTime() <= 120000))) {
                    bouncesNumber++;
                }
                if(tempBouncesInterv.keySet().contains(sl.getEntryDate())) {
                    bouncesNumber += tempBouncesInterv.get(sl.getEntryDate());
                }
            }

            tempBouncesInterv.put(sl.getEntryDate(),bouncesNumber);
        }
        return tempBouncesInterv;
    }

    @Override
    public int getOverallBouncesByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempBouncesInterv = getBouncesByInterval(startInterval,endInterval);
        int overallBounces = 0;
        for(Date dt: tempBouncesInterv.keySet()){
            overallBounces += tempBouncesInterv.get(dt);
        }
        return overallBounces;
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
    public Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempBouncesInterv = new HashMap<>();
        for(ServerLog sl: serverData){
            int conversionsNumber = 0;
            Date entryLogDate = sl.getEntryDate();
            Date exitLogDate = sl.getExitDate();
            if((entryLogDate.after(startInterval)&&entryLogDate.before(endInterval)) && (exitLogDate.after(startInterval)&&exitLogDate.before(endInterval)) ){
                if(sl.getConverted()) {
                    conversionsNumber++;
                    if(tempBouncesInterv.keySet().contains(entryLogDate)) {
                        conversionsNumber += tempBouncesInterv.get(entryLogDate);
                    }
                    tempBouncesInterv.put(sl.getEntryDate(),conversionsNumber);
                }

            }


        }
        return tempBouncesInterv;
    }

    @Override
    public int getOverallConversionsByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Integer> tempConversionsInterv = getConversionsByInterval(startInterval,endInterval);
        int overallConversions = 0;
        for(Date dt: tempConversionsInterv.keySet()){
            overallConversions += tempConversionsInterv.get(dt);
        }
        return overallConversions;
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
    public Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempClickCostInterv = new HashMap<>();
        for(ClickLog cl: clickData){
            float totalCost = 0;
            Date logDate = cl.getDate();
            if(logDate.after(startInterval)&&logDate.before(endInterval)){
                totalCost += cl.getCost();
                if(tempClickCostInterv.keySet().contains(logDate)){
                    totalCost += tempClickCostInterv.get(logDate);
                }
                tempClickCostInterv.put(logDate,totalCost);
            }
        }
        return tempClickCostInterv;
    }


    /*
DEAD FUNCTION
 */
    @Override
    public float getOverallCostByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempClickCostInterv = getCostByInterval(startInterval,endInterval);
        float overall = 0;
        for(Date dt : tempClickCostInterv.keySet()){
            overall += tempClickCostInterv.get(dt);
        }

        return overall;
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
    public Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval)
    {
        Map<Date, Float> ctrByInterval = new HashMap<>();
        Map<Date, Integer> getClicksNumber = getClicksByInterval(startInterval,endInterval);
        Map<Date, Integer> getImpressionNumber = getImpressionsByInterval(startInterval,endInterval);

        for(Date logDate: getClicksNumber.keySet()){

            ctrByInterval.put(logDate,((float) getClicksNumber.get(logDate) / (float) getImpressionNumber.get(logDate)));

        }


        return ctrByInterval;
    }

    @Override
    public float getOverallCTRByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempCTRInterv = getCTRByInterval(startInterval,endInterval);
        float overallCTR = 0;
        for(Date dt: tempCTRInterv.keySet()){
            overallCTR += tempCTRInterv.get(dt);
        }
        return overallCTR;
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
    public Map<Date, Float> getCPAByInterval(Date startInterval,Date endInterval)
    {
        Map<Date, Float> cpaByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(startInterval,endInterval);
        Map<Date, Integer> getConversionsNumber = getConversionsByInterval(startInterval,endInterval);

        for(Date logDate: getTotalCosts.keySet()){

            cpaByInterval.put(logDate,((float) getTotalCosts.get(logDate) / (float) getConversionsNumber.get(logDate)));

        }


        return cpaByInterval;
    }

    @Override
    public float getOverallCPAByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempCPAInterv = getCPAByInterval(startInterval,endInterval);
        float overallCPA = 0;
        for(Date dt: tempCPAInterv.keySet()){
            overallCPA += tempCPAInterv.get(dt);
        }
        return overallCPA;
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
    public Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval)
    {
        Map<Date, Float> cpcByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(startInterval,endInterval);
        Map<Date, Integer> getClicksNumber = getClicksByInterval(startInterval,endInterval);

        for(Date logDate: getTotalCosts.keySet()){

            cpcByInterval.put(logDate,((float) getTotalCosts.get(logDate) / (float) getClicksNumber.get(logDate)));

        }


        return cpcByInterval;
    }

    @Override
    public float getOverallCPCByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempCPCInterv = getCPCByInterval(startInterval,endInterval);
        float overallCPC = 0;
        for(Date dt: tempCPCInterv.keySet()){
            overallCPC += tempCPCInterv.get(dt);
        }
        return overallCPC;
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
    public Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval)
    {
        Map<Date, Float> cpmByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(startInterval,endInterval);
        Map<Date, Integer> getImpressionsNumber = getImpressionsByInterval(startInterval,endInterval);

        for(Date logDate: getTotalCosts.keySet()){

            cpmByInterval.put(logDate,((float) (getTotalCosts.get(logDate) / (float) getImpressionsNumber.get(logDate)) * 1000));

        }


        return cpmByInterval;
    }

    @Override
    public float getOverallCPMByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempCPMInterv = getCPMByInterval(startInterval,endInterval);
        float overallCPM = 0;
        for(Date dt: tempCPMInterv.keySet()){
            overallCPM += tempCPMInterv.get(dt);
        }
        return overallCPM;
    }

    /*
        The average number of bounces per click.
     */
    @Override
    public float getBounceRate() {
        if(getClicksNumber() > 0)
            return (float) (getBouncesNumber() / (float) getClicksNumber());
        else{
            return (float) 0.0;
        }
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval)
    {
        Map<Date, Float> bounceRateByInterval = new HashMap<>();
        Map<Date, Integer> getBouncesNumber = getBouncesByInterval(startInterval,endInterval);
        Map<Date, Integer> getClicksNumber = getClicksByInterval(startInterval,endInterval);

        for(Date logDate: getBouncesNumber.keySet()){
            int clickNo = getClicksNumber.get(logDate);;
            if(clickNo > 0 )
                bounceRateByInterval.put(logDate,((float) getBouncesNumber.get(logDate) / (float) getClicksNumber.get(logDate)));
            else{
                bounceRateByInterval.put(logDate,((float) getBouncesNumber.get(logDate) / (float) 0.0));
            }

        }


        return bounceRateByInterval;
    }

    @Override
    public float getOverallBounceRateByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Float> tempCPMInterv = getBounceRateByInterval(startInterval,endInterval);
        float overallBounceRate = 0;
        for(Date dt: tempCPMInterv.keySet()){
            overallBounceRate += tempCPMInterv.get(dt);
        }
        return overallBounceRate;
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



    public Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval) {

        Map<Date, Set<String>> usersInterv = new HashMap<>();
        for (ClickLog cl : clickData) {
            Date logDate = cl.getDate();
            Set<String> users = new HashSet<>();
            if (logDate.after(startInterval) && logDate.before(endInterval)) {

                if (usersInterv.keySet().contains(logDate)) {
                    users.addAll(usersInterv.get(logDate));
                }
                users.add(cl.getID());
                usersInterv.put(logDate, users);
            }

        }
        return usersInterv;
    }

    @Override
    public Set<String> getOverallUsersRateByInterval(Date startInterval, Date endInterval)
    {
        Map<Date,Set<String>> tempUsersInterv = getUsersByInterval(startInterval,endInterval);
        Set<String> users = new HashSet<>();
        for(Date dt: tempUsersInterv.keySet()){
            users.addAll(tempUsersInterv.get(dt));
        }
        return users;
    }



}
