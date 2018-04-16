package Backend;

import Backend.FileIO.FileHelpers;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A collection of useful methods to aid making and passing around a connection to a Database.
 *
 * This class functions mainly as a holder for the static {@code connection} field as well as a few utility functions besides.
 * To use it, one of the static {@code initConnection} methods must first be called somewhere in the program and from then on
 * new DBHelpers can be created and used throughout the rest of the program.
 */
public class DBHelper {

    public static final String dbName = "AdAuctionData";
    public static final String defaultHost = "hobbithouse.me";
    public static final String defaultPort = "3306";

    public static final String defaultConnectionUrl = "jdbc:mariadb://" + defaultHost + ":" + defaultPort + "/" + dbName;
    // TODO: Security risk here, need to implement some form of token exchange with the remote host so we don't have to
    // store the current login in plain text form to enable recreating a dropped connection
    private static String currentUrl;
    private static String currentUsername;
    private static String currentPass;

    public static final String userTableSuffix = "_users";
    public static final String impressionTableSuffix = "_impressions";
    public static final String clickTableSuffix = "_clicks";
    public static final String serverLogTableSuffix = "_serverLogs";

    private static Connection connection;

    /**
     * Used to create a Connection to the Database. If this method has not been called then all other method calls will fail
     * @param url The Url to use
     * @param user Username for login
     * @param pass Password for login
     * @throws SQLException
     */
    public static void initConnection(String url, String user, String pass) throws SQLException {
        currentUrl = url;
        currentUsername = user;
        currentPass = pass;
        connection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Used to create a Connection to the Database. If this method has not been called then all other method calls will fail
     * This method also forces the use of a default URL.
     * @param user
     * @param pass
     * @throws SQLException
     */
    public static void initConnection(String user, String pass) throws SQLException {
        initConnection(defaultConnectionUrl, user, pass);
    }

    /**
     * Returns the connection to the default database
     * @return The connection to the database
     * @throws SQLException If a connection cannot be made
     */
    public Connection getConnection() throws SQLException {
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
     * @param campaignName Campaign to delete
     * @return True if the operation completed successfully, false otherwise
     */
    public boolean deleteCampaign(String campaignName) {
        boolean success = false;
        try {
            success = executeScriptWithVariable(new File("sqlScripts/sqlDeleteCampaign.sql"), "\\[VAR]", campaignName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Replaces a regex pattern in a sql script file then executes the modified script file on the current database
     * The original file is left untouched by this operation
     * @param scripts The script file to modify
     * @param regex The pattern to replace
     * @param var The replacement string
     * @return True if the script was executed successfully, False otherwise
     * @throws IOException Thrown if an issue arises with accessing the initial script file
     */
    public boolean executeScriptWithVariable(File scripts, String regex, String var) throws IOException {
        //TODO: Remove the exception from the method signature
        boolean success = true;

        String fileContents = FileHelpers.readFileToString(scripts, Charset.defaultCharset());
        fileContents = fileContents.replaceAll(regex, var);
        File temp = File.createTempFile("sqlRegexed", ".sql");

        FileOutputStream fis = new FileOutputStream(temp);
        fis.write(fileContents.getBytes());
        fis.close();

        ScriptRunner sr = new ScriptRunner(connection, true, true);
        try {
            sr.runScript(new BufferedReader(new FileReader(temp)));
        } catch (IOException e) {
            System.err.println("Unable to access temporary file " + temp.getAbsolutePath());
            e.printStackTrace();
            success = false;
        } catch (SQLException e) {
            System.err.println("Unable to run sql file " + scripts.getAbsolutePath() + " against the Database");
            e.printStackTrace();
            success = false;
        } finally {
            temp.delete(); //Removes the temporary file when we're done with it
        }
        return success;
    }

    /**
     * Used to force the DBHelper to reestablish a connection to the Database
     * @throws NullPointerException If the connection object was never set in the first place
     * @throws SQLException If there is an issue connecting to the DB
     */
    public void reestablishConnection() throws NullPointerException, SQLException {
        if (connection == null) {throw new NullPointerException("Connection object was never been initialised");}

        connection = DriverManager.getConnection(currentUrl, currentUsername, currentPass);
    }

}
