package Backend.FileIO;

import Backend.DBHelper;
import com.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class ReadCSVsToDB {

    private static final SimpleDateFormat inDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    private static final String DefaultDataBaseIP = "hobbithouse.me";

    public static int clickProgress = 0;
    public static float impressionProgress = 0;
    public static int serverProgress = 0;

    private static String campaignName;
    private static File clickLog;
    private static File impressionLog;
    private static File serverLog;

    private static Connection dbConnection;

    /**
     * Used to create a new campaign in the database by supplying 3 data files and a connection to the desired database
     * @param connection The database connection to pipe data into
     * @param campaignName The name of the campaign to be created
     * @param clickLog The click log file
     * @param impressionLog The impression log file
     * @param serverLog The server log file
     * @throws SQLException If any sql statement is malformed at any stage of the process or there are issues with the connection this will be thrown
     * @throws IOException If there is an issue accessing the files
     */
    public static void makeCampaign(Connection connection, String campaignName, File clickLog, File impressionLog, File serverLog) throws SQLException, IOException {
        dbConnection = connection;
        ReadCSVsToDB.campaignName = campaignName;
        ReadCSVsToDB.clickLog = clickLog;
        ReadCSVsToDB.impressionLog = impressionLog;
        ReadCSVsToDB.serverLog = serverLog;

        long start = System.nanoTime();
        dbConnection.setAutoCommit(false);
        createTables();
        loadFilesToDB();
        dbConnection.setAutoCommit(true);
        System.out.println((System.nanoTime() - start)/1000000000.0);
    }

    private static void createTables() throws IOException, SQLException {
        String fileContents = FileHelpers.readFileToString("sqlScripts/sqlCreateTables.sql", Charset.defaultCharset());
        fileContents = fileContents.replaceAll("\\[CAMPAIGN]", campaignName);
        File temp = File.createTempFile("sqlRegexed", ".sql");
        temp.deleteOnExit();

        //Adds the campaign Name to the table of campaigns
        //TODO: Currently does not discriminate on existing campaigns and will overwrite them. Need to decide what to do about that
        dbConnection.createStatement().execute("INSERT INTO campaignNames VALUES ('" + campaignName +"') ON DUPLICATE KEY UPDATE name=name;");

        FileOutputStream fis = new FileOutputStream(temp);
        fis.write(fileContents.getBytes());
        fis.close();

        ScriptRunner sr = new ScriptRunner(dbConnection, true, true);
        try {
            sr.runScript(new BufferedReader(new FileReader(temp)));
        } catch (IOException e) {
            throw new IOException("Could not access sql Scripts File at sqlScripts/sqlCreateTables.sql");
        }
    }

    private static void loadFilesToDB(){

        try {
            loadImpressionLog();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loadClickLog();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            loadServerLog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadImpressionLog() throws SQLException, ParseException, IOException {
        int lineNum = FileHelpers.countLines(impressionLog);
        System.out.println(lineNum);
        float percentPerLine = 100/lineNum;

        Statement stmt = dbConnection.createStatement();
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

        stmt.execute("LOAD DATA LOCAL INFILE '" + users.getAbsolutePath() + "' INTO TABLE " + userTableName
                        + " FIELDS TERMINATED BY ',';");
        stmt.execute("LOAD DATA LOCAL INFILE '" + impressions.getAbsolutePath() + "' INTO TABLE " + impressionTableName
                + " FIELDS TERMINATED BY ',';");
        clickProgress = 100;
    }

    private static void loadClickLog() throws SQLException{
        String clickTableName = campaignName + DBHelper.clickTableSuffix;
        Statement stmt = dbConnection.createStatement();
        stmt.execute("LOAD DATA LOCAL INFILE '" + clickLog.getAbsolutePath() + "' INTO TABLE " + clickTableName
                + " FIELDS TERMINATED BY ',';");
    }

    private static void loadServerLog() throws SQLException {
        String serverTableName = campaignName + DBHelper.serverLogTableSuffix;
        Statement stmt = dbConnection.createStatement();
        stmt.execute("LOAD DATA LOCAL INFILE '" + serverLog.getAbsolutePath() + "' INTO TABLE " + serverTableName
                + " FIELDS TERMINATED BY ',';");
    }

}
