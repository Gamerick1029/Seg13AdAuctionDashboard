package Backend.Model;

import Backend.Model.Interfaces.Filter;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

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

    @Test
    public void testGetName() {
        assertEquals("JUnit" , cm.getName());
    }

    @Test
    public void testGetImpressionsNumber() {
        try {
            assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testGetFullImpressionsNoOfElements() {
    try {
        assertEquals(1, cm.getFullImpressions(DAY).size());
        assertEquals(1, cm.getFullImpressions(WEEK).size());
        assertEquals(1, cm.getFullImpressions(MONTH).size());
        //cm.getFullImpressions(DAY).get()

    }catch (SQLException e){
        e.printStackTrace();
    }
    }

    public void testGetFullImpressionsValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try {
            assertEquals(i, cm.getFullImpressions(DAY).get(date));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetClicksNumber() {
        try {
            assertEquals(10, cm.getClicksNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicks() {
        try {
            assertEquals(1, cm.getFullClicks(DAY).size());
            assertEquals(1, cm.getFullClicks(WEEK).size());
            assertEquals(1, cm.getFullClicks(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetUniquesNumber() {
        try {
            assertEquals(8, cm.getUniquesNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullUniques() {
        try {
            assertEquals(1, cm.getFullUniques(DAY).size());
            assertEquals(1, cm.getFullUniques(WEEK).size());
            assertEquals(1, cm.getFullUniques(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetBouncesNumber() {
        try {
            assertEquals(2, cm.getBouncesNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounces() {
        try {
            assertEquals(1, cm.getFullBounces(DAY).size());
            assertEquals(1, cm.getFullBounces(WEEK).size());
            assertEquals(1, cm.getFullBounces(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetConversionsNumber() {
        try {
            assertEquals(2, cm.getConversionsNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullConversions() {
        try {
            assertEquals(1, cm.getFullConversions(DAY).size());
            assertEquals(1, cm.getFullConversions(WEEK).size());
            assertEquals(1, cm.getFullConversions(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetTotalCost() {
        try {
            assertEquals(100.00, cm.getTotalCost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCost() {
        try {
            assertEquals(1, cm.getFullCost(DAY).size());
            assertEquals(1, cm.getFullCost(WEEK).size());
            assertEquals(1, cm.getFullCost(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCTR() {
        try{
        assertEquals((float) 1, cm.getCTR());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void testGetFullCTR() {
        try {
            assertEquals(1, cm.getFullCTR(DAY).size());
            assertEquals(1, cm.getFullCTR(WEEK).size());
            assertEquals(1, cm.getFullCTR(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCPA() {
        try {
            assertEquals((float) 50.0, cm.getCPA());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPA() {
        try {
            assertEquals(1, cm.getFullCPA(DAY).size());
            assertEquals(1, cm.getFullCPA(WEEK).size());
            assertEquals(1, cm.getFullCPA(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void testGetCPC() {
        try{
            assertEquals((float) 10.0 , cm.getCPC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPC() {
        try {
            assertEquals(1, cm.getFullCPC(DAY).size());
            assertEquals(1, cm.getFullCPC(WEEK).size());
            assertEquals(1, cm.getFullCPC(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetCPM() {
        try{assertEquals((float) 10000.0 , cm.getCPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPM() {
        try {
            assertEquals(1, cm.getFullCPM(DAY).size());
            assertEquals(1, cm.getFullCPM(WEEK).size());
            assertEquals(1, cm.getFullCPM(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void testGetBounceRate() {
        try{ assertEquals((float) 0.2 , cm.getBounceRate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRate() {
        try {
            assertEquals(1, cm.getFullBounceRate(DAY).size());
            assertEquals(1, cm.getFullBounceRate(WEEK).size());
            assertEquals(1, cm.getFullBounceRate(MONTH).size());
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void testGetFullUsers() {
        try {
            assertEquals( 10 , cm.getFullUsers(DAY).get(new Date("Thu Jan 01 00:00:00 GMT 2015")).size());
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