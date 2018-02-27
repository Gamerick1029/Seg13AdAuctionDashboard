import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CSVparsePerformanceTests {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files


    /**
     * Used to test the relative performances of:
     * -Reading in and tokenising the entire CSV file, then parsing it line by line
     * -Reading in, tokenising and parsing the CSV file line by line
     * Results indicated the latter to the fastest by around 2x
     * @param file
     */
    public void readClicks(File file){
        long Start = System.nanoTime();

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

        System.out.println("reader.readAll test takes: " + (System.nanoTime() - Start)/1000000000.0);

        Start = System.nanoTime();
        try (CSVReader reader = new CSVReader(new FileReader(file))){

            Iterator<String[]> it = reader.iterator();

            it.next();

            while (it.hasNext()){
                String[] tokens = it.next();
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

        System.out.println("Iterator test takes: " + (System.nanoTime() - Start)/1000000000.0);
    }


}
