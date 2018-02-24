package Frontend.FileIO;

import java.io.File;
import java.io.FileNotFoundException;

public class readCSVs {

    public static void readClicks(String fileName) throws FileNotFoundException {
        File file = stringToFile(fileName);
    }

    public static void readImpressions(String fileName) throws FileNotFoundException {
        File file = stringToFile(fileName);
    }

    public static void readServerLogs(String fileName) throws FileNotFoundException {
        File file = stringToFile(fileName);
    }

    private static File stringToFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.isFile()){ throw new FileNotFoundException("Couldn't locate file with name: "+ fileName); }
        return file;
    }

}
