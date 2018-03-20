package Backend.Model.Stubs;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Benedict on 28/02/2018.
 * Stub for the data model
 */
public class DataModelStub implements DataModel
{
    private Map<Date, Integer> intMap;
    private Map<Date, Float> floatMap;

    public DataModelStub()
    {
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/mm/yyyy");
        intMap = new HashMap<>();
        floatMap = new HashMap<>();
        try
        {
            intMap.put(df.parse("01/01/0001"), 1);
            intMap.put(df.parse("02/02/0002"), 2);
            intMap.put(df.parse("03/03/0003"), 3);

            floatMap.put(df.parse("01/01/0001"), 1.0f);
            floatMap.put(df.parse("02/02/0002"), 2.0f);
            floatMap.put(df.parse("03/03/0003"), 3.0f);
        } catch(ParseException pe)
        {
            System.err.println("Error parsing stub date formats!");
            System.err.println("Stub map values will be blank");
            pe.printStackTrace();
        }
    }

    @Override
    public List<ClickLog> getClickData()
    {
        return null;
    }

    @Override
    public List<ImpressionLog> getImpressionData()
    {
        return null;
    }

    @Override
    public List<ServerLog> getServerData()
    {
        return null;
    }

    @Override
    public int getImpressionsNumber()
    {
        return 1;
    }

    @Override
    public Map<Date, Integer> getImpressionsByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public int getClicksNumber()
    {
        return 2;
    }

    @Override
    public Map<Date, Integer> getClicksByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public int getUniquesNumber()
    {
        return 3;
    }

    @Override
    public Map<Date, Integer> getUniquesByInterval(Date startInterval, Date endInterval) {
        return null;
    }

    @Override
    public int getBouncesNumber()
    {
        return 4;
    }

    @Override
    public Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public int getConversionsNumber()
    {
        return 5;
    }

    @Override
    public Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public float getTotalCost()
    {
        return 6.0f;
    }

    @Override
    public Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public float getCTR()
    {
        return 7.0f;
    }

    @Override
    public Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public float getCPA()
    {
        return 8.0f;
    }

    @Override
    public Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public float getCPC()
    {
        return 9.0f;
    }

    @Override
    public Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval) {
        return null;
    }

    @Override
    public float getCPM()
    {
        return 10.0f;
    }

    @Override
    public Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval) {
        return null;
    }


    @Override
    public float getBounceRate()
    {
        return 11.0f;
    }

    @Override
    public Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval) {
        return null;
    }

    @Override
    public Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval) {
        return null;
    }


}
