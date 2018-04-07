import Backend.Model.Interfaces.DataModelDBTrimmed;
import Backend.Model.Interfaces.Filter;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class DataModelDBTestClass implements DataModelDBTrimmed {

    @Override
    public int getImpressionsNumber() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getImpressionsData() throws SQLException {
        return null;
    }

    @Override
    public int getClicksNumber() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getClicksData() throws SQLException {
        return null;
    }

    @Override
    public int getUniquesNumber() throws SQLException {
        return 0;
    }

    @Override
    public int getBouncesNumber() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getBouncesData() throws SQLException {
        return null;
    }

    @Override
    public int getConversionsNumber() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getConversionsData() throws SQLException {
        return null;
    }

    @Override
    public float getTotalCost() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getCostData() throws SQLException {
        return null;
    }

    @Override
    public Map<Date, Float> getCTRData() throws SQLException {
        return null;
    }

    @Override
    public Map<Date, Float> getCPAData() throws SQLException {
        return null;
    }

    @Override
    public Map<Date, Float> getCPCData() throws SQLException {
        return null;
    }

    @Override
    public Map<Date, Integer> getCPMData() throws SQLException {
        return null;
    }

    @Override
    public float getBounceRate() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getBounceData() throws SQLException {
        return null;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void setFilter(Filter filter) {

    }
}
