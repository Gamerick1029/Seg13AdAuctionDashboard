import Frontend.FileIO.TempImpressionHolder;
import Frontend.FileIO.readCSVs;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.sql.Time;
import java.util.Date;

public class TestMain {

    public static void main(String[] args){
        File clickLog = new File("TestCSVs/2_week_campaign/click_log.csv");
        File impressionLog = new File("TestCSVs/2_month_campaign/impression_log.csv");

        CSVparsePerformanceTests csvppt = new CSVparsePerformanceTests();
        csvppt.readClicks(clickLog);

//        long start = System.nanoTime();
//        readCSVs.readImpressions(impressionLog);
//        System.out.println((System.nanoTime() - start)/1000000000.0);




    }

}
