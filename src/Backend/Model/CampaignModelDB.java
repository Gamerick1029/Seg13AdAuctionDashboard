package Backend.Model;

import Backend.DBHelper;
import Backend.FileIO.ReadCSVsToDB;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.Filter;
import Backend.Model.Interfaces.Step;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CampaignModelDB implements DataModel{

    private String campaignName;
    private DBHelper dbHelper;

    private Filter filterDB = new Filter();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); //DateTime format as specified in the CSV files

    public CampaignModelDB(String campaignName) throws SQLException {

        this.campaignName = campaignName;
        DBHelper.initConnection("seg", "seg13");
        dbHelper = new DBHelper();

    }

    public CampaignModelDB(String campaignName, File impressions, File clicks, File server) throws SQLException, IOException {
        DBHelper.initConnection("seg", "seg13");
        dbHelper = new DBHelper();
        this.campaignName = campaignName;
        ReadCSVsToDB.makeCampaign(dbHelper, campaignName, clicks, impressions,  server);
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
        if (filterDB.ageAbove54)  sb.append("AgeRange = '>54'");

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
    private Map<Date, Integer> getDateToInt(ResultSet rs, Step step) throws SQLException {
        Map<Date, Integer> result = new HashMap<>();
        while (rs.next()){
            Date date = sqlDateToJavaDate(rs.getString(1), step);
            int num = rs.getInt(2);
            result.put(date, num);
        }
        return result;
    }

    private Map<Date, Float> getDateToFloat(ResultSet rs, Step step) throws SQLException {
        Map<Date, Float> result = new HashMap<>();
        while (rs.next()){
            Date date = sqlDateToJavaDate(rs.getString(1), step);
            float num = rs.getFloat(2);
            result.put(date, num);
        }
        return result;
    }

    private Date sqlDateToJavaDate(String date, Step step){
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        switch (step){
            case MONTH:
                sdf.applyPattern("yyyy-MM");
                try {
                    result = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case WEEK:
                sdf.applyPattern("yyyy-ww");
                try {
                    result = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case DAY:
                sdf.applyPattern("yyyy-MM-dd");
                try {
                    result = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return result;
    }

    /**
     * Builds a system of joins, where the first argument is the master table and all other are slaves.
     * @param tables
     * @return
     */
    private String makeJoins(String... tables){
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < tables.length ; i++) {
            sb.append("LEFT OUTER JOIN " + tables[i] + " ON " + tables[0] + ".ID = " + tables[i] + ".ID ");
        }

        return sb.toString();
    }

    private String stepToDate(Step step){
        String result = "";
        switch (step){
            case DAY:   result = "\"%Y-%m-%d\"";
                        break;
            case WEEK:  result = "\"%Y-%u\"";
                        break;
            case MONTH: result = "\"%Y-%m\"";
                        break;
            default:
        }
        return result;
    }

    /**
     * Simple helper function for building SQL queries
     * @param select
     * @param fromTables
     * @param whereConditions
     * @return
     * @throws SQLException
     */
    private ResultSet buildAndExecuteStatement(String select, String fromTables, String joins, String whereConditions, String otherSuffixes) throws SQLException {
        Statement stmt = dbHelper.getConnection().createStatement();
        ResultSet rs;
        rs = stmt.executeQuery(buildStatement(select, fromTables, joins, whereConditions, otherSuffixes));
        return rs;
    }

    private ResultSet buildAndExecuteSubStatements(String[] select, String[] fromTables, String[] joins, String[] whereConditions, String[] asSuffixes) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        for (int i = 0; i < select.length ; i++) {
            sb.append("(").append(buildStatement(select[i], fromTables[i], joins[i], whereConditions[i], "")).append(")").append("AS " + asSuffixes[i]).append(",");
        }
        return dbHelper.getConnection().createStatement().executeQuery(sb.toString().substring(0, sb.toString().length() - 1 ));
    }

    private String buildStatement(String select, String fromTables, String joins, String whereConditions, String otherSuffixes){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " + select + " FROM " + fromTables + " ");
        if (joins != null && !joins.equals("")) sb.append(joins + " ");

        String conditions = conditions();
        if (!whereConditions.equals("")){
            if (!conditions.equals("")){
                sb.append("WHERE ").append(whereConditions).append(" AND ").append(conditions);
            }
        } else {
            if (!conditions.equals("")){
                sb.append("WHERE ").append(conditions);
            }
        }

        sb.append(otherSuffixes);

        return trimTrailingOperators(sb.toString());
    }

    //Public methods

    @Override
    public int getImpressionsNumber() throws SQLException {
        String select = "COUNT(*)";
        String fromTables = impTable();
        String joins = makeJoins(impTable(), userTable());
        String whereConditions = "";
        ResultSet rs = buildAndExecuteStatement(select, fromTables, joins, whereConditions, "");

        rs.next();
        return rs.getInt("COUNT(*)");
    }

    @Override
    public Map<Date, Integer> getFullImpressions(Step step) throws SQLException {
        String select = "DATE_FORMAT(Date, " + stepToDate(step) +") AS Dated, COUNT(*)";
        String fromTables = impTable();
        String joins = makeJoins(impTable(), userTable());
        String whereConditions = "";

        ResultSet rs = buildAndExecuteStatement(select, fromTables, joins, whereConditions, "GROUP BY `Dated`");

        return getDateToInt(rs, step);
    }

    @Override
    public int getClicksNumber() throws SQLException {
        String select = "COUNT(*)";
        String from = clickTable();
        String joins = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullClicks(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + clickTable() + ".Date, " + stepToDate(step) +") AS Dated, COUNT(*)";
        String from = clickTable();
        String joins = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "GROUP BY `Dated`");

        return getDateToInt(rs, step);
    }

    @Override
    public int getUniquesNumber() throws SQLException {
        String select = "COUNT(DISTINCT(" + clickTable() + ".ID))";
        String from = clickTable();
        String joins = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullUniques(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + clickTable() + ".Date, " + stepToDate(step) +") AS Dated, COUNT(DISTINCT(" + clickTable() + ".ID))";
        String from = clickTable();
        String joins = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "GROUP BY `Dated`" );

        return getDateToInt(rs, step);
    }

    @Override
    public int getBouncesNumber() throws SQLException {
        String select = "COUNT(*)";
        String from = servTable();
        String joins = makeJoins(servTable(), impTable(), userTable());
        String where = "PagesViewed <= 1";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "");

        rs.next();
        return rs.getInt(select);
    }

    /**
     * Gets a mapping of bounces to dates. Bounces defined as someone who only visits one page
     * @param step the millisecond interval by which to group
     * @return
     * @throws SQLException
     */
    @Override
    public Map<Date, Integer> getFullBounces(Step step) throws SQLException {
        String select = "DATE_FORMAT(EntryDate, " + stepToDate(step) +") AS Dated, COUNT(PagesViewed <= 1)";
        String from = servTable();
        String joins = makeJoins(servTable(), impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "GROUP BY `Dated`");

        return getDateToInt(rs, step);
    }

    @Override
    public int getConversionsNumber() throws SQLException {
        String select = "COUNT(*)";
        String from = servTable();
        String joins = makeJoins(servTable(), impTable(), userTable());
        String where = "Conversion = 1";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "");

        rs.next();
        return rs.getInt(select);
    }

    @Override
    public Map<Date, Integer> getFullConversions(Step step) throws SQLException {
        String select = "DATE_FORMAT(EntryDate, " + stepToDate(step) +") AS Dated, COUNT(*)";
        String from = servTable();
        String joins = makeJoins(servTable(), impTable(), userTable());
        String where = "Conversion = 1";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "GROUP BY `Dated`");

        return getDateToInt(rs, step);
    }

    @Override
    public float getTotalCost() throws SQLException {
        String select = "SUM(Cost)";
        String from = impTable();
        String joins = makeJoins(impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "");

        rs.next();
        return rs.getFloat(select);
    }

    @Override
    public Map<Date, Float> getFullCost(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + impTable() + ".Date, " + stepToDate(step) +") AS Dated, SUM(Cost)";
        String from = impTable();
        String joins = makeJoins(impTable(), userTable());
        String where = "";
        ResultSet rs = buildAndExecuteStatement(select, from, joins, where, "GROUP BY `Dated`");

        return getDateToFloat(rs, step);
    }

    @Override
    public float getCTR() throws SQLException {
        String select = "COUNT(*)";

        String fromClicks = clickTable();
        String joinClicks = makeJoins(clickTable(), impTable(), userTable());
        String whereClicks = "";

        String fromImpr = impTable();
        String joinImpr = makeJoins(impTable(), userTable());
        String whereImpr = "";

        String[] selects = {select, select};
        String[] froms = {fromClicks, fromImpr};
        String[] joins = {joinClicks, joinImpr};
        String[] wheres = {whereClicks, whereImpr};
        String[] asSuffixes = {"Clicks", "Impressions"};
        ResultSet rs = buildAndExecuteSubStatements(selects, froms, joins, wheres, asSuffixes);

        rs.next();
        float clickCount = rs.getFloat("Clicks");
        float impCount = rs.getFloat("Impressions");
        return clickCount/impCount;
    }

    @Override
    public Map<Date, Float> getFullCTR(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + clickTable() + ".Date, " + stepToDate(step) + ") AS CDated, COUNT(*) AS clickCount";
        String from = clickTable();
        String join = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        String statement = buildStatement(select, from, join, where, "GROUP BY `CDated`");

        String select2 = "DATE_FORMAT(" + impTable() + ".Date, " + stepToDate(step) + ") AS IDated, COUNT(*) AS impCount";
        String from2 = impTable();
        String join2 = makeJoins(impTable(), userTable());
        String where2 = "";
        String statement2 = buildStatement(select2, from2, join2, where2, "GROUP BY `IDated` ORDER BY `IDated`");

        String statementFinal = "SELECT IDated, (clickCount/impCount)  FROM (" + statement2 + ") AS impressions LEFT OUTER JOIN (" + statement + ") AS clicks ON impressions.IDated = clicks.CDated";

        ResultSet rs = dbHelper.getConnection().createStatement().executeQuery(statementFinal);

        return getDateToFloat(rs, step);
    }

    @Override
    public float getCPA() throws SQLException {
        String selectCost = "SUM(Cost)";
        String fromCost = impTable();
        String joinCost = makeJoins(impTable(), userTable());
        String whereCost = "";

        String selectConv = "COUNT(Conversion = 1)";
        String fromConv = servTable();
        String joinConv = makeJoins(servTable(), impTable(), userTable());
        String whereConv = "";

        String[] selects = {selectCost, selectConv};
        String[] froms = {fromCost, fromConv};
        String[] joins = {joinCost, joinConv};
        String[] wheres = {whereCost, whereConv};
        String[] asSuffixes = {"totalCost", "convCount"};

        ResultSet rs = buildAndExecuteSubStatements(selects, froms, joins, wheres, asSuffixes);

        rs.next();
        float totalCost = rs.getFloat("totalCost");
        float convCount = rs.getFloat("convCount");
        return totalCost/convCount;
    }

    @Override
    public Map<Date, Float> getFullCPA(Step step) throws SQLException {
        String select = "DATE_FORMAT(EntryDate, " + stepToDate(step) + ") AS SDated, COUNT(Conversion = 1) AS convCount";
        String from = servTable();
        String join = makeJoins(servTable(), impTable(), userTable());
        String where = "";
        String statement = buildStatement(select, from, join, where, "GROUP BY `SDated`");

        String select2 = "DATE_FORMAT(" + impTable() + ".Date, " + stepToDate(step) + ") AS IDated, SUM(Cost) AS costTotal";
        String from2 = impTable();
        String join2 = makeJoins(impTable(), userTable());
        String where2 = "";
        String statement2 = buildStatement(select2, from2, join2, where2, "GROUP BY `IDated`");

        String statementFinal = "SELECT IDated, (costTotal/convCount)  FROM (" + statement2 + ") AS impressions LEFT OUTER JOIN (" + statement + ") AS servs ON impressions.IDated = servs.SDated";

        ResultSet rs = dbHelper.getConnection().createStatement().executeQuery(statementFinal);

        return getDateToFloat(rs, step);
    }

    @Override
    public float getCPC() throws SQLException {
        float totalCost = getTotalCost();
        float clicksNum = getClicksNumber();

        return totalCost/clicksNum;
    }

    @Override
    public Map<Date, Float> getFullCPC(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + clickTable() + ".Date, " + stepToDate(step) + ") AS CDated, COUNT(*) AS clickCount";
        String from = clickTable();
        String join = makeJoins(clickTable(), impTable(), userTable());
        String where = "";
        String statement = buildStatement(select, from, join, where, "GROUP BY `CDated`");

        String select2 = "DATE_FORMAT(" + impTable() + ".Date, " + stepToDate(step) + ") AS IDated, SUM(Cost) AS costTotal";
        String from2 = impTable();
        String join2 = makeJoins(impTable(), userTable());
        String where2 = "";
        String statement2 = buildStatement(select2, from2, join2, where2, "GROUP BY `IDated`");

        String statementFinal = "SELECT IDated, (costTotal/clickCount)  FROM (" + statement2 + ") AS impressions LEFT OUTER JOIN (" + statement + ") AS clicks ON impressions.IDated = clicks.CDated";

        ResultSet rs = dbHelper.getConnection().createStatement().executeQuery(statementFinal);

        return getDateToFloat(rs, step);
    }

    @Override
    public float getCPM() throws SQLException {
        return (getTotalCost()/getImpressionsNumber()) * 1000;
    }

    @Override
    public Map<Date, Float> getFullCPM(Step step) throws SQLException {
        Map <Date, Float> result = getFullCost(step);
        for (Date date:result.keySet()){
            result.put(date, result.get(date) * 1000);
        }
        return result;
    }

    @Override
    public float getBounceRate() throws SQLException {
        return (float)getBouncesNumber()/getClicksNumber();
    }

    @Override
    public Map<Date, Float> getFullBounceRate(Step step) throws SQLException {
        Map<Date, Integer> bounces = getFullBounces(step);
        Map<Date, Integer> clicks = getFullClicks(step);
        Map<Date, Float> result = new HashMap<>();

        for (Date date : bounces.keySet()){
            result.put(date, (float) bounces.get(date)/clicks.get(date));
        }

        return result;
    }

    @Override
    public Map<Date, Set<String>> getFullUsers(Step step) throws SQLException {
        String select = "DATE_FORMAT(" + impTable() + ".Date, " + stepToDate(step) + ") As Dated, " + impTable() + ".ID";
        String from = impTable();
        String join = makeJoins(impTable(), userTable());
        String where = "";

        ResultSet rs = buildAndExecuteStatement(select, from, join, where, "ORDER BY `Dated`");

        Map<Date, Set<String>> result = new HashMap<>();
        while(rs.next()){
            Date date = sqlDateToJavaDate(rs.getString(1), step);
            String id = rs.getString(2);

            if (result.containsKey(date)) {
                result.get(date).add(id);
            } else {
                HashSet<String> hs = new HashSet<>();
                hs.add(id);
                result.put(date, hs);
            }
        }

        return result;
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
