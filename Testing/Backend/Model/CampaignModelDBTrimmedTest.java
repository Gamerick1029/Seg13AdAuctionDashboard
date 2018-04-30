package Backend.Model;

import Backend.Model.Interfaces.Filter;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

import static Backend.Model.Interfaces.StepHolder.Step.*;
import static org.junit.Assert.fail;

public class CampaignModelDBTrimmedTest extends TestCase {


    CampaignModelDB cm;

    {
        try {
            cm = new CampaignModelDB("JUnit");
            cm.setFilter(new Filter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    CampaignModelDB threeWeeks; {
        try {
            threeWeeks = new CampaignModelDB("jahdjasdjag");
            threeWeeks.setFilter(new Filter());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetName() {
        assertEquals("JUnit", cm.getName());
    }

    // Testing steps

    public void testGetFullImpressionsNoOfElements() {
        try {
            assertEquals(1, cm.getFullImpressions(DAY).size());
            assertEquals(1, cm.getFullImpressions(WEEK).size());
            assertEquals(1, cm.getFullImpressions(MONTH).size());
            //cm.getFullImpressions(DAY).get()

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void testGetFullImpressionsNoOfElementsthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullImpressions(DAY).size());
            assertEquals(3, threeWeeks.getFullImpressions(WEEK).size());
            assertEquals(1, threeWeeks.getFullImpressions(MONTH).size());
            //cm.getFullImpressions(DAY).get()

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicksthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullClicks(DAY).size());
            assertEquals(3, threeWeeks.getFullClicks(WEEK).size());
            assertEquals(1, threeWeeks.getFullClicks(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullUniquesthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullUniques(DAY).size());
            assertEquals(3, threeWeeks.getFullUniques(WEEK).size());
            assertEquals(1, threeWeeks.getFullUniques(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBouncesthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullBounces(DAY).size());
            assertEquals(3, threeWeeks.getFullBounces(WEEK).size());
            assertEquals(1, threeWeeks.getFullBounces(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullConversionsthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullConversions(DAY).size());
            assertEquals(3, threeWeeks.getFullConversions(WEEK).size());
            assertEquals(1, threeWeeks.getFullConversions(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void testGetFullCostthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullCost(DAY).size());
            assertEquals(3, threeWeeks.getFullCost(WEEK).size());
            assertEquals(1, threeWeeks.getFullCost(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void testGetFullCTRthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullCTR(DAY).size());
            assertEquals(3, threeWeeks.getFullCTR(WEEK).size());
            assertEquals(1, threeWeeks.getFullCTR(MONTH).size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPAthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullCPA(DAY).size());
            assertEquals(3, threeWeeks.getFullCPA(WEEK).size());
            assertEquals(1, threeWeeks.getFullCPA(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPCthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullCPC(DAY).size());
            assertEquals(3, threeWeeks.getFullCPC(WEEK).size());
            assertEquals(1, threeWeeks.getFullCPC(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPMthreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullCPM(DAY).size());
            assertEquals(3, threeWeeks.getFullCPM(WEEK).size());
            assertEquals(1, threeWeeks.getFullCPM(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRatethreeWeeks() {
        try {
            assertEquals(14, threeWeeks.getFullBounceRate(DAY).size());
            assertEquals(3, threeWeeks.getFullBounceRate(WEEK).size());
            assertEquals(1, threeWeeks.getFullBounceRate(MONTH).size());
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetTotalCost() {
        try {
            assertEquals((float) 0.014562, cm.getTotalCost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCost() {
        try {
            assertEquals(1, cm.getFullCost(DAY).size());
            assertEquals(1, cm.getFullCost(WEEK).size());
            assertEquals(1, cm.getFullCost(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetCTR() {
        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetCPA() {
        try {
            assertEquals((float) 0.0014561999, cm.getCPA());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPA() {
        try {
            assertEquals(1, cm.getFullCPA(DAY).size());
            assertEquals(1, cm.getFullCPA(WEEK).size());
            assertEquals(1, cm.getFullCPA(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetCPC() {
        try {
            assertEquals((float) 0.0014561999, cm.getCPC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPC() {
        try {
            assertEquals(1, cm.getFullCPC(DAY).size());
            assertEquals(1, cm.getFullCPC(WEEK).size());
            assertEquals(1, cm.getFullCPC(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetCPM() {
        try {
            assertEquals((float) 1.4561999, cm.getCPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPM() {
        try {
            assertEquals(1, cm.getFullCPM(DAY).size());
            assertEquals(1, cm.getFullCPM(WEEK).size());
            assertEquals(1, cm.getFullCPM(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetBounceRate() {
        try {
            assertEquals((float) 0.2, cm.getBounceRate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRate() {
        try {
            assertEquals(1, cm.getFullBounceRate(DAY).size());
            assertEquals(1, cm.getFullBounceRate(WEEK).size());
            assertEquals(1, cm.getFullBounceRate(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetFullUsers() {
        try {
            assertEquals(10, cm.getFullUsers(DAY).get(new Date("Thu Jan 01 00:00:00 GMT 2015")).size());
        } catch (SQLException e1) {
            e1.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get maps with specific Date Tests by Day
    public void testGetFullImpressionsValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try {
            assertEquals(i, cm.getFullImpressions(DAY).get(date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBouncesValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 2;
        try {
            assertEquals(i, cm.getFullBounces(DAY).get(date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicksValues() {

        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(i, cm.getFullClicks(DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRateValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals((float) 0.2, cm.getFullBounceRate(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullConversionsValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 2;
        try

        {
            assertEquals(i, cm.getFullConversions(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPAValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals((float) 0.0014562, cm.getFullCPA(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPCValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        try

        {
            assertEquals((float) 0.0014562, cm.getFullCPC(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPMValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        try

        {
            assertEquals((float) 14.561999, cm.getFullCPM(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCTRValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals((float) 1.0, cm.getFullCTR(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullUniquesValues() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 8;
        try

        {
            assertEquals(i, cm.getFullUniques(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullUsersValuesSpecificUser() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(true, cm.getFullUsers(
                    DAY).get(date).contains("4620864431353617408"));


        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullUsersValuesUsersNo() {
        Date date = new Date("Thu Jan 01 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(10, cm.getFullUsers(
                    DAY).get(date).size());

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }




    // Invalid Data queries Tests
    public void testGetFullImpressionsValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 10;
        try {
            assertEquals(null, cm.getFullImpressions(DAY).get(date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBouncesValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 2;
        try {
            assertEquals(null, cm.getFullBounces(DAY).get(date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicksValuesInvalidDate() {

        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(null, cm.getFullClicks(DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRateValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(null, cm.getFullBounceRate(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullConversionsValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 2;
        try

        {
            assertEquals(null, cm.getFullConversions(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPAValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(null, cm.getFullCPA(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPCValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        try

        {
            assertEquals(null, cm.getFullCPC(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCPMValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        try

        {
            assertEquals(null, cm.getFullCPM(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullCTRValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 10;
        try

        {
            assertEquals(null, cm.getFullCTR(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFullUniquesValuesInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Integer i = 8;
        try

        {
            assertEquals(null, cm.getFullUniques(
                    DAY).get(date));

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    @Test(expected=NullPointerException.class)
    public void testGetFullUsersValuesSpecificUserInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        Boolean thrown = false;
        Integer i = 10;
        try

        {
           cm.getFullUsers(
                    DAY).get(date).contains("4620864431353617408");


        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
             catch (NullPointerException e){
        thrown = true;

        }

        assertTrue(thrown);


    }

    public void testGetFullUsersValuesUsersInvalidDate() {
        Date date = new Date("Thu Jan 02 00:00:00 GMT 2015");
        try

        {
            assertEquals(null
                    , cm.getFullUsers(
                    DAY).get(date));


        } catch (
                SQLException e)

        {
            e.printStackTrace();
        }
    }

    public void testGetFilter() {
    }

    public void testSetFilter() {
    }
}