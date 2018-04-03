package Backend.Model.Interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
    DataModel holds the data that must be viewed by the UI
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


    String getName();

    /*
      Number of Impressions generated during a campaign
    */
    int getImpressionsNumber();

    /*
    Retrieve impression data.
    step - Number of milliseconds between each grouping
     */
    Map<Date, Integer> getFullImpressions(long step);

    int getOverallImpressionsByInterval(Date startInterval, Date endInterval);

    int getClicksNumber();

    Map<Date, Integer> getFullClicks(long step);

    int getOverallClicksByInterval(Date startInterval, Date endInterval);

    int getUniquesNumber();

    Map<Date, Integer> getFullUniques(long step);

    int getOverallUniquesByInterval(Date startInterval, Date endInterval);

    int getBouncesNumber();

//    Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval);
//    Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Integer> getFullBounces(long step);

    int getOverallBouncesByInterval(Date startInterval, Date endInterval);

    int getConversionsNumber();

//    Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval);
//    Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Integer> getFullConversions(long step);

    int getOverallConversionsByInterval(Date startInterval, Date endInterval);

    float getTotalCost();

//    Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullCost(long step);

    /*
DEAD FUNCTION
 */
    float getOverallCostByInterval(Date startInterval, Date endInterval);

    /*
            The average number of clicks per impression.
        */
    float getCTR();

//    Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullCTR(long step);

    float getOverallCTRByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising campaign.
        */
    float getCPA();

//    Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullCPA(long step);

    float getOverallCPAByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising campaign for each
                click.
        */
    float getCPC();

//    Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullCPC(long step);

    float getOverallCPCByInterval(Date startInterval, Date endInterval);

    /*
            The average amount of money spent on an advertising
                campaign for every one thousand impressions.
        */
    float getCPM();

//    Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullCPM(long step);

    float getOverallCPMByInterval(Date startInterval, Date endInterval);

    /*
            The average number of bounces per click.
        */
    float getBounceRate();

//    Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval);
//    Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Float> getFullBounceRate(long step);

    float getOverallBounceRateByInterval(Date startInterval, Date endInterval);

//    Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval);
//    Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval, long step);
    Map<Date, Set<String>> getFullUsers(long step);

//    Set<String> getOverallUsersRateByInterval(Date startInterval, Date endInterval);

    /*
    Filter Getter and Setters
     */
    Filter getFilter();
    void setFilter(Filter f);
}
