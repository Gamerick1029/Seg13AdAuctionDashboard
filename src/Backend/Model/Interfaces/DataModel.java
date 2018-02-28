package Backend.Model.Interfaces;

import java.util.ArrayList;

/*
    DataModel holds the data that must be viewed by the UI
 */
public interface DataModel {

    /*
        Returns an ArrayList of all the Click Logs
     */
    ArrayList<ClickLog> getClickData();

    /*
        Returns an ArrayList of all the Impression Logs
    */
    ArrayList<ImpressionLog> getImpressionData();

    /*
        Returns an ArrayList of all the Server Logs
     */
    ArrayList<ServerLog> getServerData();

    /*
      Number of Impressions generated during a campaign
    */
    int getImpressionsNumber();

    int getClicksNumber();

    int getUniquesNumber();

    int getBouncesNumber();

    int getConversionsNumber();

    float getTotalCost();

    /*
        The average number of clicks per impression.
    */
    float getCTR();

    /*
        The average amount of money spent on an advertising campaign.
    */
    float getCPA();

    /*
        The average amount of money spent on an advertising campaign for each
            click.
    */
    float getCPC();

    /*
        The average amount of money spent on an advertising
            campaign for every one thousand impressions.
    */
    float getCPM();

    /*
        The average number of bounces per click.
    */
    float getBounceRate();









}
