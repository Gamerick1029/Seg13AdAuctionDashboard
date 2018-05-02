package Backend.Model;

import Backend.Model.Interfaces.Filter;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Backend.Model.Interfaces.StepHolder.Step.DAY;
import static Backend.Model.Interfaces.StepHolder.Step.MONTH;
import static Backend.Model.Interfaces.StepHolder.Step.WEEK;

public class StepsTesting extends TestCase {

    CampaignModelDB twoWeek; {
        try {
            twoWeek = new CampaignModelDB("two_week");
            twoWeek.setFilter(new Filter());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    CampaignModelDB twoMonth; {
        try {
            twoMonth = new CampaignModelDB("two_month");
            twoMonth.setFilter(new Filter());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFullImpressionsNoOfElementsTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullImpressions(DAY).size());
            assertEquals(3, twoWeek.getFullImpressions(WEEK).size());
            assertEquals(1, twoWeek.getFullImpressions(MONTH).size());
            //cm.getFullImpressions(DAY).get()

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void testGetFullClicksTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullClicks(DAY).size());
            assertEquals(3, twoWeek.getFullClicks(WEEK).size());
            assertEquals(1, twoWeek.getFullClicks(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullUniquesTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullUniques(DAY).size());
            assertEquals(3, twoWeek.getFullUniques(WEEK).size());
            assertEquals(1, twoWeek.getFullUniques(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBouncesTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullBounces(DAY).size());
            assertEquals(3, twoWeek.getFullBounces(WEEK).size());
            assertEquals(1, twoWeek.getFullBounces(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullConversionsTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullConversions(DAY).size());
            assertEquals(3, twoWeek.getFullConversions(WEEK).size());
            assertEquals(1, twoWeek.getFullConversions(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void testGetFullCostTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullCost(DAY).size());
            assertEquals(3, twoWeek.getFullCost(WEEK).size());
            assertEquals(1, twoWeek.getFullCost(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void testGetFullCTRTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullCTR(DAY).size());
            assertEquals(3, twoWeek.getFullCTR(WEEK).size());
            assertEquals(1, twoWeek.getFullCTR(MONTH).size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPATwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullCPA(DAY).size());
            assertEquals(3, twoWeek.getFullCPA(WEEK).size());
            assertEquals(1, twoWeek.getFullCPA(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPCTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullCPC(DAY).size());
            assertEquals(3, twoWeek.getFullCPC(WEEK).size());
            assertEquals(1, twoWeek.getFullCPC(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPMTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullCPM(DAY).size());
            assertEquals(3, twoWeek.getFullCPM(WEEK).size());
            assertEquals(1, twoWeek.getFullCPM(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRateTwoWeeks() {
        try {
            assertEquals(14, twoWeek.getFullBounceRate(DAY).size());
            assertEquals(3, twoWeek.getFullBounceRate(WEEK).size());
            assertEquals(1, twoWeek.getFullBounceRate(MONTH).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /*
    // Tests for two Months Campaign
    @Test
    public void testGetFullImpressionsNoOfElementsTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullImpressions(DAY).size());
            assertEquals(9, twoMonth.getFullImpressions(WEEK).size());
            //cm.getFullImpressions(DAY).get()

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullClicksTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullClicks(DAY).size());
            assertEquals(9, twoMonth.getFullClicks(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullUniquesTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullUniques(DAY).size());
            assertEquals(9, twoMonth.getFullUniques(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBouncesTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullBounces(DAY).size());
            assertEquals(9, twoMonth.getFullBounces(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullConversionsTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullConversions(DAY).size());
            assertEquals(9, twoMonth.getFullConversions(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void testGetFullCostTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullCost(DAY).size());
            assertEquals(9, twoMonth.getFullCost(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void testGetFullCTRTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullCTR(DAY).size());
            assertEquals(9, twoMonth.getFullCTR(WEEK).size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPATwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullCPA(DAY).size());
            assertEquals(9, twoMonth.getFullCPA(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetFullCPCTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullCPC(DAY).size());
            assertEquals(9, twoMonth.getFullCPC(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullCPMTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullCPM(DAY).size());
            assertEquals(9, twoMonth.getFullCPM(WEEK).size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetFullBounceRateTwoMonth() {
        try {
            assertEquals(59, twoMonth.getFullBounceRate(DAY).size());
            assertEquals(9, twoMonth.getFullBounceRate(WEEK).size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    */
}
