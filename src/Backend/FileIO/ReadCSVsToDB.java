package Backend.FileIO;

import Backend.DBHelper;
import Backend.ScriptRunner;
import com.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.ParseException;
import java.util.Iterator;

public class ReadCSVsToDB {

    //TODO: Use the file line number to approximate an execution time
    public static int clickProgress = 0;
    public static float impressionProgress = 0;
    public static int serverProgress = 0;

    private static String campaignName;
    private static File clickLog;
    private static File impressionLog;
    private static File serverLog;

    private static DBHelper DBH;

    /**
     * Used to create a new campaign in the database by supplying 3 data files and a connection to the desired database
     * @param campaignName The name of the campaign to be created
     * @param clickLog The click log file
     * @param impressionLog The impression log file
     * @param serverLog The server log file
     * @throws SQLException If any sql statement is malformed at any stage of the process or there are issues with the connection this will be thrown
     * @throws IOException If there is an issue accessing the files
     */
    public static void makeCampaign(DBHelper dbh, String campaignName, File clickLog, File impressionLog, File serverLog) throws SQLException, IOException {
        DBH = dbh;
        ReadCSVsToDB.campaignName = campaignName;
        ReadCSVsToDB.clickLog = clickLog;
        ReadCSVsToDB.impressionLog = impressionLog;
        ReadCSVsToDB.serverLog = serverLog;

        long start = System.nanoTime();
        DBH.getConnection().setAutoCommit(false);
        createTables();
        loadFilesToDB();
        DBH.getConnection().setAutoCommit(true);
        System.out.println("Time to create campaign " + campaignName + ": " + ((System.nanoTime() - start)/1000000000.0) + " seconds");
    }

    private static void createTables() throws IOException, SQLException {

        //Adds the campaign Name to the table of campaigns
        //TODO: Currently does not discriminate on existing campaigns and will overwrite them. Need to decide what to do about that
        if (DBH.getCampaigns().contains(campaignName)) DBH.deleteCampaign(campaignName);
        DBH.getConnection().createStatement().execute("INSERT INTO campaignNames VALUES ('" + campaignName +"') ON DUPLICATE KEY UPDATE name=name;");
        DBH.executeScriptWithVariable(new File("sqlScripts/sqlCreateTables.sql"), "\\[VAR]", campaignName);
    }

    private static void loadFilesToDB() throws IOException, SQLException {

        try {
            loadImpressionLog();
            loadClickLog();
            loadServerLog();
        } catch (SQLException | IOException e) {
            throw e;
        } finally {
            DBH.deleteCampaign(campaignName);
        }

    }

    private static void loadImpressionLog() throws SQLException, IOException {
        int lineNum = FileHelpers.countLines(impressionLog);

        Statement stmt = DBH.getConnection().createStatement();
        String impressionTableName = campaignName + DBHelper.impressionTableSuffix;
        String userTableName = campaignName + DBHelper.userTableSuffix;

        CSVReader reader = new CSVReader(new FileReader(impressionLog));

        Iterator<String[]> lines = reader.iterator();

        //Skips header line
        lines.next();

        File users = File.createTempFile("users", ".csv");
        File impressions = File.createTempFile("impressions", ".csv");
        users.deleteOnExit();
        impressions.deleteOnExit();

        System.out.println();

        BufferedWriter fwUsers = new BufferedWriter(new FileWriter(users));
        BufferedWriter fwImpressions = new BufferedWriter(new FileWriter(impressions));

        long start = System.nanoTime();
        while (lines.hasNext()){

            String[] tokens = lines.next();
            String date = tokens[0];
            String id = tokens[1];
            String gender = tokens[2];
            String ageRange = tokens[3];
            String income = tokens[4];
            String context = tokens[5];
            String impressionCost = tokens[6];

            fwUsers.write(id + "," + gender + "," + ageRange + "," + income + "\n");
            fwImpressions.write(date + "," + id + "," + context + "," + impressionCost + "\n");

        }

        fwUsers.flush();
        fwImpressions.flush();

        stmt.execute("LOAD DATA LOCAL INFILE '" + users.getPath().replace("\\", "\\\\") + "' INTO TABLE " + userTableName
                        + " FIELDS TERMINATED BY ',';");
        stmt.execute("LOAD DATA LOCAL INFILE '" + impressions.getPath().replace("\\", "\\\\") + "' INTO TABLE " + impressionTableName
                + " FIELDS TERMINATED BY ',';");
    }

    private static void loadClickLog() throws SQLException{
        String clickTableName = campaignName + DBHelper.clickTableSuffix;
        Statement stmt = DBH.getConnection().createStatement();
        stmt.execute("LOAD DATA LOCAL INFILE '" + clickLog.getAbsolutePath().replace("\\", "\\\\") + "' INTO TABLE " + clickTableName
                + " FIELDS TERMINATED BY ',';");
    }

    private static void loadServerLog() throws SQLException, IOException {
        String serverTableName = campaignName + DBHelper.serverLogTableSuffix;
        Statement stmt = DBH.getConnection().createStatement();

        CSVReader reader = new CSVReader(new FileReader(serverLog));

        Iterator<String[]> lines = reader.iterator();

        //Skips header line
        lines.next();

        File serv = File.createTempFile("serv", ".csv");
        BufferedWriter fw = new BufferedWriter(new FileWriter(serv));

        while (lines.hasNext()){
            String[] tokens = lines.next();
            String entryDate = tokens[0];
            String id = tokens[1];
            String exitDate = tokens[2];
            String pagesViewed = tokens[3];
            int conversion = tokens[4].equals("Yes") ? 1 : 0;

            fw.write(entryDate + "," + id + "," + exitDate + "," + pagesViewed + "," + conversion + "\n");
        }

        fw.flush();

        stmt.execute("LOAD DATA LOCAL INFILE '" + serv.getAbsolutePath().replace("\\", "\\\\") + "' INTO TABLE " + serverTableName
                + " FIELDS TERMINATED BY ',';");
    }

}
