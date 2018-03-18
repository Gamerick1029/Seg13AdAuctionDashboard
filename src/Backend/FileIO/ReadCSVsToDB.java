package Backend.FileIO;

import java.io.File;
import java.sql.*;

public class ReadCSVsToDB {

    private static final String DefaultDataBaseIP = "hobbithouse.me";
    private String currentIP = new String(DefaultDataBaseIP);



    private File clickLog, impressionLog, serverLog;

    public ReadCSVsToDB(File clickLog, File impressionLog, File serverLog, String dataBaseIP){
        this(clickLog, impressionLog, serverLog);
        currentIP = dataBaseIP;
    }

    public ReadCSVsToDB(File clickLog, File impressionLog, File serverLog){
        this.clickLog = clickLog;
        this.impressionLog = impressionLog;
        this.serverLog = serverLog;
    }

    public void loadFilesToDB(){

    }

    public Connection getDBConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://" + currentIP + ":3306", "seg", "seg13");
        return connection;
    }

}
