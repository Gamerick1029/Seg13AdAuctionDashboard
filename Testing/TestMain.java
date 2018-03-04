import Backend.FileIO.readCSVs;
import Backend.Model.ClickData;
import Backend.Model.ImpressionData;
import Backend.Model.Interfaces.ServerLog;
import Backend.Model.ServerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        File twoWeekClicks = new File("TestCSVs/2_week_campaign/click_log.csv");
        File twoWeekImpressions = new File("TestCSVs/2_week_campaign/impression_log.csv");
        File twoWeekServer = new File("TestCSVs/2_week_campaign/server_log.csv");

        File twoMonthClicks = new File("TestCSVs/2_month_campaign/click_log.csv");
        File twoMonthImpressions = new File("TestCSVs/2_month_campaign/impression_log.csv");
        File twoMonthServer = new File("TestCSVs/2_month_campaign/server_log.csv");

        TestMain testMain = new TestMain();
        testMain.testCampaign(twoWeekClicks, twoWeekImpressions, twoWeekServer, "2 weeks");
//        testMain.testCampaign(twoMonthClicks, twoMonthImpressions, twoMonthServer, "2 months");


    }

    public void testCampaign(File clickLog, File impressionLog, File serverLog, String campaignName) throws FileNotFoundException, ParseException {

        System.out.println("Starting test: " + campaignName);

        long start = System.nanoTime();

        ArrayList<ClickData> clicks = (ArrayList)readCSVs.readClicks(clickLog);
        ArrayList<ImpressionData> impressions = (ArrayList)readCSVs.readImpressions(impressionLog);
        ArrayList<ServerData> serverLogs = (ArrayList)readCSVs.readServerLogs(serverLog);

        System.out.println(clicks.get(0).getID());
        System.out.println(impressions.get(0).getAgeRange());
        System.out.println(serverLogs.get(0).getConverted());

        System.out.println(campaignName + " campaign time to load (seconds): " + (System.nanoTime() - start)/1000000000.0);

    }

}
