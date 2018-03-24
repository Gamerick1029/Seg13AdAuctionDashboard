package Backend.FileIO;

import Backend.MagicDB;
import com.opencsv.CSVReader;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ReadCSVsToDB {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    private static final String DefaultDataBaseIP = "hobbithouse.me";

    private int clickProgress = 0;
    private int impressionProgress = 0;
    private int serverProgress = 0;

    private String currentIP;
    private String campaignName;
    private File clickLog;
    private File impressionLog;
    private File serverLog;
    private String user;
    private String pass;

    private Connection dbConnection;

    /**
     * Default constructor for the class. Uses the hardcoded domain to connect to the MariaDB instance.
     * @param campaignName
     * @param clickLog
     * @param impressionLog
     * @param serverLog
     */
    public ReadCSVsToDB(String campaignName, File clickLog, File impressionLog, File serverLog, String user, String pass) throws SQLException, IOException {
        this(campaignName, clickLog, impressionLog, serverLog, user, pass, DefaultDataBaseIP);
    }

    /**
     * Allows for specifying a custom domain/ip for the MariaDB instance
     * @param campaignName
     * @param clickLog
     * @param impressionLog
     * @param serverLog
     * @param dataBaseIP
     */
    public ReadCSVsToDB(String campaignName, File clickLog, File impressionLog, File serverLog, String user, String pass, String dataBaseIP) throws SQLException, IOException {
        this.campaignName = campaignName;
        this.clickLog = clickLog;
        this.impressionLog = impressionLog;
        this.serverLog = serverLog;

        currentIP = dataBaseIP;

        String sql = "jdbc:mariadb://" + currentIP + ":" + MagicDB.defaultPort + "/" + MagicDB.dbName;
        dbConnection = DriverManager.getConnection(sql, user, pass);

        ScriptRunner sr = new ScriptRunner(dbConnection, true, true);
        try {
            sr.runScript(new BufferedReader(new FileReader("sqlScripts/sqlCreateTables.sql")));
        } catch (IOException e) {
            throw new IOException("Could not find sql Scripts File at sqlScripts/sqlCreateTables.sql");
        }

        loadFilesToDB();
    }

    private void loadFilesToDB(){
        try {
            loadClickLog();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        loadImpressionLog();
        loadServerLog();
    }

    private void loadClickLog() throws SQLException, FileNotFoundException, ParseException {
        int lineNum = FileHelpers.countLines(clickLog);
        int percentPerLine = 100/lineNum;

        Statement stmt = dbConnection.createStatement();
        String clickTableName = campaignName + MagicDB.clickTableSuffix;
        String userTableName = campaignName + MagicDB.userTableSuffix;

        CSVReader reader = new CSVReader(new FileReader(clickLog));

        Iterator<String[]> lines = reader.iterator();

        //Skips header line
        lines.next();

        while (lines.hasNext()){
            String[] tokens = lines.next();
            Date date = sdf.parse(tokens[0]);
            String id = tokens[1];//TODO: See if compatible with UUID
            String ageRange = tokens[2];//TODO: Possibly to be changed in to Enum
            String gender = tokens[3];
            String income = tokens[4];
            String context = tokens[5];
            String impressionCost = tokens[6];

            stmt.execute("INSERT INTO " + userTableName + "VALUES (" +
            id + "," +
            ageRange + "," +
            gender + "," +
            income + "," +
            context +
            "ON DUPLICATE KEY UPDATE id=id" //This line to not reinsert existing fields in to the specified table, whilst still erroring out otherwise
            );

            stmt.execute("INSERT INTO " + clickTableName + "VALUES (" +
            date.toString() + "," +
            id + "," +
            impressionCost
            );

            clickProgress += percentPerLine;
        }

        clickProgress = 100;
    }

    private void loadImpressionLog() {

    }

    private void loadServerLog() {

    }

    /**
     * Used to get the connection to the server once the parent Object has been initialised
     * @return
     * @throws SQLException
     */
    public Connection getDBConnection() {
        return dbConnection;
    }

}
