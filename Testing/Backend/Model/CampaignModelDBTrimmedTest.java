package Backend.Model;

import Backend.Model.Interfaces.Filter;
import Backend.Model.Interfaces.Step;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import static Backend.Model.Interfaces.Step.*;

public class CampaignModelDBTrimmedTest extends TestCase {


    CampaignModelDBTrimmed cm;

    {
        try {
            cm = new CampaignModelDBTrimmed("JUnit");
            cm.setFilter(new Filter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetName() {

        Assert.assertEquals("JUnit" , cm.getName());
    }


    public void testGetImpressionsNumber() {
        try {
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    public void testGetFullImpressionsNoOfElements() {
    try {
        Assert.assertEquals(1, cm.getFullImpressions(DAY).get(new Date("Thu Jan 01 00:00:00 GMT 2015")));
       // Assert.assertEquals(1, cm.getFullImpressions(WEEK).size());
       // Assert.assertEquals(1, cm.getFullImpressions(MONTH).size());
        //cm.getFullImpressions(DAY).get()

    }catch (SQLException e){
        e.printStackTrace();
    }
    }

    public void testGetFullImpressionsValues() {
        try {
            Assert.assertEquals(1, cm.getFullImpressions(DAY).size());
            Assert.assertEquals(1, cm.getFullImpressions(WEEK).size());
            Assert.assertEquals(1, cm.getFullImpressions(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetClicksNumber() {
        try {
            Assert.assertEquals(10, cm.getClicksNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicks() {
        try {
            Assert.assertEquals(1, cm.getFullClicks(DAY).size());
            Assert.assertEquals(1, cm.getFullClicks(WEEK).size());
            Assert.assertEquals(1, cm.getFullClicks(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetUniquesNumber() {
        try {
            Assert.assertEquals(8, cm.getClicksNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullUniques() {
        try {
            Assert.assertEquals(1, cm.getFullUniques(DAY).size());
            Assert.assertEquals(1, cm.getFullUniques(WEEK).size());
            Assert.assertEquals(1, cm.getFullUniques(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetBouncesNumber() {
        try {
            Assert.assertEquals(5, cm.getBouncesNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounces() {
        try {
            Assert.assertEquals(1, cm.getFullBounces(DAY).size());
            Assert.assertEquals(1, cm.getFullBounces(WEEK).size());
            Assert.assertEquals(1, cm.getFullBounces(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetConversionsNumber() {
        try {
            Assert.assertEquals(2, cm.getConversionsNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullConversions() {
        try {
            Assert.assertEquals(1, cm.getFullConversions(DAY).size());
            Assert.assertEquals(1, cm.getFullConversions(WEEK).size());
            Assert.assertEquals(1, cm.getFullConversions(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetTotalCost() {
        try {
            Assert.assertEquals(100.00, cm.getTotalCost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCost() {
        try {
            Assert.assertEquals(1, cm.getFullCost(DAY).size());
            Assert.assertEquals(1, cm.getFullCost(WEEK).size());
            Assert.assertEquals(1, cm.getFullCost(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCTR() {
        try{
        Assert.assertEquals((float) 1, cm.getCTR());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void testGetFullCTR() {
        try {
            Assert.assertEquals(1, cm.getFullCTR(DAY).size());
            Assert.assertEquals(1, cm.getFullCTR(WEEK).size());
            Assert.assertEquals(1, cm.getFullCTR(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCPA() {
        try {
            Assert.assertEquals((float) 50.0, cm.getCPA());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPA() {
        try {
            Assert.assertEquals(1, cm.getFullCPA(DAY).size());
            Assert.assertEquals(1, cm.getFullCPA(WEEK).size());
            Assert.assertEquals(1, cm.getFullCPA(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void testGetCPC() {
        try{
            Assert.assertEquals((float) 10.0 , cm.getCPC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPC() {
        try {
            Assert.assertEquals(1, cm.getFullCPC(DAY).size());
            Assert.assertEquals(1, cm.getFullCPC(WEEK).size());
            Assert.assertEquals(1, cm.getFullCPC(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCPM() {
        try{Assert.assertEquals((float) 10000.0 , cm.getCPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPM() {
        try {
            Assert.assertEquals(1, cm.getFullCPM(DAY).size());
            Assert.assertEquals(1, cm.getFullCPM(WEEK).size());
            Assert.assertEquals(1, cm.getFullCPM(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetBounceRate() {
        try{ Assert.assertEquals((float) 0.5 , cm.getBounceRate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRate() {
        try {
            Assert.assertEquals(1, cm.getFullBounceRate(DAY).size());
            Assert.assertEquals(1, cm.getFullBounceRate(WEEK).size());
            Assert.assertEquals(1, cm.getFullBounceRate(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void testGetFullUsers() {
        try {
            Assert.assertEquals( 10 , cm.getFullUsers(DAY).get(new Date("Thu Jan 01 00:00:00 GMT 2015")).size());
        } catch (SQLException e1) {
            e1.printStackTrace();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void testGetFilter() {
    }

    public void testSetFilter() {
    }
}