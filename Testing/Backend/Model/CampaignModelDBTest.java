package Backend.Model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CampaignModelDBTest extends TestCase {

    String campaignModel = "JUnit";
    String campaignModelMets = "JUnitMetrics";



    public void testGetImpressionsNumber() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals(10, cm.getImpressionsNumber(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void testGetClicksNumber() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals(10, cm.getClicksNumber(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetUniquesNumber() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals(8, cm.getUniquesNumber(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBouncesNumber() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals(5, cm.getBouncesNumber(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetConversionsNumber() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals(2, cm.getConversionsNumber(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetTotalCost() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 100.00, cm.getTotalCost(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCTR() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 1, cm.getCTR(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPA() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 50.0 , cm.getCPA(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPC() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 10.0 , cm.getCPC(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPM() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 10000.0 , cm.getCPM(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBounceRate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            Assert.assertEquals((float) 0.5 , cm.getBounceRate(campaignModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClickData() {

    }

    public void testGetImpressionData() {
    }

    public void testGetServerData() {
    }


    public void testGetAllImpressionsByInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Assert.assertEquals(8 , cm.getImpressionsByInterval(campaignModel,start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetAllImpressionsByIntervalMultipleValSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:00");
            Assert.assertEquals(3 , (int) cm.getImpressionsByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsByIntervalSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:10");
            Assert.assertEquals((int) 1 , (int)cm.getImpressionsByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsNullPointerException() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-12-04 12:04:10");
            boolean thrown = false;
            try {
                int test = cm.getImpressionsByInterval(campaignModel,start, end).get(specific);
            } catch (NullPointerException e){
                thrown = true;

            }
            assertTrue(thrown);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetClicksByInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:00");
            Assert.assertEquals((int) 5 , (int)cm.getClicksByInterval(campaignModel,start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((int) 1 , (int)cm.getClicksByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalMultipleValDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((int) 2 , (int)cm.getClicksByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalNullPointerException() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2045-01-13 08:57:55");
            boolean throwIt = false;
            try {
                Assert.assertEquals((int) 2, (int) cm.getClicksByInterval(campaignModel,start, end).get(specific));
            }catch (NullPointerException e){
                throwIt = true;
            }
            assertTrue(throwIt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetUniquesByInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Assert.assertEquals((int) 5 , (int)cm.getUniquesByInterval(campaignModel,start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalMultipleValDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((int) 2 , (int)cm.getUniquesByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((int) 1 , (int)cm.getUniquesByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalNullPointerException() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("3212-01-13 08:58:48");
            boolean throwIt = false;
            try {
                int test = cm.getUniquesByInterval(campaignModel,start, end).get(specific);
            }
            catch (NullPointerException e){
                throwIt = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void testGetBouncesByInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 12:00:00");
            Date end = dateFormat.parse("2015-01-13 14:00:00");
            Assert.assertEquals((int) 7 , (int)cm.getBouncesByInterval(campaignModel,start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalMultipleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:21");
            Assert.assertEquals((int) 3 , (int)cm.getBouncesByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:02");
            Assert.assertEquals((int) 1 , (int)cm.getBouncesByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalNullPointer() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:54:02");
            boolean throwIt = false;
            try {
                int tests = (int) cm.getBouncesByInterval(campaignModel,start, end).get(specific);
            }catch (NullPointerException e){
                throwIt = true;
            }
            assertTrue(throwIt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetConversionsByInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Assert.assertEquals((int) 1 , (int)cm.getConversionsByInterval(campaignModel,start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetConversionsByIntervalSingleDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:13");
            Assert.assertEquals((int) 1 , (int)cm.getConversionsByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetConversionsByIntervalNullPointerException() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2044-01-01 12:04:13");
            boolean throwIt = false;
            try {
                Assert.assertEquals((int) 1, (int) cm.getConversionsByInterval(campaignModel,start, end).get(specific));
            }catch (NullPointerException e){
                throwIt = true;
            }
            assertTrue(throwIt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetConversionsByIntervalMultipleValuesOnADate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:13");
            Assert.assertEquals((int) 2 , (int)cm.getConversionsByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetCostByIntervalSpecificDate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:56:00");
            Date end = dateFormat.parse("2015-01-13 08:59:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((float) 12.4 , (float)cm.getCostByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testNullPointerExcpetionGetCostInterval() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            boolean thrown = false;

            try {
                Date start = dateFormat.parse("2015-01-01 12:00:00");
                Date end = dateFormat.parse("2015-01-01 14:00:00");
                Date specific = dateFormat.parse("2015-01-01 12:04:13");
                float test = (float)cm.getCostByInterval(campaignModel,start,end).get(specific);
            } catch (NullPointerException e) {
                thrown = true;
            }

            assertTrue(thrown);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCostByIntervalMultipleValuesOnADate() {
        try {
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((float) 20.0 , (float)cm.getCostByInterval(campaignModel,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCostByInterval() {try {
        CampaignModelDB cm = new CampaignModelDB();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat.parse("2015-01-13 08:57:00");
        Date end = dateFormat.parse("2015-01-13 09:00:00");
        Assert.assertEquals((float) 69.2 , (float)cm.getOverallCostByInterval(campaignModel,start,end));
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void testGetOverallImpressionsByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:59");
            Date end = dateFormat.parse("2015-01-01 12:05:00");
            Assert.assertEquals((int) 4 , (int)cm.getOverallImpressionsByInterval(campaignModel,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetOverallClicksByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:55");
            Date end = dateFormat.parse("2015-01-13 08:59:12");
            Assert.assertEquals((int) 2 , (int)cm.getOverallClicksByInterval(campaignModel,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallUniquesByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:56:55");
            Date end = dateFormat.parse("2015-01-13 08:59:12");
            Assert.assertEquals((int) 4 , (int)cm.getOverallUniquesByInterval(campaignModel, start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallBouncesByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:04:16");
            Assert.assertEquals((int) 4 , (int)cm.getOverallBouncesByInterval(campaignModel, start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallConversionsByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((int) 1 , (int)cm.getOverallConversionsByInterval(campaignModel, start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //------------------------------------------------------
    public void testGetCTRByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:29");
            Assert.assertEquals((float) 1.0 , (float)cm.getCTRByInterval(campaignModelMets,start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetOverallCTRByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 1.0 , (float)cm.getOverallCTRByInterval(campaignModelMets,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPAByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 77.299995 , (float)cm.getOverallCPAByInterval(campaignModelMets,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPCByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 12.883332 , (float)cm.getOverallCPCByInterval(campaignModelMets,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPMByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 12883.332 , (float)cm.getOverallCPMByInterval(campaignModelMets,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void testGetOverallBounceRateByInterval() {
        try{
            CampaignModelDB cm = new CampaignModelDB();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 1.0 , (float)cm.getOverallBounceRateByInterval(campaignModelMets,start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}