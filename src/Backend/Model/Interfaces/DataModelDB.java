package Backend.Model.Interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataModelDB {


    Date MINDATE = new Date(Long.MIN_VALUE);
    Date MAXDATE = new Date(Long.MAX_VALUE);

    /*
            Returns the number of all Impressions of a Campaign
         */
    int getImpressionsNumber(String campaignName) throws SQLException;

    /*
    DEAD FUNCTION
*/
    Map<Date, Integer> getImpressionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Integer> getImpressionsByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Integer> getFullImpressions(String campaignName, long step) throws SQLException;

    int getOverallImpressionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
        Returns the number of all Clicks of a Campaign
     */
    int getClicksNumber(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Integer> getClicksByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Integer> getClicksByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Integer> getFullClicks(String campaignName, long step) throws SQLException;

    int getOverallClicksByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the number of all Uniques of a Campaign
         */
    int getUniquesNumber(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Integer> getUniquesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Integer> getUniquesByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Integer> getFullUniques(String campaignName, long step) throws SQLException;

    int getOverallUniquesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the number of all Bounces of a Campaign
         */
    int getBouncesNumber(String campaignName) throws SQLException;

    /*
        Must see if we are using the actual Date that represents the key as there should be somehow
        distinguish the ones with a different exit date but with same entry date. Mapping is made
        only using entry date at the moment.
         */
    Map<Date, Integer> getBouncesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Integer> getBouncesByInterval(String campaignName, Date startInterval, Date endInterval, long step)  throws SQLException;

    Map<Date, Integer> getFullBounces(String campaignName, long step) throws SQLException;

    int getOverallBouncesByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the number of Conversions of a Campaign
        */
    int getConversionsNumber(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Integer> getConversionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Integer> getConversionsByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Integer> getFullConversions(String campaignName, long step) throws SQLException;

    int getOverallConversionsByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the Total Cost of a Campaign
        */
    float getTotalCost(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getCostByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getCostByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullCost(String campaignName, long step) throws SQLException;

    /*
    DEAD FUNCTION
     */
    float getOverallCostByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the average number of clicks per impression.
         */
    float getCTR(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getCTRByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getCTRByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullCTR(String campaignName, long step) throws SQLException;

    float getOverallCTRByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the average amount of money spent on an advertising campaign
             for each acquisition (i.e., conversion).
         */
    float getCPA(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getCPAByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getCPAByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullCPA(String campaignName, long step) throws SQLException;

    float getOverallCPAByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns the average amount of money spent on an advertising campaign for each
             click.
         */
    float getCPC(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getCPCByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getCPCByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullCPC(String campaignName, long step) throws SQLException;

    float getOverallCPCByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            CPM is calculated by taking the cost of the advertising and dividing by the total
             number of impressions, then multiplying the total by 1000 (CPM = cost/impressions x 1000).
             More commonly, a CPM rate is set by a platform for its advertising space and used
             to calculate the total cost of an ad campaign.
         */
    float getCPM(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getCPMByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getCPMByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullCPM(String campaignName, long step) throws SQLException;

    float getOverallCPMByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            The average number of bounces per click.
         */
    float getBounceRate(String campaignName) throws SQLException;

    /*
        DEAD FUNCTION
         */
    Map<Date, Float> getBounceRateByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Float> getBounceRateByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Float> getFullBounceRate(String campaignName, long step) throws SQLException;

    float getOverallBounceRateByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
            Returns a Set of the Unique Users from the ClickData.
         */
    Set<String> getUsersFromClickLog(String campaignName) throws SQLException;

    String getName();

    void setCampaignName(String campaignName);

    Map<Date, Set<String>> getUsersByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    Map<Date, Set<String>> getUsersByInterval(String campaignName, Date startInterval, Date endInterval, long step) throws SQLException;

    Map<Date, Set<String>> getFullUsers(String campaignName, long step) throws SQLException;

    Set<String> getOverallUsersByInterval(String campaignName, Date startInterval, Date endInterval) throws SQLException;

    /*
        A helper method to group a map by specified step values
        Probably an unnecessarily complicated method. Having specific types might have
        simplified this
         */
    <T> Map<Date, List<T>> groupMapOld(long step, Map<Date, T> map);

    <T> Map<Date, List<T>> groupMap(long step, Map<Date, T> map);

    Map<Date, Float> resolveF(Map<Date, List<Float>> in);

    Map<Date, Integer> resolveI(Map<Date, List<Integer>> in);

    /*
        Another helper method to get the starting date
         */
    Date getEarliestDate(Set<Date> dates);

    /*
        These methods simplify the control chain for this.
         */
    Map<Date, Integer> groupI(long step, Map<Date, Integer> in);

    Map<Date, Float> groupF(long step, Map<Date, Float> in);
}

