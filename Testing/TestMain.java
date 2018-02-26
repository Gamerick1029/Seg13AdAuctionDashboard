import Frontend.FileIO.TempImpressionHolder;
import Frontend.FileIO.readCSVs;

import java.io.File;
import java.lang.instrument.Instrumentation;

public class TestMain {

    static Instrumentation instrumentation;

    public static void main(String[] args){
        File clickLog = new File("TestCSVs/click_log.csv");
        File impressionLog = new File("TestCSVs/2_week_campaign/impression_log.csv");
        //readCSVs.readClicks(clickLog);

        long start = System.nanoTime();
        readCSVs.readImpressions(impressionLog);
        System.out.println((System.nanoTime() - start)/1000000000.0);
    }

}
