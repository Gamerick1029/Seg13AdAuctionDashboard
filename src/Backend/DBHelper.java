package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
