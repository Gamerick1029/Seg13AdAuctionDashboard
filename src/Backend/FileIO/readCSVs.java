package Backend.FileIO;

import Backend.Model.ClickData;
import Backend.Model.ImpressionData;
import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.Gender;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;
import Backend.Model.ServerData;
import com.opencsv.CSVReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jacob on 23/02/2018.
 * Holds static functions for loading each of the 3 CSVs in to the application
 */

public class readCSVs {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    public static float fileProgress = 0;

    public static List<ClickLog> readClicks(File file) {
        int fileLength = countLines(file);

        List<ClickLog> clicks = new ArrayList<>(fileLength);

        try (CSVReader reader = new CSVReader(new FileReader(file))){
            Iterator<String[]> lines = reader.iterator();
            lines.next();

            while (lines.hasNext()){
                String[] tokens = lines.next();
                Date date = sdf.parse(tokens[0]);
                String id = tokens[1];
                float cost = Float.parseFloat(tokens[2]);

                clicks.add(new ClickData(date, id, cost));
            }

            return clicks;
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

        return null;

    }

    //TODO: Optimise if possible.
    public static List<ImpressionLog> readImpressions(File file) {
        List<ImpressionLog> impressions = new ArrayList<>(countLines(file)); //We pre-define the size of the array to improve average insertion speeds

        try (CSVReader reader = new CSVReader(new FileReader(file))){

            Iterator<String[]> lines = reader.iterator(); //Used over reader.readAll due to memory space issues

            lines.next(); //Skips the header line
            while (lines.hasNext()){
                String[] tokens = lines.next();
                Date date = sdf.parse(tokens[0]);
                String id = tokens[1];//TODO: See if compatible with UUID
                String ageRange = tokens[2];//TODO: Possibly to be changed in to Enum

                Gender gender;
                switch(tokens[3]) {
                    case "Male": gender = Gender.MALE; break;
                    case "Female": gender = Gender.FEMALE; break;
                    default: gender = Gender.OTHER;
                }

                String income = tokens[4]; //TODO: possibly Enum
                String context = tokens[5]; //TODO: possibly Enum
                float impressionCost = Float.parseFloat(tokens[6]);

                impressions.add(new ImpressionData(date, id, ageRange, gender, income, context, impressionCost));
            }

            return impressions;

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

        return null;

    }

    public static List<ServerLog> readServerLogs(File file) {
        List<ServerLog> serverLogs = new ArrayList<>(countLines(file));

        try (CSVReader reader = new CSVReader(new FileReader(file))){
            Iterator<String[]> lines = reader.iterator();
            lines.next();

            while (lines.hasNext()){
                String[] tokens = lines.next();
                Date dateStart = sdf.parse(tokens[0]);
                String id = tokens[1];
                Date dateEnd = (tokens[2].equals("n/a") ? new Date(0) : sdf.parse(tokens[2])); //If date is n/a then set date to 0, else parse date
                int pagesViewed = Integer.parseInt(tokens[3]);
                boolean converted = (tokens[4].equals("Yes") ? true : false);

                serverLogs.add(new ServerData(dateStart, id, dateEnd, pagesViewed, converted));
            }

            return serverLogs;
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

        return null;
    }

    // Modified function based on https://stackoverflow.com/a/14411695
    private static int countLines(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))){
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if(endsWithoutNewLine) {
                ++count;
            }
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
