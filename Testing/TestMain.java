import Frontend.FileIO.readCSVs;

import java.io.File;

public class TestMain {

    public static void main(String[] args){
        File clickLog = new File("TestCSVs/click_log.csv");
        File impressionLog = new File("TestCSVs/impression_log.csv");
        //readCSVs.readClicks(clickLog);
        readCSVs.readImpressions(impressionLog);
    }

}
