package Backend.Model.Interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * The main Interface for getting data from a database.
 */
public interface DataModelDBTrimmed {

    /**
     * Gets the total number of unique impressions
     * @return
     * @throws SQLException
     */
    int getImpressionsNumber() throws SQLException;

    /**
     * Returns a mapping of dates to the number of impressions on that date
     * @return
     */
    Map<Date, Integer> getImpressionsData() throws SQLException;


    /**
     * Gets the total number of unique clicks in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    int getClicksNumber() throws SQLException;

    /**
     * Returns a mapping of dates to the number of clicks on that date
     * @return
     * @throws SQLException
     */
    Map<Date, Integer> getClicksData() throws SQLException;


    /**
     * Returns the total number of Uniques in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    int getUniquesNumber() throws SQLException;

    /**
     * Gets the number of bounces in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    int getBouncesNumber() throws SQLException;

    /**
     * Returns a mapping of dates to the number of bounces on that date
     * @return
     * @throws SQLException
     */
    Map<Date, Integer> getBouncesData() throws SQLException;


    /**
     * Gets the total number of unique conversions in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    int getConversionsNumber() throws SQLException;

    /**
     * Returns a mapping of dates to the number of conversions on that date
     * @return
     * @throws SQLException
     */
    Map<Date, Integer> getConversionsData() throws SQLException;


    /**
     * Returns the total overall cost of the campaign in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    float getTotalCost() throws SQLException;

    /**
     * Returns a mapping of dates to the total cumulative cost on that date
     * @return
     * @throws SQLException
     */
    Map<Date, Float> getCostData() throws SQLException;


    /**
     * Returns the total clicks-per-impression in the range bounded by the start and end Dates of the Filter
     *
     * @return
     * @throws SQLException
     */
    default float getCTR() throws SQLException {
        return (float) getClicksNumber()/getImpressionsNumber();
    }

    /**
     * Returns a mapping of dates to the average clicks-per-impression up to that date
     * @return
     * @throws SQLException
     */
    Map<Date, Float> getCTRData() throws SQLException;


    /**
     * Returns the total cost-per-acquisition (conversion) in the range bounded by the start and end Dates of the Filter
     *
     * @return
     * @throws SQLException
     */
    default float getCPA() throws SQLException {
        return getTotalCost()/getConversionsNumber();
    }

    /**
     * Returns a mapping of dates to the total cost-per-acquisition (conversion) up to that date
     * @return
     * @throws SQLException
     */
    Map<Date, Float> getCPAData() throws SQLException;


    /**
     * Returns the total cost-per-click in the range bounded by the start and end Dates of the Filter
     *
     * @return
     * @throws SQLException
     */
    default float getCPC() throws SQLException {
        return getTotalCost()/getClicksNumber();
    }

    /**
     * Returns a mapping of dates to average cost-per-click up to that date
     * @return
     * @throws SQLException
     */
    Map<Date, Float> getCPCData() throws SQLException;


    //TODO: This more commonly set by campaign designers. Should include some way of editing this
    /**
     * Returns (cost/impressions x 1000) in the range bounded by the start and end Dates of the Filter
     *
     * @param campaignName
     * @return
     * @throws SQLException
     */
    default float getCPM(String campaignName) throws SQLException {
        return (getTotalCost()/getImpressionsNumber()) * 1000;
    }

    /**
     * Returns a mapping of dates to the average CPM up to that date
     * @return
     * @throws SQLException
     */
    Map<Date, Integer> getCPMData() throws SQLException;


    /**
     * Returns the total bounce rate in the range bounded by the start and end Dates of the Filter
     * @return
     * @throws SQLException
     */
    float getBounceRate() throws SQLException;

    /**
     * Returns a mapping of dates to the average bounce rate up to that date
     * @return
     * @throws SQLException
     */
    Map<Date, Integer> getBounceData() throws SQLException;


    /**
     * Returns a reference to the currently held Filter. Can also be accessed directly
     * @return
     */
    Filter getFilter();

    /**
     * Used to set the current Filter. Can also be set directly
     * @param filter
     */
    void setFilter(Filter filter);
}
