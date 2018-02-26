package Frontend.FileIO;

import Backend.Model.Interfaces.CustomRange;
import Backend.Model.Interfaces.Gender;
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

    public static void readClicks(File file) {

        try (CSVReader reader = new CSVReader(new FileReader(file))){
            reader.readNext();
            List<String[]> lines = reader.readAll();

            for (String[] tokens : lines){
                Date date = sdf.parse(tokens[0]);
                String id = tokens[1]; //TODO: See if this can be turned in to a UUID
                float cost = Float.parseFloat(tokens[2]);
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

    //TODO: Make faster. Reading a 500MB file in to memory in one go to then work on it takes a long time and cripples lower end systems
    public static void readImpressions(File file) {
        List<TempImpressionHolder> impressions = new ArrayList<>(countLines(file)); //temporary impression store location. We pre-define the size of the array to improve average element insertion speeds

        try (CSVReader reader = new CSVReader(new FileReader(file))){

            Iterator<String[]> tokenised = reader.iterator(); //Used over reader.readAll due to memory space issues

            tokenised.next(); //Skips the header line
            while (tokenised.hasNext()){
                String[] tokens = tokenised.next();
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

                impressions.add(new TempImpressionHolder(date, id, ageRange, gender, income, context, impressionCost));
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

    public static void readServerLogs(String fileName) {
        //TODO: Read server logs
    }

    // Modified function based on https://stackoverflow.com/a/14411695
    private static int countLines(File file) {
        System.out.println("Counting lines");
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))){
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
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
            System.out.println("Counted " + count + " lines");
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
