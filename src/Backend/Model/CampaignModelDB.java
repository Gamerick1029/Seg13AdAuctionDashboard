package Backend.Model;

import Backend.DBHelper;
import Backend.Model.Interfaces.DataModelDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CampaignModelDB implements DataModelDB {

    private String campaignName;
    private Connection connection;
    private DBHelper dbHelper;

    public CampaignModelDB() throws SQLException {

        //this.campaignName = campaignName ;

        dbHelper = new DBHelper("seg", "seg13");

        connection = dbHelper.getDefaultConnection();


    }


    /*
            Returns the number of all Impressions of a Campaign
         */
    @Override
    public int getImpressionsNumber(String campaignName) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + campaignName +"_impressions;");
        int i = 0;
        while(rs.next()){
            System.out.println(rs.getString(1));
            i++;
        }
        return i;
    }
    /*
    DEAD FUNCTION
*/
    @Override
    public Map<Date, Integer> getImpressionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName + "_impressions \n" +
                "WHERE   Date >= " + startInterval.toString() +  ";");
        System.out.println("Starting to gather Impressions");
        Map<Date, Integer> tempImpressInterv = new HashMap<>();
        while(rs.next()){
            Date tempDate = rs.getDate(1);
            int impressNo = 1;
            if(tempImpressInterv.keySet().contains(tempDate)) {
                impressNo += tempImpressInterv.get(tempDate);
            }
            tempImpressInterv.put(tempDate,impressNo);

        }
        System.out.println("Data Gathered");
        return tempImpressInterv;
    }

    @Override
    public Map<Date, Integer> getImpressionsByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException {
        return groupI(step, getImpressionsByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Integer> getFullImpressions(String campaignName, long step) throws SQLException {
        System.out.println("Getting Impressions");
        return groupI(step, getImpressionsByInterval(campaignName,MINDATE, MAXDATE, step));
    }

    @Override
    public int getOverallImpressionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Integer> tempImpressInterv = getImpressionsByInterval(campaignName, startInterval, endInterval);
        int overallImpressions = 0;
        for (Date dt : tempImpressInterv.keySet()) {
            overallImpressions ++ ;
        }
        return overallImpressions;
    }

    /*
        Returns the number of all Clicks of a Campaign
     */
    @Override
    public int getClicksNumber(String campaignName) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(ID) FROM " + campaignName + "_clicks;");
        Set<String> clicks = new HashSet<>();
        while(rs.next()){
            clicks.add(rs.getString(2));
        }
        return clicks.size();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getClicksByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Integer> tempClicksInterv = new HashMap<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName +"_clicks \n" +
                "WHERE   Date >= " + startInterval.toString() +  ";");
        System.out.println("Starting to gather Clicks");

        while(rs.next()){
            Date tempDate = rs.getDate(1);
            int clicksNo = 1;
            if (tempClicksInterv.keySet().contains(tempDate)){
                clicksNo += tempClicksInterv.get(tempDate);
            }
            tempClicksInterv.put(tempDate,clicksNo);
        }

        return tempClicksInterv;
    }

    @Override
    public Map<Date, Integer> getClicksByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException{
        return groupI(step, getClicksByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Integer> getFullClicks(String campaignName, long step) throws SQLException {
        return resolveI(groupMap(step, getClicksByInterval(campaignName, MINDATE, MAXDATE, step)));
    }

    @Override
    public int getOverallClicksByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Integer> tempClicksInterv = getClicksByInterval(campaignName, startInterval, endInterval);
        int overallClicks = 0;
        for (Date dt : tempClicksInterv.keySet()) {
            overallClicks += tempClicksInterv.get(dt);
        }
        return overallClicks;
    }

    /*
        Returns the number of all Uniques of a Campaign
     */
    @Override
    public int getUniquesNumber(String campaignName) throws SQLException{
        return getUsersFromClickLog(campaignName).size();
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getUniquesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Set<String>> usersMap = getUsersByInterval(campaignName, startInterval, endInterval);
        Map<Date, Integer> tempUniquesInterv = new HashMap<>();
        Set<Date> dateSet = usersMap.keySet();
        for (Date dt : dateSet) {

            tempUniquesInterv.put(dt, usersMap.get(dt).size());
        }
        return tempUniquesInterv;
    }

    @Override
    public Map<Date, Integer> getUniquesByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException{
        return groupI(step, getUniquesByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Integer> getFullUniques(String campaignName, long step) throws SQLException{
        return groupI(step, getUniquesByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public int getOverallUniquesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Integer> tempUniquesInterv = getUniquesByInterval(campaignName, startInterval, endInterval);
        int overallUniques = 0;
        for (Date dt : tempUniquesInterv.keySet()) {
            overallUniques += tempUniquesInterv.get(dt);
        }
        return overallUniques;
    }

    /*
        Returns the number of all Bounces of a Campaign
     */
    @Override
    public int getBouncesNumber(String campaignName) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName +  "_serverLogs;");
        System.out.println("Starting to gather Impressions");
        int bouncesNumber = 0;
        while(rs.next()){
            if (rs.getInt(4) == 1 || (rs.getDate(3) != null) && ((rs.getDate(3).getTime() - rs.getDate(1).getTime()) <= 120000)) {
                bouncesNumber++;
            }

        }
        return bouncesNumber;
    }

    /*
    Must see if we are using the actual Date that represents the key as there should be somehow
    distinguish the ones with a different exit date but with same entry date. Mapping is made
    only using entry date at the moment.
     */
    @Override
    public Map<Date, Integer> getBouncesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Integer> tempBouncesInterv = new HashMap<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM "  + campaignName +  "_serverLogs \n" +
                "WHERE   Date >= " + startInterval.toString() + "AND Date <= " + endInterval.toString() +  ";");
        while(rs.next()){
            int bounceNo = 0;
            Date entryLogDate = rs.getDate(1);
            Date exitLogDate = rs.getDate(3);
            if (rs.getInt(4) == 1 || (rs.getDate(3) != null) && ((rs.getDate(3).getTime() - rs.getDate(1).getTime()) <= 120000)) {
                if(tempBouncesInterv.keySet().contains(entryLogDate)){
                    bounceNo += tempBouncesInterv.get(entryLogDate);
                }
                bounceNo++;
                tempBouncesInterv.put(entryLogDate,bounceNo);
            }

        }

        return tempBouncesInterv;
    }

    @Override
    public Map<Date, Integer> getBouncesByInterval(String campaignName, Date startInterval, Date endInterval, long step)  throws SQLException{
        return groupI(step, getBouncesByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Integer> getFullBounces(String campaignName, long step) throws SQLException
    {
        return groupI(step, getBouncesByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public int getOverallBouncesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Integer> tempBouncesInterv = getBouncesByInterval(campaignName, startInterval, endInterval);
        int overallBounces = 0;
        for (Date dt : tempBouncesInterv.keySet()) {
            overallBounces += tempBouncesInterv.get(dt);
        }
        return overallBounces;
    }


    /*
        Returns the number of Conversions of a Campaign
    */
    @Override
    public int getConversionsNumber(String campaignName) throws SQLException{
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName + "_serverLogs ;");
        int conversionsNumber = 0;
        while( rs.next() ){

            if(rs.getBoolean(5)){
                conversionsNumber ++;
            }

        }

        return conversionsNumber;
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Integer> getConversionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Integer> tempConversionsInterv = new HashMap<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName + "_serverLogs \n" +
                "WHERE   Date >= " + startInterval.toString() +  ";");
        while(rs.next()){

            int conversionsNumber = 0;
            Date entryLogDate = rs.getDate(1);
            Date exitLogDate = rs.getDate(3);
            if (rs.getBoolean(5)) {
                conversionsNumber++;
                if (tempConversionsInterv.keySet().contains(entryLogDate)) {
                    conversionsNumber += tempConversionsInterv.get(entryLogDate);
                }
                tempConversionsInterv.put(entryLogDate, conversionsNumber);
            }


        }

        return tempConversionsInterv;
    }

    @Override
    public Map<Date, Integer> getConversionsByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException
    {
        return groupI(step, getConversionsByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Integer> getFullConversions(String campaignName, long step) throws SQLException
    {
        return groupI(step, getConversionsByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public int getOverallConversionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Integer> tempConversionsInterv = getConversionsByInterval(campaignName, startInterval, endInterval);
        int overallConversions = 0;
        for (Date dt : tempConversionsInterv.keySet()) {
            overallConversions += tempConversionsInterv.get(dt);
        }
        return overallConversions;
    }

    /*
        Returns the Total Cost of a Campaign
    */
    @Override
    public float getTotalCost(String campaignName) throws SQLException{
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + campaignName +  "_clicks;");
        float totalCost = 0;
        while(rs.next()){
            totalCost = rs.getFloat(3);
        }
        return totalCost;
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCostByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Float> tempClickCostInterv = new HashMap<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName + "_clicks \n" +
                "WHERE   Date >= " + startInterval.toString() + "AND Date <= " + endInterval.toString() +  ";");

        while (rs.next()) {
            float totalCost = 0;
            Date logDate = rs.getDate(1);
            if (logDate.after(startInterval) && logDate.before(endInterval)) {
                totalCost += rs.getFloat(3);
                if (tempClickCostInterv.keySet().contains(logDate)) {
                    totalCost += tempClickCostInterv.get(logDate);
                }
                tempClickCostInterv.put(logDate, totalCost);
            }
        }
        return tempClickCostInterv;
    }

    @Override
    public Map<Date, Float> getCostByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException{
        return groupF(step, getCostByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullCost(String campaignName, long step) throws SQLException{
        return groupF(step, getCostByInterval(campaignName, MINDATE, MAXDATE, step));
    }


    /*
DEAD FUNCTION
 */
    @Override
    public float getOverallCostByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Float> tempClickCostInterv = getCostByInterval(campaignName, startInterval, endInterval);
        float overall = 0;
        for (Date dt : tempClickCostInterv.keySet()) {
            overall += tempClickCostInterv.get(dt);
        }

        return overall;
    }

    /*
        Returns the average number of clicks per impression.
     */
    @Override
    public float getCTR(String campaignName) throws SQLException{
        return (float) getClicksNumber(campaignName) / (float) getImpressionsNumber(campaignName);
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCTRByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Float> ctrByInterval = new HashMap<>();
        Map<Date, Integer> getClicksNumber = getClicksByInterval(campaignName, startInterval, endInterval);
        Map<Date, Integer> getImpressionNumber = getImpressionsByInterval(campaignName ,startInterval, endInterval);

        /*
        NOTE: A CTR is only registered if a click and impression
        happen on the same instant. Is this correct?
         */
        for (Date logDate : getClicksNumber.keySet()) {

            if (getImpressionNumber.keySet().contains(logDate))
                ctrByInterval.put(logDate, ((float) getClicksNumber.get(logDate) / (float) getImpressionNumber.get(logDate)));

        }


        return ctrByInterval;
    }

    @Override
    public Map<Date, Float> getCTRByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException {
        return groupF(step, getCTRByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullCTR(String campaignName, long step) throws SQLException {
        return groupF(step, getCTRByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public float getOverallCTRByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        return ((float) getOverallClicksByInterval(campaignName, startInterval, endInterval) / (float) getOverallImpressionsByInterval(campaignName, startInterval, endInterval));
    }

    /*
        Returns the average amount of money spent on an advertising campaign
         for each acquisition (i.e., conversion).
     */
    @Override
    public float getCPA(String campaignName) throws SQLException {
        return (float) getTotalCost(campaignName) / (float) getConversionsNumber(campaignName);
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPAByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Float> cpaByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(campaignName, startInterval, endInterval);
        Map<Date, Integer> getConversionsNumber = getConversionsByInterval(campaignName, startInterval, endInterval);

        for (Date logDate : getTotalCosts.keySet()) {
            float cpa = 0;
            if (cpaByInterval.containsKey(logDate)) {
                cpa += cpaByInterval.get(logDate);

            }
            if (getTotalCosts.containsKey(logDate) && getConversionsNumber.containsKey(logDate))
                cpa += (float) getTotalCosts.get(logDate) / (float) getConversionsNumber.get(logDate);
            cpaByInterval.put(logDate, (cpa));
        }


        return cpaByInterval;
    }

    @Override
    public Map<Date, Float> getCPAByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException {
        return groupF(step, getCPAByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullCPA(String campaignName, long step) throws SQLException {
        return groupF(step, getCPAByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public float getOverallCPAByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        return ((float) getOverallCostByInterval(campaignName, startInterval, endInterval) / (float) getOverallConversionsByInterval(campaignName, startInterval, endInterval));
    }

    /*
        Returns the average amount of money spent on an advertising campaign for each
         click.
     */
    @Override
    public float getCPC(String campaignName) throws SQLException {
        return (float) getTotalCost(campaignName) / (float) getClicksNumber(campaignName);
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPCByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Float> cpcByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(campaignName, startInterval, endInterval);
        Map<Date, Integer> getClicksNumber = getClicksByInterval(campaignName, startInterval, endInterval);

        for (Date logDate : getTotalCosts.keySet()) {

            cpcByInterval.put(logDate, ((float) getTotalCosts.get(logDate) / (float) getClicksNumber.get(logDate)));

        }


        return cpcByInterval;
    }

    @Override
    public Map<Date, Float> getCPCByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException{
        return groupF(step, getCPCByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullCPC(String campaignName, long step) throws SQLException{
        return groupF(step, getCPCByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public float getOverallCPCByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        return ((float) getOverallCostByInterval(campaignName, startInterval, endInterval) / (float) getOverallClicksByInterval(campaignName,startInterval, endInterval));
    }

    /*
        CPM is calculated by taking the cost of the advertising and dividing by the total
         number of impressions, then multiplying the total by 1000 (CPM = cost/impressions x 1000).
         More commonly, a CPM rate is set by a platform for its advertising space and used
         to calculate the total cost of an ad campaign.
     */
    @Override
    public float getCPM(String campaignName) throws SQLException{
        return (float) (getTotalCost(campaignName) / (float) getImpressionsNumber(campaignName)) * 1000;
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getCPMByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {
        Map<Date, Float> cpmByInterval = new HashMap<>();
        Map<Date, Float> getTotalCosts = getCostByInterval(campaignName,startInterval, endInterval);
        Map<Date, Integer> getImpressionsNumber = getImpressionsByInterval(campaignName,startInterval, endInterval);

        for (Date logDate : getTotalCosts.keySet()) {

            cpmByInterval.put(logDate, ((float) (getTotalCosts.get(logDate) / (float) getImpressionsNumber.get(logDate)) * 1000));

        }


        return cpmByInterval;
    }

    @Override
    public Map<Date, Float> getCPMByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException{
        return groupF(step, getCPMByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullCPM(String campaignName, long step) throws SQLException {
        return groupF(step, getCPMByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public float getOverallCPMByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        return ((float) (getOverallCostByInterval(campaignName, startInterval, endInterval) / (float) getOverallImpressionsByInterval(campaignName, startInterval, endInterval)) * 1000);
    }

    /*
        The average number of bounces per click.
     */
    @Override
    public float getBounceRate(String campaignName) throws SQLException {
        if (getClicksNumber(campaignName) > 0)
            return (float) (getBouncesNumber(campaignName) / (float) getClicksNumber(campaignName));
        else {
            return (float) 0.0;
        }
    }

    /*
    DEAD FUNCTION
     */
    @Override
    public Map<Date, Float> getBounceRateByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{
        Map<Date, Float> bounceRateByInterval = new HashMap<>();
        Map<Date, Integer> getBouncesNumber = getBouncesByInterval(campaignName, startInterval, endInterval);
        Map<Date, Integer> getClicksNumber = getClicksByInterval(campaignName, startInterval, endInterval);

        for (Date logDate : getBouncesNumber.keySet()) {
            int clickNo = 0;
            float bounceRate = 1;
            if (getClicksNumber.containsKey(logDate) && getBouncesNumber.containsKey(logDate)) {
                clickNo += getClicksNumber.get(logDate);
                if (clickNo > 0) {
                    bounceRate = ((float) getBouncesNumber.get(logDate) / clickNo);
                }
                bounceRateByInterval.put(logDate, bounceRate);
            }

        }


        return bounceRateByInterval;
    }

    @Override
    public Map<Date, Float> getBounceRateByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException {
        return groupF(step, getBounceRateByInterval(campaignName, startInterval, endInterval));
    }

    @Override
    public Map<Date, Float> getFullBounceRate(String campaignName, long step) throws SQLException{
        return groupF(step, getBounceRateByInterval(campaignName, MINDATE, MAXDATE, step));
    }

    @Override
    public float getOverallBounceRateByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException{

        return ((float) getOverallBouncesByInterval(campaignName, startInterval, endInterval) / (float) getOverallClicksByInterval(campaignName, startInterval, endInterval));
    }


    /*
        Returns a Set of the Unique Users from the ClickData.
     */
    @Override
    public Set<String> getUsersFromClickLog(String campaignName) throws SQLException{
        Set<String> userSet = new HashSet<String>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT  * FROM " + campaignName + "_users ;");
        System.out.println("Starting to gather Impressions");
        while(rs.next()){
            userSet.add(rs.getString(1));
        }
        return userSet;
    }

    @Override
    public String getName() {
        return campaignName;
    }

    @Override
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public Map<Date, Set<String>> getUsersByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {

        Map<Date, Set<String>> usersInterv = new HashMap<>();
        ResultSet rsDate = connection.createStatement().executeQuery("SELECT * FROM " + campaignName + "_clicks WHERE Date >= "
                + startInterval.toString() + "AND Date <= " + endInterval.toString() +  ";");
        while (rsDate.next()) {
            Date logDate = rsDate.getDate(1);
            Set<String> users = new HashSet<>();
            if (logDate.after(startInterval) && logDate.before(endInterval)) {

                if (usersInterv.keySet().contains(logDate)) {
                    users.addAll(usersInterv.get(logDate));
                }
                users.add(rsDate.getString(2));
                usersInterv.put(logDate, users);
            }

        }
        return usersInterv;
    }

    @Override
    public Map<Date, Set<String>> getUsersByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException {
        return getUsersByInterval(campaignName, startInterval, endInterval);
    }

    @Override
    public Map<Date, Set<String>> getFullUsers(String campaignName, long step) throws SQLException {
        return getUsersByInterval(campaignName, MINDATE, MAXDATE, step);
    }

    @Override
    public Set<String> getOverallUsersByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException {

        Map<Date,Set<String>> tempUsersInterv = getUsersByInterval(campaignName, startInterval,endInterval);
        Set<String> users = new HashSet<>();
        for(Date dt: tempUsersInterv.keySet()){
            users.addAll(tempUsersInterv.get(dt));
        }
        return users;
    }

    /*
    A helper method to group a map by specified step values
    Probably an unnecessarily complicated method. Having specific types might have
    simplified this
     */
    @Override
    public <T> Map<Date, List<T>> groupMapOld(long step, Map<Date, T> map) {
        System.out.println("Starting to group");
        int total = map.size();
        int current = 0;
        Date start = getEarliestDate(map.keySet());
        Date end = new Date(start.getTime() + step);
        Map<Date, List<T>> output = new HashMap<>();
        Map<Integer, List<T>> groups = new HashMap<>();
        while (current < total) {
            System.out.println(current);
            List<T> listable = new ArrayList<>();
            for (Date d : map.keySet()) {
                //Admin decision. Include the early date, exclude the later date
                if (d.before(end) && (d.after(start) || d.equals(start))) {
                    listable.add(map.get(d));
                    current++;
                }
            }
            output.put(start, listable);
            start = new Date(end.getTime());
            end = new Date(end.getTime() + step);
        }

        return output;
    }

    @Override
    public <T> Map<Date, List<T>> groupMap(long step, Map<Date, T> map) {
        System.out.println("Starting to group");
//        int total = map.size();
//        int current = 0;
        Date start = getEarliestDate(map.keySet());
//        Date end = new Date(start.getTime() + step);
        Map<Date, List<T>> output = new HashMap<>();
        Map<Long, List<T>> groups = new HashMap<>();
        for (Date d : map.keySet()) {
            long pos = Math.floorDiv(d.getTime() - start.getTime(), step);
            if (!groups.containsKey(pos)) {
                groups.put(pos, new ArrayList<>());
            }
            groups.get(pos).add(map.get(d));

        }
        for (Long pos : groups.keySet()) {
            output.put(new Date(start.getTime() + (pos * step)), groups.get(pos));
        }

        return output;
    }

    @Override
    public Map<Date, Float> resolveF(Map<Date, List<Float>> in) {
        Map<Date, Float> out = new HashMap<>();
        for (Date d : in.keySet()) {
            float sum = 0f;
            for (Float f : in.get(d)) {
                sum = sum + f;
            }
            out.put(d, sum);
        }
        return out;
    }

    @Override
    public Map<Date, Integer> resolveI(Map<Date, List<Integer>> in) {
        System.out.println("Starting to Resolve");
        Map<Date, Integer> out = new HashMap<>();
        for (Date d : in.keySet()) {
            int sum = 0;
            for (Integer i : in.get(d)) {
                sum += i;
            }
            out.put(d, sum);
        }
        System.out.println("Ending resolve");
        return out;
    }

//    pr

    /*
    Another helper method to get the starting date
     */
    @Override
    public Date getEarliestDate(Set<Date> dates) {
        Date output = MAXDATE;
        for (Date d : dates) {
            if (d.before(output))
                output = d;
        }
        return output;
    }

    /*
    These methods simplify the control chain for this.
     */
    @Override
    public Map<Date, Integer> groupI(long step, Map<Date, Integer> in) {
        return resolveI(groupMap(step, in));
    }

    @Override
    public Map<Date, Float> groupF(long step, Map<Date, Float> in) {
        return resolveF(groupMap(step, in));
    }



}
