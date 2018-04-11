package Backend.Model;

import Backend.DBHelper;
import Backend.Model.Interfaces.DataModelDBTrimmed;
import Backend.Model.Interfaces.Filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class CampaignModelDBTrimmed implements DataModelDBTrimmed {

    private String campaignName;
    private Connection connection;
    private DBHelper dbHelper;

    Filter filterDB;

    String impressionsQuery;
    String userQuery;
    String dateQuery;

    public CampaignModelDBTrimmed() throws SQLException {

        //this.campaignName = campaignName ;

        dbHelper = new DBHelper("seg", "seg13");
        connection = dbHelper.getConnection();


    }

    @Override
    public int getImpressionsNumber() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + campaignName +"_impressions;");
        int i = 0;
        while(rs.next()){
            System.out.println(rs.getString(1));
            i++;
        }
        return i;
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
    public float getCTR() throws SQLException {
        return 0;
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

    private String dateFilter(){

        return dateQuery;
    }

    private String impressionFilter(){

        return impressionsQuery;
    }

    private String userFilter(){

        impressionsQuery = null;

        if(filterDB.genderOther){
            impressionsQuery += " Gender='Other' ";
        }
        if(filterDB.genderMale && false){
            impressionsQuery += " Gender='Male' ";
        }
        if(filterDB.genderFemale){
            impressionsQuery += " Gender='Female' ";
        }
        if(filterDB.age25to34){
            impressionsQuery += " Age > 24 AND Age < 35 ";
        }
        if(filterDB.age35to44){
            impressionsQuery += " Age > 34 AND Age < 45 ";
        }
        if(filterDB.age45to54){
            impressionsQuery += " Age > 44 AND Age < 55 ";
        }
        if(filterDB.ageAbove54){
            impressionsQuery += " Age > 54 ";
        }
        if(filterDB.ageBelow20){
            impressionsQuery += " Age < 20 ";
        }
        if(filterDB.incomeHigh){
            impressionsQuery += " Gender='High' ";
        }
        if(filterDB.incomeMedium){
            impressionsQuery += " Gender='Medium' ";
        }
        if(filterDB.incomeLow){
            impressionsQuery += " Gender='Low' ";
        }


        return impressionsQuery;
    }

    private String filterToQuery(){

        String query = "";


        if(filterDB.contextBlog){

        }if(filterDB.contextHobbies){

        }
        if(filterDB.contextBlog){

        }
        if(filterDB.contextMedia){

        }
        if(filterDB.contextNews){
        }
        if(filterDB.contextShopping){
        }
        if(filterDB.incomeLow){

        }
        if(filterDB.incomeMedium){

        }
        if(filterDB.incomeHigh){

        }
        if(filterDB.genderOther){

        }
        if(filterDB.contextTravel){

        }
        if(filterDB.genderOther){

        }



        return query;

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
