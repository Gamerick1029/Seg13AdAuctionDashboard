import Backend.FileIO.ReadCSVsToDB;
import Backend.FileIO.readCSVs;
import Backend.DBHelper;
import Backend.Model.CampaignModelDBTrimmed;
import Backend.Model.ClickData;
import Backend.Model.ImpressionData;
import Backend.Model.Interfaces.Filter;
import Backend.Model.Interfaces.Step;
import Backend.Model.ServerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TestMain {

    private static final String defClickLog = "2_week_campaign_2\\click_log.csv";
    private static final String defImpressionLog = "2_week_campaign_2\\impression_log.csv";
    private static final String defServerLog = "2_week_campaign_2\\server_log.csv";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

    public static void main(String[] args) throws IOException, ParseException, SQLException {

//        Filter filter = new Filter();


        DBHelper.initConnection(args[0], args[1]);
        DBHelper dbh = new DBHelper();


        CampaignModelDBTrimmed cmdt = new CampaignModelDBTrimmed("JUnit");
        Step step = Step.DAY;

//        System.out.println(cmdt.getImpressionsNumber());
//        System.out.println(cmdt.getClicksNumber());
//        System.out.println(cmdt.getUniquesNumber());
//        System.out.println(cmdt.getBouncesNumber());
//        System.out.println(cmdt.getConversionsNumber());
//        System.out.println(cmdt.getTotalCost());
//        System.out.println(cmdt.getCTR());
//        System.out.println(cmdt.getCPA());
//        System.out.println(cmdt.getCPC());
//        System.out.println(cmdt.getCPM());
//        System.out.println(cmdt.getBounceRate());

//        System.out.println(cmdt.getFullImpressions(step));
//        System.out.println(cmdt.getFullClicks(step));
//        System.out.println(cmdt.getFullUniques(step));
//        System.out.println(cmdt.getFullBounces(step));
//        System.out.println(cmdt.getFullConversions(step));
//        System.out.println(cmdt.getFullCost(step));
//        System.out.println(cmdt.getFullCTR(step));
//        System.out.println(cmdt.getFullCPA(step));
//        System.out.println(cmdt.getFullCPC(step));
//        System.out.println(cmdt.getFullCPM(step));
//        System.out.println(cmdt.getFullBounceRate(step));

        System.out.println(cmdt.getFullUsers(step));


//        System.out.println(cmdt.getCTR());
//        System.out.println(cmdt.getFullCPA(Step.DAY));

//        File JUnitClicks = new File("TestRuns/Backend/Model/TestSamples/click_logMetrics.csv");
//        File JUnitImpressions = new File("TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv");
//        File JUnitServerLogs = new File("TestRuns/Backend/Model/TestSamples/server_logMetrics.csv");
//
//        ReadCSVsToDB.makeCampaign(dbh, "JUnitMetrics", JUnitClicks, JUnitImpressions, JUnitServerLogs);

//        System.out.println(dbh.getCampaigns());
//        System.out.println("Success!");
    }

    public void databaseTesting(Connection connection) throws SQLException {
        Scanner in = new Scanner(System.in);

        System.out.print("User: ");
        String user = in.nextLine();

        System.out.print("Pass: ");
        String pass = in.nextLine();

        //This just loops over building and executing statements with pretty output to System.Out
        while (true){

            System.out.print("SQL statement (Ends with ';'): ");
            String statement = in.nextLine();

            while (statement.charAt(statement.length() - 1) != ';') {
                System.out.print("> ");
                statement = statement + in.nextLine();
            }

            if (statement.equals("exit;")) {break;}

            try{
                //The following lines attempt to execute the statement on the Database. Returns true if there
                //is a result to grab (Usually because of SELECT based statements), false if no output to receive
                //(CREATE, INSERT, etc), and errors out if the statement is not valid SQL
                Statement stmt = connection.createStatement();
                Boolean result = stmt.execute(statement);

                if (result){
                    //We grab the result and store it in a local object
                    ResultSet rs = stmt.getResultSet();

                    //We print out the results in a human readable way to Standard Out
                    prettyPrintResult(rs);

                }
            } catch (SQLException e) {
                //Tells us which bit of our SQL we got wrong
                System.out.println(e.getMessage());
            }

        }
    }

    private void prettyPrintResult(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();


        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
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
