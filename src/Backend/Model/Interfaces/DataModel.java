package Backend.Model.Interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
    DataModel holds the data that must be viewed by the UI
    If the UI needs any information from the model there should be a getter
    here
 */
public interface DataModel {

    /*
        Returns an ArrayList of all the Click Logs
     */
    List<ClickLog> getClickData();

    /*
        Returns an ArrayList of all the Impression Logs
    */
    List<ImpressionLog> getImpressionData();

    /*
        Returns an ArrayList of all the Server Logs
     */
    List<ServerLog> getServerData();


    /**
     * Gets the name of the associated campaign
     * @return  the name of the campaign
     */
    String getName();

    /**
     *
     * @return the total number of impressions in the campaign
     */
    int getImpressionsNumber();

    /**
     * Gets all the impression data, grouped within a
     * specified interval
     * @param step the millisecond interval by which to group
     * @return A grouped map of Impression quantity and date
     */
    Map<Date, Integer> getFullImpressions(long step);

    //int getOverallImpressionsByInterval(Date startInterval, Date endInterval);

    /**
     * Get the total number of clicks in the campaign
     * @return Number of clicks
     */
    int getClicksNumber();

    /**
     * get the number of clicks grouped by an interval
     * @param step the millisecond interval by which to group
     * @return A map of number of clicks within a date interval
     */
    Map<Date, Integer> getFullClicks(long step);

    //int getOverallClicksByInterval(Date startInterval, Date endInterval);

    /**
     * get the total number of uniques within the campaign
     * @return total uniques
     */
    int getUniquesNumber();

    /**
     * The number of uniques grouped by a specified interval
     * @param step the millisecond interval by which to group
     * @return a map of uniques by date interval
     */
    Map<Date, Integer> getFullUniques(long step);

//    int getOverallUniquesByInterval(Date startInterval, Date endInterval);

    /**
     * get the total number of bounces across the campaign
     * @return total bounces
     */
    int getBouncesNumber();

//    Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval);
//    Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the number of bounces grouped by the a step interval
     * @param step the millisecond interval by which to group
     * @return a map of the number of bounces in each time interval
     */
    Map<Date, Integer> getFullBounces(long step);

//    int getOverallBouncesByInterval(Date startInterval, Date endInterval);

    /**
     * get the total number of conversions across the campaign
     * @return number of conversions
     */
    int getConversionsNumber();

//    Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval);
//    Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the number of conversions grouped by a step interval
     * @param step the millisecond interval by which to group
     * @return a mapping of conversion quantity and time interval
     */
    Map<Date, Integer> getFullConversions(long step);

//    int getOverallConversionsByInterval(Date startInterval, Date endInterval);

    /**
     * get the total cost of the campaign
     * @return campaign total cost
     */
    float getTotalCost();

//    Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the cost of the campaign grouped by a time step
     * @param step the millisecond interval by which to group
     * @return cost of the campaign within each time step
     */
    Map<Date, Float> getFullCost(long step);

    /*
DEAD FUNCTION
 */
//    float getOverallCostByInterval(Date startInterval, Date endInterval);

    /*
            The average number of clicks per impression.
        */

    /**
     * get the Click Through Rate of the campaign
     * @return click through rate
     */
    float getCTR();

//    Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the Click Through Rate for the campaign grouped
     * by a specified step interval
     * @param step the millisecond interval by which to group
     * @return a mapping of the click through rate with its date interval
     */
    Map<Date, Float> getFullCTR(long step);

//    float getOverallCTRByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising campaign.
        */

    /**
     * The Cost-Per-Acquisition of the whole campaign
     * @return cost-per-acquisition
     */
    float getCPA();

//    Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the cost-per-acquisition grouped by a specified time step
     * @param step the millisecond interval by which to group
     * @return map of date interval with corresponding cost-per-acquisition
     */
    Map<Date, Float> getFullCPA(long step);

//    float getOverallCPAByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising campaign for each
                click.
        */

    /**
     * get the average cost-per-click of the whole campaign
     * @return average cost-per-click
     */
    float getCPC();

//    Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the cost-per-click of time intervals within the campaign
     * @param step the millisecond interval by which to group
     * @return map of date interval with corresponding cost-per-click
     */
    Map<Date, Float> getFullCPC(long step);

//    float getOverallCPCByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising
                campaign for every one thousand impressions.
        */


    /**
     * get cost-per-thousand-impressions fot the whole campaign
     * @return cost-per-thousand-impressions
     */
    float getCPM();

//    Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval, long step);

    /**
     * get the cost-per-thousand-impressions for intervals within the campaign
     * @param step millisecond interval by which to group
     * @return map of cost-per-thousand-impressions with date interval
     */
    Map<Date, Float> getFullCPM(long step);

//    float getOverallCPMByInterval(Date startInterval, Date endInterval);

    /*
            The average number of bounces per click.
        */

    /**
     * returns the average number of bounces per click
     * @return average bounces per click
     */
    float getBounceRate();

//    Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval, long step);

    /**
     * returns a mapping of bounces per click grouped by a specified
     * time interval
     * @param step millisecond time interval by which to group
     * @return map of bounce rates within associated time intervals
     */
    Map<Date, Float> getFullBounceRate(long step);

//    float getOverallBounceRateByInterval(Date startInterval, Date endInterval);

//    Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval);
//    Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval, long step);

    /**
     * return the names of users with registered activity grouped by
     * a specified time interval
     * @param step the millisecond interval by which to group
     * @return map of user sets within associated time intervals
     */
    Map<Date, Set<String>> getFullUsers(long step);

//    Set<String> getOverallUsersRateByInterval(Date startInterval, Date endInterval);

    /**
     * gets the currently applied filter
     * @return currently applied filter
     */
    Filter getFilter();

    /**
     * sets the current filter for the campaign
     * @param f the filter that should be applied to the campaign
     */
    void setFilter(Filter f);
}
