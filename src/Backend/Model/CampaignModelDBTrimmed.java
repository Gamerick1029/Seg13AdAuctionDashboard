package Backend.Model;

import Backend.DBHelper;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.Filter;

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
    private String trimTrailingOperators(String untrimmed){
        if (untrimmed.endsWith(" OR ")) return untrimmed.substring(0, untrimmed.length() - 4);
        if (untrimmed.endsWith(" AND ")) return untrimmed.substring(0, untrimmed.length() - 5);
        return untrimmed;
    }

    private String dateCondition() {
        StringBuilder sb = new StringBuilder();

        if (filterDB.getStartDate() != null) sb.append("Date > " + sdf.format(filterDB.getStartDate()) + " AND ");
        if (filterDB.getEndDate() != null)   sb.append("Date < " + sdf.format(filterDB.getEndDate()));

        return trimTrailingOperators(sb.toString());
    }

    private String genderCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.genderMale) sb.append("Gender = 'Male' OR ");
        if (filterDB.genderFemale) sb.append("Gender = 'Female' OR ");
        if (filterDB.genderOther) sb.append("Gender = 'Other'");

        return trimTrailingOperators(sb.toString());
    }

    private String ageCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.ageBelow25)  sb.append("AgeRange = '<25' OR ");
        if (filterDB.age25to34)   sb.append("AgeRange = '25-34' OR ");
        if (filterDB.age35to44)   sb.append("AgeRange = '35-44' OR ");
        if (filterDB.age45to54)   sb.append("AgeRange = '45-54' OR ");
        if (filterDB.ageAbove54)  sb.append("AgeRange = '>55'");

        return trimTrailingOperators(sb.toString());
    }

    private String incomeCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.incomeLow)    sb.append("Income = 'Low' OR ");
        if (filterDB.incomeMedium) sb.append("Income = 'Medium' OR ");
        if (filterDB.incomeHigh)   sb.append("Income = 'High' OR ");

        return trimTrailingOperators(sb.toString());
    }

    private String contextCondition(){
        StringBuilder sb = new StringBuilder();

        if (filterDB.contextBlog) sb.append("Context = 'Blog' OR ");
        if (filterDB.contextNews) sb.append("Context = 'News' OR ");
        if (filterDB.contextShopping) sb.append("Context = 'Shopping' OR ");
        if (filterDB.contextBlog) sb.append("Context = 'Social Media' OR ");

        return trimTrailingOperators(sb.toString());
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

        return trimTrailingOperators(sb.toString());
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
     *
     * @param rs
     * @throws SQLException
     */
    private Map<Date, Integer> getDateToInt(ResultSet rs) throws SQLException {
        Map<Date, Integer> result = new HashMap<>();
        while (rs.next()){
            Date date = rs.getDate(1);
            int num = rs.getInt(2);
            result.put(date, num);
        }
        return result;
    }

    private Map<Date, Float> getDateToFloat(ResultSet rs) throws SQLException {
        Map<Date, Float> result = new HashMap<>();
        while (rs.next()){
            Date date = rs.getDate(1);
            float num = rs.getFloat(2);
            result.put(date, num);
        }
        return result;
    }

    /**
     * Builds a string of format {table1}.ID = {table2}.ID etc.
     * @param tables
     * @return
     */
    private String idsEqual(String... tables){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tables.length - 1; i++) {
            sb.append(tables[i] + ".ID = " + tables[i+1] + ".ID AND ");
        }

        return trimTrailingOperators(sb.toString());
    }

    /**
     * Simple helper function for building SQL queries
     * @param select
     * @param fromTables
     * @param whereConditions
     * @return
     * @throws SQLException
     */
    private ResultSet buildAndExecuteStatement(String select, String fromTables, String whereConditions, String otherSuffixes) throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery(buildStatement(select, fromTables, whereConditions, otherSuffixes));
        return rs;
    }

    private ResultSet buildAndExecuteSubStatements(String[] select, String[] fromTables, String[] whereConditions, String[] asSuffixes) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        for (int i = 0; i < select.length ; i++) {
            sb.append("(").append(buildStatement(select[i], fromTables[i], whereConditions[i], "")).append(")").append("AS " + asSuffixes[i]).append(",");
        }
        return dbHelper.getConnection().createStatement().executeQuery(sb.toString().substring(0, sb.toString().length() - 1 ));
    }

    private String buildStatement(String select, String fromTables, String whereConditions, String otherSuffixes){
        return "SELECT " + select + " FROM " + fromTables + " WHERE " + whereConditions + " AND " + conditions() + " " + otherSuffixes; // + ";"
    }

    //Public methods

    @Override
    public int getImpressionsNumber() throws SQLException {
        String select = "COUNT(*)";
        String fromTables = impTable() + "," + userTable();
        String whereConditions = idsEqual(impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, fromTables, whereConditions, "");

        rs.next();
        return rs.getInt("COUNT(*)");
    }

    @Override
    public Map<Date, Integer> getFullImpressions(long step) throws SQLException {
        String select = "Date, COUNT(Date)";
        String fromTables = impTable() + "," + userTable();
        String whereConditions = idsEqual(impTable(), userTable());

        ResultSet rs = buildAndExecuteStatement(select, fromTables, whereConditions, "GROUP BY `Date` ORDER BY `Date`");

        return getDateToInt(rs);
    }

    @Override
    public int getClicksNumber() throws SQLException {
        String select = "COUNT(*)";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullClicks(long step) throws SQLException {
        String select = "Date, COUNT(Date)";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "GROUP BY `Date` ORDER BY `Date`");

        return getDateToInt(rs);
    }

    @Override
    public int getUniquesNumber() throws SQLException {
        String select = "COUNT(DISTINCT(" + clickTable() + ".ID))";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullUniques(long step) throws SQLException {
        String clickDate = clickTable() + ".Date";
        String select = clickDate +", COUNT(" + clickDate + ")";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "GROUP BY `" + clickDate + "` ORDER BY `" + clickDate + "`" );

        return getDateToInt(rs);
    }

    @Override
    public int getBouncesNumber() throws SQLException {
        String select = "COUNT(" + servTable() + ".PagesViewed <= 1)";
        String from = servTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(servTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "");

        rs.next();
        return rs.getInt(select);
    }

    //TODO: Doesn't actually use the step yet
    /**
     * Gets a mapping of bounces to dates. Bounces defined as someone who only visits one page
     * @param step the millisecond interval by which to group
     * @return
     * @throws SQLException
     */
    @Override
    public Map<Date, Integer> getFullBounces(long step) throws SQLException {
        String select = "Date";
        String from = servTable() + "," + impTable() + "," + clickTable();
        String where = servTable() + ".PagesViewed <= 1 AND " + idsEqual(servTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "GROUP BY `Date` ORDER BY `Date`");

        return getDateToInt(rs);
    }

    @Override
    public int getConversionsNumber() throws SQLException {
        String select = "COUNT(" + servTable() + ".Conversion = 1)";
        String from = servTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(servTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullConversions(long step) throws SQLException {
        String select = servTable() + ".Date";
        String from = servTable() + "," + impTable() + "," + userTable();
        String where = servTable() + ".Conversion = 1 AND " + idsEqual(servTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "GROUP BY `" + select + "` ORDER BY `" + select + "`");

        return getDateToInt(rs);
    }

    @Override
    public float getTotalCost() throws SQLException {
        String select = "SUM(ClickCost)";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "");

        rs.next();
        return rs.getFloat(select);
    }

    @Override
    public Map<Date, Float> getFullCost(long step) throws SQLException {
        String select = clickTable() + ".Date AS Date, ClickCost";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        ResultSet rs = buildAndExecuteStatement(select, from, where, "GROUP BY `Date` ORDER BY `Date`");

        return getDateToFloat(rs);
    }

    @Override
    public float getCTR() throws SQLException {
        String select = "COUNT(*)";
        String fromClicks = clickTable() + "," + impTable() + "," + userTable();
        String fromImpr = impTable() + "," + userTable();
        String whereClicks = idsEqual(clickTable(), impTable(), userTable());
        String whereImpr = idsEqual(impTable(), userTable());
        String[] selects = {select, select};
        String[] froms = {fromClicks, fromImpr};
        String[] wheres = {whereClicks, whereImpr};
        String[] asSuffixes = {"Clicks", "Impressions"};
        ResultSet rs = buildAndExecuteSubStatements(selects, froms, wheres, asSuffixes);

        rs.next();
        float clickCount = rs.getFloat("Clicks");
        float impCount = rs.getFloat("Impressions");
        return clickCount/impCount;
    }

    @Override
    public Map<Date, Float> getFullCTR(long step) throws SQLException {
        String select = "DATE_FORMAT(" + clickTable() + ".Date, \"%Y-%m-%d\") AS CDated, COUNT(*) AS clickCount";
        String from = clickTable() + "," + impTable() + "," + userTable();
        String where = idsEqual(clickTable(), impTable(), userTable());
        String statement = buildStatement(select, from, where, "GROUP BY `CDated` ORDER BY `CDated`");

        String select2 = "DATE_FORMAT(" + impTable() + ".Date, \"%Y-%m-%d\") AS IDated, COUNT(*) AS impCount";
        String from2 = impTable() + "," + userTable();
        String where2 = idsEqual(impTable(), userTable());
        String statement2 = buildStatement(select2, from2, where2, "GROUP BY `IDated` ORDER BY `IDated`");

        String statementFinal = "SELECT IDated, (clickCount/impCount)  FROM (" + statement2 + ") AS impressions LEFT OUTER JOIN (" + statement + ") AS clicks ON impressions.IDated = clicks.CDated";

        ResultSet rs = dbHelper.getConnection().createStatement().executeQuery(statementFinal);

        return getDateToFloat(rs);
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
    public void setFilter(Filter filter) {
        filterDB = filter;
    }
}
