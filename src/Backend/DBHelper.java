package Backend;

import Backend.FileIO.FileHelpers;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    public static final String dbName = "AdAuctionData";
    public static final String defaultHost = "hobbithouse.me";
    public static final String defaultPort = "3306";

    public static final String defaultConnectionUrl = "jdbc:mariadb://" + defaultHost + ":" + defaultPort + "/" + dbName;

    public static final String userTableSuffix = "_users";
    public static final String impressionTableSuffix = "_impressions";
    public static final String clickTableSuffix = "_clicks";
    public static final String serverLogTableSuffix = "_serverLogs";

    private Connection connection;

    /**
     * Middleman for acquiring a connection to the database. Establishes a connection using default settings.
     * @param user
     * @param pass
     * @throws SQLException
     */
    public DBHelper(String user, String pass) throws SQLException {
        connection = DriverManager.getConnection(defaultConnectionUrl, user, pass);
    }

    /**
     * Used when a connection already exists. Allows accessing the helper functions without needing to create a new
     * connection
     * @param connection
     */
    public DBHelper(Connection connection){
        this.connection = connection;
    }

    /**
     * Returns a connection to the default database
     * @return The connection to the database
     * @throws SQLException If a connection cannot be made
     */
    public Connection getDefaultConnection() throws SQLException {
        return connection;
    }

    /**
     * Returns the list of campaigns currently in the database
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getCampaigns() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM campaignNames;");
        ArrayList<String> campaigns = new ArrayList<>();
        while(rs.next()){
            campaigns.add(rs.getString(1));
        }
        return campaigns;
    }

    /**
     * Deletes a campaign from the database by name
     * @param campaignName
     * @return True if the operation completed successfully, false otherwise
     */
    public boolean deleteCampaign(String campaignName) {
        try {
            executeScriptWithVariable(new File("sqlScripts/sqlDeleteCampaign.sql"), campaignName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void executeScriptWithVariable(File scripts, String var) throws IOException {
        String fileContents = FileHelpers.readFileToString(scripts, Charset.defaultCharset());
        fileContents = fileContents.replaceAll("\\[VAR]", var);
        File temp = File.createTempFile("sqlRegexed", ".sql");
        temp.deleteOnExit();


        FileOutputStream fis = new FileOutputStream(temp);
        fis.write(fileContents.getBytes());
        fis.close();

        ScriptRunner sr = new ScriptRunner(connection, true, true);
        try {
            sr.runScript(new BufferedReader(new FileReader(temp)));
        } catch (IOException e) {
            throw new IOException("Could not access sql Scripts File at " + scripts.getPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
