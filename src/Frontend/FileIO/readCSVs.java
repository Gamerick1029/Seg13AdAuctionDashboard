package Frontend.FileIO;

import com.opencsv.CSVReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacob on 23/02/2018.
 * Holds static functions for loading each of the 3 CSVs in to the application
 */

public class readCSVs {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    public static void readClicks(File file) {

        try (CSVReader reader = new CSVReader(new FileReader(file))){
            reader.readNext();
            List<String[]> lines = reader.readAll();

            for (String[] tokens : lines){
                Date date = sdf.parse(tokens[0]);
                String id = tokens[1]; //TODO: See if this can be turned in to a UUID
                float cost = Float.parseFloat(tokens[2]);
                System.out.println(date + "//" + id + "//" + cost);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Could not find file with name: " + file);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error when CSVparsing file with name: " + file);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error parsing DateTime from CSV in file with name: " + file);
        }
    }

    public static void readImpressions(String fileName) {
        //TODO: Read impressions
    }

    public static void readServerLogs(String fileName) {
        //TODO: Read server logs
    }



}
