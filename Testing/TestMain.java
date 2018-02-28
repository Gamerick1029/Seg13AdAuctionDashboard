import Backend.FileIO.readCSVs;
import Backend.Model.ClickData;
import Backend.Model.ImpressionData;
import Backend.Model.Interfaces.ServerLog;

import java.io.File;
import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args){
        File clickLog = new File("TestCSVs/2_week_campaign/click_log.csv");
        File impressionLog = new File("TestCSVs/2_week_campaign/impression_log.csv");
        File serverLog = new File("TestCSVs/2_week_campaign/server_log.csv");

//        CSVparsePerformanceTests csvppt = new CSVparsePerformanceTests();
//        csvppt.readClicks(clickLog);

        long start = System.nanoTime();

        ArrayList<ClickData> clicks = (ArrayList)readCSVs.readClicks(clickLog);
        ArrayList<ImpressionData> impressions = (ArrayList)readCSVs.readImpressions(impressionLog);
        ArrayList<ServerLog> serverLogs = (ArrayList)readCSVs.readServerLogs(serverLog);

        for (int i = 0; i < 10; i++) {
            System.out.println(clicks.get(i));
            System.out.println(impressions.get(i));
            System.out.println(serverLogs.get(i));
        }

        System.out.println();
        System.out.println((System.nanoTime() - start)/1000000000.0);


    }

}
