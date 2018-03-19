package Backend.Model.Interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /*
      Number of Impressions generated during a campaign
    */
    int getImpressionsNumber();

    Map<Date, Integer> getImpressionsByInterval(Date interval);

    int getClicksNumber();

    Map<Date, Integer> getClicksByInterval(Date interval);

    int getUniquesNumber();

    Map<Date, Integer> getUniquesByInterval(Date interval);

    int getBouncesNumber();

    Map<Date, Integer> getBouncesByInterval(Date interval);

    int getConversionsNumber();

    Map<Date, Integer> getConversionsByInterval(Date interval);

    float getTotalCost();

    Map<Date, Float> getCostByInterval(Date interval);

    /*
        The average number of clicks per impression.
    */
    float getCTR();

    Map<Date, Float> getCTRByInterval(Date interval);

    /*
        The average amount of money spent on an advertising campaign.
    */
    float getCPA();

    Map<Date, Float> getCPAByInterval(Date interval);

    /*
        The average amount of money spent on an advertising campaign for each
            click.
    */
    float getCPC();

    Map<Date, Float> getCPCByInterval(Date interval);

    /*
        The average amount of money spent on an advertising
            campaign for every one thousand impressions.
    */
    float getCPM();

    Map<Date, Float> getCPMByInterval(Date interval);

    /*
        The average number of bounces per click.
    */
    float getBounceRate();

    Map<Date, Float> getBounceRateByInterval(Date interval);
    
}
