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

        DBHelper.initConnection("seg", "seg13");

        dbHelper = new DBHelper();
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

    /* As I have seen in the table we cannot use a general query. So I have divided the query
     in 3 in order to do filtering on the User Table, Impression table and the dateQuery which can be used
     in all 3.
      Below are methods that fill the variable mentioned.
    */

    // Date filter
    private String dateFilter(){
        dateQuery = " Date >= " + filterDB.getStartDate() + " AND " + " Date <= " + filterDB.getEndDate() ;
        return dateQuery;
    }


    // Immpressiosn filter -- Context
    private String impressionFilter(){
        impressionsQuery = null;
        if(filterDB.contextNews){
            impressionsQuery = " Context = 'News' ";
        }
        if(filterDB.contextShopping){
            if(impressionsQuery == null){
                impressionsQuery = " Context = 'Shopping' ";
            }else{
                impressionsQuery += " OR Context = 'Shopping' ";
            }
        }
        if(filterDB.contextMedia){
            if(impressionsQuery == null){
                impressionsQuery = " Context = 'Media' ";
            }else{
                impressionsQuery += " OR Context = 'Media' ";
            }
        }
        if(filterDB.contextBlog){
            if(impressionsQuery == null){
                impressionsQuery = " Context = 'Blog' ";
            }else{
                impressionsQuery += " OR Context = 'Blog' ";
            }
        }
        if(filterDB.contextHobbies){
            if(impressionsQuery == null){
                impressionsQuery = " Context = 'Hobbies' ";
            }else{
                impressionsQuery += " OR Context = 'Hobbies' ";
            }
        }
        if(filterDB.contextTravel){
            if(impressionsQuery == null){
                impressionsQuery = " Context = 'Travel' ";
            }else{
                impressionsQuery += " OR Context = 'Travel' ";
            }
        }
        return impressionsQuery;
    }

    // This method is used within this class for DB filering.
    // It is used only for user Table.
    // Contains: Age Income and gender query.
    private String userFilter(){

        userQuery = null;

        String genderQuery = null;
        String ageQuery = null;
        String incomeQuery = null;

        /* This is just a weirder denser version
        if(filterDB.genderOther && filterDB.genderMale && filterDB.genderFemale){
            genderQuery += " Gender='Other' OR Gender='Male' OR Gender='Female' ";
        }else if(filterDB.genderOther && filterDB.genderMale && filterDB.genderFemale == false){
            genderQuery += " Gender='Other' OR Gender='Male' ";
        }else if(filterDB.genderOther && filterDB.genderMale == false && filterDB.genderFemale == false){
            genderQuery += " Gender='Other' ";
        } else if(filterDB.genderOther && filterDB.genderMale ==false && filterDB.genderFemale){
            genderQuery += " Gender='Other' OR Gender='Female' ";
        }else if(filterDB.genderOther == false && filterDB.genderMale ==false && filterDB.genderFemale == false){
            genderQuery += "";
        }else if(filterDB.genderOther == false && filterDB.genderMale ==false && filterDB.genderFemale){
            genderQuery += " Gender='Female' ";
        }else if(filterDB.genderOther == false && filterDB.genderMale ==true && filterDB.genderFemale == false){
            genderQuery += " Gender='Male' ";
        }else if(filterDB.genderOther ==false && filterDB.genderMale && filterDB.genderFemale){
            genderQuery += " Gender='Male' OR Gender='Female' ";
        }
        */

        if(filterDB.genderOther){
            if(genderQuery == null){
                genderQuery = " Gender='Other' ";
            }else{
                genderQuery += " OR Gender='Other' ";
            }
        }
        if(filterDB.genderMale){
            if(genderQuery == null){
                genderQuery = " Gender='Male' ";
            }else{
                genderQuery += " OR Gender='Male' ";
            }
        }
        if(filterDB.genderFemale){
            if(genderQuery == null){
                genderQuery = " Gender='Female' ";
            }else{
                genderQuery += " OR Gender='Female' ";
            }
        }



        if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 && filterDB.age45to54 && filterDB.ageAbove54 ){
            ageQuery += "";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 && filterDB.age45to54 && filterDB.ageAbove54 == false ){
            ageQuery += " Age < 55 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 && filterDB.age45to54 == false && filterDB.ageAbove54){
            ageQuery += " Age < 45 OR Age > 54 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 && filterDB.age45to54 == false  && filterDB.ageAbove54 == false){
            ageQuery += " Age < 45 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 == false && filterDB.age45to54 && filterDB.ageAbove54 ){
            ageQuery += " Age < 35 OR Age > 44 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 == false && filterDB.age45to54 && filterDB.ageAbove54 == false ){
            ageQuery += "  (Age < 35 OR Age > 44) AND Age < 55 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 == false && filterDB.age45to54 == false && filterDB.ageAbove54 ){
            ageQuery += "  Age < 35 OR Age > 54 ";
        }
        else if(filterDB.ageBelow20 && filterDB.age25to34 && filterDB.age35to44 == false && filterDB.age45to54 == false && filterDB.ageAbove54 == false ){
            ageQuery += "  Age < 35 ";
        } else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 && filterDB.age45to54 && filterDB.ageAbove54 ){
            ageQuery += " Age < 25 OR Age > 24";
        } else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 && filterDB.age45to54 && filterDB.ageAbove54 == false){
            ageQuery += "( Age < 25 OR Age > 24 ) AND Age < 55 ";
        }   else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 && filterDB.age45to54 == false && filterDB.ageAbove54 ){
            ageQuery += " ( ( Age < 25 OR Age > 34 ) AND Age < 45 ) OR Age > 54 ";
        }    else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 && filterDB.age45to54 == false && filterDB.ageAbove54 == false ){
            ageQuery += " ( ( Age < 25 OR Age > 34 ) AND Age < 45 ) ";
        }   else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 == false && filterDB.age45to54 && filterDB.ageAbove54 ){
            ageQuery += " ( Age < 25  OR Age > 44 ) ";
        }   else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 == false && filterDB.age45to54 && filterDB.ageAbove54 == false){
            ageQuery += " ( Age < 25  OR (Age > 44 AND < 55)) ";
        }   else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 == false && filterDB.age45to54 == false && filterDB.ageAbove54 ){
            ageQuery += " ( Age < 25  OR Age > 54) ";
        }   else if(filterDB.ageBelow20 && filterDB.age25to34 == false && filterDB.age35to44 == false && filterDB.age45to54 == false && filterDB.ageAbove54 == false){
            ageQuery += " ( Age < 25  OR (Age > 44 AND < 55)) ";
        }


        // Complementary of first part
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 || filterDB.age45to54 || filterDB.ageAbove54) ){
            ageQuery += " Age = -1";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 || filterDB.age45to54 || filterDB.ageAbove54 == false) ){
            ageQuery += " Age > 54 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 || filterDB.age45to54 == false || filterDB.ageAbove54)){
            ageQuery += " Age > 44 AND Age < 55 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 || filterDB.age45to54 == false  || filterDB.ageAbove54 == false)){
            ageQuery += " Age > 44 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 == false || filterDB.age45to54 || filterDB.ageAbove54)){
            ageQuery += " Age > 34 AND Age < 45 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 == false || filterDB.age45to54 || filterDB.ageAbove54 == false )){
            ageQuery += "  (Age > 34 AND Age < 45) OR Age > 54 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 == false || filterDB.age45to54 == false || filterDB.ageAbove54) ){
            ageQuery += "  Age > 34 AND Age < 55 ";
        }
        else if(!(filterDB.ageBelow20 || filterDB.age25to34 || filterDB.age35to44 == false || filterDB.age45to54 == false || filterDB.ageAbove54 == false) ){
            ageQuery += "  Age > 34 ";
        } else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 || filterDB.age45to54 || filterDB.ageAbove54 )){
            ageQuery += " Age > 24 AND Age < 34";
        } else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 || filterDB.age45to54 && filterDB.ageAbove54 == false)){
            ageQuery += " ( Age > 24 AND Age < 35 ) OR Age > 54 ";
        }   else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 || filterDB.age45to54 == false || filterDB.ageAbove54) ){
            ageQuery += " ( ( Age > 24 AND Age < 35 ) OR ( Age > 44 AND Age < 55 ) ";
        }    else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 || filterDB.age45to54 == false || filterDB.ageAbove54 == false )){
            ageQuery += " ( ( Age > 24 AND Age < 35 ) OR Age > 44 ) ";
        }   else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 == false || filterDB.age45to54 || filterDB.ageAbove54) ){
            ageQuery += " ( Age > 24 AND Age < 45 ) ";
        }   else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 == false || filterDB.age45to54 || filterDB.ageAbove54 == false)){
            ageQuery += " ( (Age > 24 AND < 45) OR Age > 54 ) ";
        }   else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 == false || filterDB.age45to54 == false || filterDB.ageAbove54 )){
            ageQuery += " ( Age > 24  AND Age < 55) ";
        }   else if(!(filterDB.ageBelow20 || filterDB.age25to34 == false || filterDB.age35to44 == false || filterDB.age45to54 == false || filterDB.ageAbove54 == false)){
            ageQuery += " ( Age > 24 ) ";
        }


        if(filterDB.incomeLow && filterDB.incomeMedium && filterDB.incomeHigh){
            incomeQuery += " Income='Low' OR Income='Medium' OR Income='High' ";
        }else if(filterDB.incomeLow  && filterDB.incomeMedium && filterDB.incomeHigh == false){
            incomeQuery += " Income='Low' OR Income='Medium' ";
        }else if(filterDB.incomeLow  && filterDB.incomeMedium == false && filterDB.incomeHigh == false){
            incomeQuery += " Income='Low' ";
        } else if(filterDB.incomeLow  && filterDB.incomeMedium ==false && filterDB.incomeHigh){
            incomeQuery += " Income='Low' OR Income='High' ";
        }else if(filterDB.incomeLow  == false && filterDB.incomeMedium ==false && filterDB.incomeHigh == false){
            incomeQuery += "";
        }else if(filterDB.incomeLow  == false && filterDB.incomeMedium ==false && filterDB.incomeHigh){
            incomeQuery += " Income='High' ";
        }else if(filterDB.incomeLow  == false && filterDB.incomeMedium ==true && filterDB.incomeHigh == false){
            incomeQuery += " Income='Medium' ";
        }else if(filterDB.incomeLow  ==false && filterDB.incomeMedium && filterDB.incomeHigh){
            incomeQuery += " Income='Medium' OR Income='High' ";
        }


        if(genderQuery != null && ageQuery != null && incomeQuery != null) {
            userQuery = genderQuery + " AND " + ageQuery + " AND " + incomeQuery;
        } else if (genderQuery != null && ageQuery != null && incomeQuery == null){
            userQuery = genderQuery + " AND " + ageQuery ;
        }
        else if (genderQuery != null && ageQuery == null && incomeQuery == null){
            userQuery = genderQuery ;
        }
        else if (genderQuery != null && ageQuery == null && incomeQuery != null){
            userQuery = genderQuery + " AND " + incomeQuery; ;
        }else if (!(genderQuery != null || ageQuery != null || incomeQuery != null)) {
            userQuery = "";
        } else if (!(genderQuery != null || ageQuery != null || incomeQuery == null)){
            userQuery = incomeQuery ;
        }
        else if (!(genderQuery != null || ageQuery == null || incomeQuery == null)){
            userQuery =  ageQuery + " AND " + incomeQuery ;
        }
        else if (!(genderQuery != null || ageQuery == null || incomeQuery != null)){
            userQuery = ageQuery ;
        }


        return userQuery;
    }


    @Override
    public Filter getFilter() {
        return filterDB;
    }

    @Override
    public void setFilter(Filter filter) {

        filterDB = filter;

        impressionFilter();
        userFilter();
        dateFilter();



    }
}
