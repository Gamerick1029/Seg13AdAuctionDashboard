package Backend.Model;

import Backend.DBHelper;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.DataModelDBTrimmed;
import Backend.Model.Interfaces.Filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class CampaignModelDBTrimmed implements DataModel {

    private String campaignName;
    private DBHelper dbHelper;

    private Filter filterDB = new Filter();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    public CampaignModelDBTrimmed(String campaignName) throws SQLException {

        this.campaignName = campaignName;
        DBHelper.initConnection("seg", "seg13");
        dbHelper = new DBHelper();

    }


    @Override
    public String getName() {
        return campaignName;
    }

    /**
     * Used to trim a trailing " OR " or " AND " from the end of a String. Mostly useful for SQL statements
     * @return The trimmed String
     */
    private String trimTrailingOperands(String untrimmed){
        if (untrimmed.endsWith(" OR ")) return untrimmed.substring(0, untrimmed.length() - 4);
        if (untrimmed.endsWith(" AND ")) return untrimmed.substring(0, untrimmed.length() - 5);
        return untrimmed;
    }

    private String dateCondition() {
        StringBuilder sb = new StringBuilder();

        if (filterDB.getStartDate() != null) sb.append("Date > " + sdf.format(filterDB.getStartDate()) + " AND ");
        if (filterDB.getEndDate() != null)   sb.append("Date < " + sdf.format(filterDB.getEndDate()));

        return trimTrailingOperands(sb.toString());
    }

    private String genderCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.genderMale) sb.append("Gender = 'Male' OR ");
        if (filterDB.genderFemale) sb.append("Gender = 'Female' OR ");
        if (filterDB.genderOther) sb.append("Gender = 'Other'");

        return trimTrailingOperands(sb.toString());
    }

    private String ageCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.ageBelow25)  sb.append("AgeRange = '<25' OR ");
        if (filterDB.age25to34)   sb.append("AgeRange = '25-34' OR ");
        if (filterDB.age35to44)   sb.append("AgeRange = '35-44' OR ");
        if (filterDB.age45to54)   sb.append("AgeRange = '45-54' OR ");
        if (filterDB.ageAbove54)  sb.append("AgeRange = '>55'");

        return trimTrailingOperands(sb.toString());
    }

    private String incomeCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.incomeLow)    sb.append("Income = 'Low' OR ");
        if (filterDB.incomeMedium) sb.append("Income = 'Medium' OR ");
        if (filterDB.incomeHigh)   sb.append("Income = 'High' OR ");

        return trimTrailingOperands(sb.toString());
    }

    private String contextCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.contextBlog) sb.append("Context = 'Blog' OR ");
        if (filterDB.contextNews) sb.append("Context = 'News' OR ");
        if (filterDB.contextShopping) sb.append("Context = 'Shopping' OR ");
        if (filterDB.contextBlog) sb.append("Context = 'Social Media' OR ");

        return trimTrailingOperands(sb.toString());
    }

    /**
     * Crafts a WHERE statement based on the current Filter Settings
     * @return
     */
    private String conditions(){
        StringBuilder sb = new StringBuilder();

        String dateCondition = dateCondition();
        String genderCondition = genderCondition();
        String ageCondition = ageCondition();
        String incomeCondition = incomeCondition();
        String contextCondition = contextCondition();

        if (!dateCondition.equals(""))    sb.append("(").append(dateCondition).append(")").append(" AND ");
        if (!genderCondition.equals(""))  sb.append("(").append(genderCondition).append(")").append(" AND ");
        if (!ageCondition.equals(""))     sb.append("(").append(ageCondition).append(")").append(" AND ");
        if (!incomeCondition.equals(""))  sb.append("(").append(incomeCondition).append(")").append(" AND ");
        if (!contextCondition.equals("")) sb.append("(").append(contextCondition).append(")");

        return trimTrailingOperands(sb.toString());
    }

    private String userTable(){
        return campaignName + DBHelper.userTableSuffix;
    }

    private String impTable(){
        return campaignName + DBHelper.impressionTableSuffix;
    }

    private String clickTable(){
        return campaignName + DBHelper.clickTableSuffix;
    }

    private String servTable(){
        return campaignName + DBHelper.serverLogTableSuffix;
    }

    /**
     * Takes an empty Map and a ResultSet consisting of a single column of Dates, and maps the Dates to the number of
     * of occurrences in the ResultSet
     * @param rs
     * @param result
     * @throws SQLException
     */
    private void getDateToInt(ResultSet rs, Map<Date, Integer> result) throws SQLException {
        while (rs.next()){
            Date date = rs.getDate("Date");
            if (result.containsKey(date)){
                result.put(date, result.get(date)+1);
            } else {
                result.put(date, 1);
            }
        }
    }

    @Override
    public int getImpressionsNumber() throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT COUNT(*) FROM " + impTable() + "," + userTable()
                + " WHERE " + impTable() + ".ID = " + userTable() + ".ID AND " + conditions() + ";");

        rs.next();
        return rs.getInt("COUNT(*)");
    }

    @Override
    public Map<Date, Integer> getFullImpressions(long step) throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT Date FROM " + impTable() + "," + userTable()
                + " WHERE " + impTable() + ".ID = " + userTable() + ".ID AND " + conditions() + ";");

        Map<Date, Integer> result = new HashMap<>();

        getDateToInt(rs, result);

        return result;
    }

    @Override
    public int getClicksNumber() throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT COUNT(*) FROM " + clickTable() + "," + userTable()
                + " WHERE " + clickTable() + ".ID = " + userTable() + ".ID AND " + conditions() + ";");

        rs.next();
        return rs.getInt("COUNT(*)");
    }

    @Override
    public Map<Date, Integer> getFullClicks(long step) throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT Date FROM " + clickTable() + "," + userTable()
                + " WHERE " + impTable() + ".ID = " + userTable() + ".ID AND " + conditions() + ";");

        Map<Date, Integer> result = new HashMap<>();

        getDateToInt(rs, result);

        return result;
    }

    @Override
    public int getUniquesNumber() throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT COUNT(DISTINCT (" + clickTable() + ".ID)) FROM " + clickTable() + ", " + userTable() + ", " + impTable()
                + " WHERE " + clickTable() + ".ID = " + userTable() + ".ID AND " + clickTable() + ".ID = " + impTable() + ".ID AND " + conditions() + ";");

        rs.next();
        return rs.getInt("COUNT(DISTINCT (" + clickTable() + ".ID))");
    }

    @Override
    public Map<Date, Integer> getFullUniques(long step) throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT " + clickTable() + ".Date FROM " + clickTable() + ", " + userTable() + ", " + impTable()
                + " WHERE " + clickTable() + ".ID = " + userTable() + ".ID AND " + clickTable() + ".ID = " + impTable() + ".ID AND " + conditions() + ";");

        Map<Date, Integer> result = new HashMap<>();
        int count = 0;

        while (rs.next()){
            Date date = rs.getDate("Date");
            count += 1;
            if (result.containsKey(date)) {
                result.put(date, result.get(date) + 1);
            } else {
                result.put(date, count);
            }
        }

        return result;
    }

    @Override
    public int getBouncesNumber() throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT COUNT(" + servTable() + ".PagesViewed <= 1) FROM " + servTable() + ", " + userTable() + ", " + impTable()
                + " WHERE " + servTable() + ".ID = " + userTable() + ".ID AND " + servTable() + ".ID = " + impTable() + ".ID AND " + conditions() + ";");

        rs.next();
        return rs.getInt("COUNT(" + servTable() + ".PagesViewed <= 1)");
    }

    @Override
    public Map<Date, Integer> getFullBounces(long step) throws SQLException {
        return null;
    }

    @Override
    public int getConversionsNumber() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Integer> getFullConversions(long step) throws SQLException {
        return null;
    }

    @Override
    public float getTotalCost() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullCost(long step) throws SQLException {
        return null;
    }

    @Override
    public float getCTR() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullCTR(long step) throws SQLException {
        return null;
    }

    @Override
    public float getCPA() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullCPA(long step) throws SQLException {
        return null;
    }

    @Override
    public float getCPC() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullCPC(long step) throws SQLException {
        return null;
    }

    @Override
    public float getCPM() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullCPM(long step) throws SQLException {
        return null;
    }

    @Override
    public float getBounceRate() throws SQLException {
        return 0;
    }

    @Override
    public Map<Date, Float> getFullBounceRate(long step) throws SQLException {
        return null;
    }

    @Override
    public Map<Date, Set<String>> getFullUsers(long step) throws SQLException {
        return null;
    }

    @Override
    public Filter getFilter() {
        return filterDB;
    }

    @Override
    public void setFilter(Filter f) {
        filterDB = f;
    }
}
