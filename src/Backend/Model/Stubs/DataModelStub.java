package Backend.Model.Stubs;

import Backend.Model.Interfaces.*;

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
    private Filter filter;

    public DataModelStub()
    {
        filter = new Filter();
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/mm/yyyy");
        intMap = new HashMap<>();
        floatMap = new HashMap<>();
        try
        {
            intMap.put(df.parse("05/01/2015"), 1);
            intMap.put(df.parse("02/01/2015"), 2);
            intMap.put(df.parse("03/01/2015"), 3);

            floatMap.put(df.parse("05/01/2015"), 1.0f);
            floatMap.put(df.parse("02/01/2015"), 2.0f);
            floatMap.put(df.parse("03/01/2015"), 3.0f);
        } catch(ParseException pe)
        {
            System.err.println("Error parsing stub date formats!");
            System.err.println("Stub map values will be blank");
            pe.printStackTrace();
        }
    }

     
    public List<ClickLog> getClickData()
    {
        return null;
    }

     
    public List<ImpressionLog> getImpressionData()
    {
        return null;
    }

     
    public List<ServerLog> getServerData()
    {
        return null;
    }

     
    public String getName() {return "Stub";}

     
    public int getImpressionsNumber()
    {
        return 1;
    }

     
    public Map<Date, Integer> getImpressionsByInterval(Date startInterval, Date endInterval) {
        return intMap;
    }

     
    public Map<Date, Integer> getImpressionsByInterval(Date startInterval, Date endInterval, Step step)
    {
        return intMap;
    }

     
    public Map<Date, Integer> getFullImpressions(Step step)
    {
        return intMap;
    }

     
    public int getOverallImpressionsByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public int getClicksNumber()
    {
        return 2;
    }

     
    public Map<Date, Integer> getClicksByInterval(Date startInterval, Date endInterval) {
        return intMap;
    }

     
    public Map<Date, Integer> getClicksByInterval(Date startInterval, Date endInterval, Step step)
    {
        return intMap;
    }

     
    public Map<Date, Integer> getFullClicks(Step step)
    {
        return intMap;
    }

     
    public int getOverallClicksByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public int getUniquesNumber()
    {
        return 3;
    }

     
    public Map<Date, Integer> getUniquesByInterval(Date startInterval, Date endInterval) {
        return intMap;
    }

     
    public Map<Date, Integer> getUniquesByInterval(Date startInterval, Date endInterval, Step step)
    {
        return intMap;
    }

     
    public Map<Date, Integer> getFullUniques(Step step)
    {
        return intMap;
    }

     
    public int getOverallUniquesByInterval(Date startInterval, Date endInterval) {
        return 0;
    }

     
    public int getBouncesNumber()
    {
        return 4;
    }

     
    public Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval) {
        return intMap;
    }

     
    public Map<Date, Integer> getBouncesByInterval(Date startInterval, Date endInterval, Step step)
    {
        return intMap;
    }

     
    public Map<Date, Integer> getFullBounces(Step step)
    {
        return intMap;
    }

     
    public int getOverallBouncesByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public int getConversionsNumber()
    {
        return 5;
    }

     
    public Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval) {
        return intMap;
    }

     
    public Map<Date, Integer> getConversionsByInterval(Date startInterval, Date endInterval, Step step)
    {
        return intMap;
    }

     
    public Map<Date, Integer> getFullConversions(Step step)
    {
        return intMap;
    }

     
    public int getOverallConversionsByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public float getTotalCost()
    {
        return 6.0f;
    }

     
    public Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getCostByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullCost(Step step)
    {
        return floatMap;
    }

     
    public float getOverallCostByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public float getCTR()
    {
        return 7.0f;
    }

     
    public Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getCTRByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullCTR(Step step)
    {
        return floatMap;
    }

     
    public float getOverallCTRByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public float getCPA()
    {
        return 8.0f;
    }

     
    public Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getCPAByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullCPA(Step step)
    {
        return floatMap;
    }

     
    public float getOverallCPAByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public float getCPC()
    {
        return 9.0f;
    }

     
    public Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getCPCByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullCPC(Step step)
    {
        return floatMap;
    }

     
    public float getOverallCPCByInterval(Date startInterval, Date endInterval) {
        return 0;
    }

     
    public float getCPM()
    {
        return 10.0f;
    }

     
    public Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getCPMByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullCPM(Step step)
    {
        return floatMap;
    }

     
    public float getOverallCPMByInterval(Date startInterval, Date endInterval) {
        return 0;
    }


     
    public float getBounceRate()
    {
        return 11.0f;
    }

     
    public Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval) {
        return floatMap;
    }

     
    public Map<Date, Float> getBounceRateByInterval(Date startInterval, Date endInterval, Step step)
    {
        return floatMap;
    }

     
    public Map<Date, Float> getFullBounceRate(Step step)
    {
        return floatMap;
    }

     
    public float getOverallBounceRateByInterval(Date startInterval, Date endInterval) {
        return 0;
    }

     
    public Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval) {
        return null;
    }

     
    public Map<Date, Set<String>> getUsersByInterval(Date startInterval, Date endInterval, Step step)
    {
        return null;
    }

     
    public Map<Date, Set<String>> getFullUsers(Step step)
    {
        return null;
    }

     
    public Filter getFilter()
    {
        return filter;
    }

     
    public void setFilter(Filter f)
    {
        this.filter = f;
    }

     
    public Set<String> getOverallUsersRateByInterval(Date startInterval, Date endInterval) {
        return null;
    }


}
