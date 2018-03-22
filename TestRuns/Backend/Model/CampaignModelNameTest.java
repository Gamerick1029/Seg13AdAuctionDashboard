package Backend.Model;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CampaignModelNameTest extends TestCase {

    private List<ClickLog> clickData;
    private List<ImpressionLog> impressionData;
    private List<ServerLog> serverData;
    File currentDirFile = new File(".");
    String path = currentDirFile.getAbsolutePath().substring(0,currentDirFile.getAbsolutePath().length()-1);

    public void testGetImpressionsNumber() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));

            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void testGetClicksNumber() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals(10, cm.getClicksNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetUniquesNumber() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals(8, cm.getUniquesNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBouncesNumber() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals(5, cm.getBouncesNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetConversionsNumber() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals(2, cm.getConversionsNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetTotalCost() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 100.00, cm.getTotalCost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCTR() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 1, cm.getCTR());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPA() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 50.0 , cm.getCPA());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPC() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 10.0 , cm.getCPC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetCPM() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 10000.0 , cm.getCPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBounceRate() {
        try {
            CampaignModel cm = new CampaignModel(new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            Assert.assertEquals((float) 0.5 , cm.getBounceRate());
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
            CampaignModelName cm = new CampaignModelName("CocaCola",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Assert.assertEquals(8 , cm.getImpressionsByInterval(start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetAllImpressionsByIntervalMultipleValSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("CocaCola",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:00");
            Assert.assertEquals(3 , (int) cm.getImpressionsByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsByIntervalSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Sprite",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:10");
            Assert.assertEquals((int) 1 , (int)cm.getImpressionsByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsNullPointerException() {
        try {
            CampaignModelName cm = new CampaignModelName("Sprite",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 13:00:00");
            Date specific = dateFormat.parse("2015-12-04 12:04:10");
            boolean thrown = false;
            try {
                 int test = cm.getImpressionsByInterval(start, end).get(specific);
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
            CampaignModelName cm = new CampaignModelName("Sprite",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:00");
            Assert.assertEquals((int) 5 , (int)cm.getClicksByInterval(start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((int) 1 , (int)cm.getClicksByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalMultipleValDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((int) 2 , (int)cm.getClicksByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetClicksByIntervalNullPointerException() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2045-01-13 08:57:55");
            boolean throwIt = false;
            try {
                Assert.assertEquals((int) 2, (int) cm.getClicksByInterval(start, end).get(specific));
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
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Assert.assertEquals((int) 5 , (int)cm.getUniquesByInterval(start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalMultipleValDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((int) 2 , (int)cm.getUniquesByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((int) 1 , (int)cm.getUniquesByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetUniquesByIntervalNullPointerException() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("3212-01-13 08:58:48");
            boolean throwIt = false;
            try {
                int test = cm.getUniquesByInterval(start, end).get(specific);
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
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 12:00:00");
            Date end = dateFormat.parse("2015-01-13 14:00:00");
            Assert.assertEquals((int) 7 , (int)cm.getBouncesByInterval(start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalMultipleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:01:21");
            Assert.assertEquals((int) 3 , (int)cm.getBouncesByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:02");
            Assert.assertEquals((int) 1 , (int)cm.getBouncesByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetBouncesByIntervalNullPointer() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:54:02");
            boolean throwIt = false;
            try {
                int tests = (int) cm.getBouncesByInterval(start, end).get(specific);
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
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Assert.assertEquals((int) 1 , (int)cm.getConversionsByInterval(start,end).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetConversionsByIntervalSingleDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:13");
            Assert.assertEquals((int) 1 , (int)cm.getConversionsByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetConversionsByIntervalNullPointerException() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2044-01-01 12:04:13");
            boolean throwIt = false;
            try {
                Assert.assertEquals((int) 1, (int) cm.getConversionsByInterval(start, end).get(specific));
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
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log2.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:00:00");
            Date end = dateFormat.parse("2015-01-01 14:00:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:13");
            Assert.assertEquals((int) 2 , (int)cm.getConversionsByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetCostByIntervalSpecificDate() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log2.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:56:00");
            Date end = dateFormat.parse("2015-01-13 08:59:00");
            Date specific = dateFormat.parse("2015-01-13 08:58:48");
            Assert.assertEquals((float) 12.4 , (float)cm.getCostByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testNullPointerExcpetionGetCostInterval() {
        try {
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log2.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            boolean thrown = false;

            try {
                Date start = dateFormat.parse("2015-01-01 12:00:00");
                Date end = dateFormat.parse("2015-01-01 14:00:00");
                Date specific = dateFormat.parse("2015-01-01 12:04:13");
                float test = (float)cm.getCostByInterval(start,end).get(specific);
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
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log2.csv"));
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:00");
            Date end = dateFormat.parse("2015-01-13 09:00:00");
            Date specific = dateFormat.parse("2015-01-13 08:57:55");
            Assert.assertEquals((float) 20.0 , (float)cm.getCostByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCostByInterval() {try {
        CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat.parse("2015-01-13 08:57:00");
        Date end = dateFormat.parse("2015-01-13 09:00:00");
        Assert.assertEquals((float) 69.2 , (float)cm.getOverallCostByInterval(start,end));
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void testGetOverallImpressionsByInterval() {
        try{
        CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat.parse("2015-01-01 12:00:59");
        Date end = dateFormat.parse("2015-01-01 12:05:00");
        Assert.assertEquals((int) 4 , (int)cm.getOverallImpressionsByInterval(start,end));
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    public void testGetOverallClicksByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:57:55");
            Date end = dateFormat.parse("2015-01-13 08:59:12");
            Assert.assertEquals((int) 2 , (int)cm.getOverallClicksByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallUniquesByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-13 08:56:55");
            Date end = dateFormat.parse("2015-01-13 08:59:12");
            Assert.assertEquals((int) 4 , (int)cm.getOverallUniquesByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallBouncesByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:04:16");
            Assert.assertEquals((int) 4 , (int)cm.getOverallBouncesByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOverallConversionsByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((int) 1 , (int)cm.getOverallConversionsByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------
    public void testGetCTRByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_logMetrics.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Date specific = dateFormat.parse("2015-01-01 12:04:29");
            Assert.assertEquals((float) 1.0 , (float)cm.getCTRByInterval(start,end).get(specific));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetOverallCTRByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_logMetrics.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 1.0 , (float)cm.getOverallCTRByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPAByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_logMetrics.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 77.299995 , (float)cm.getOverallCPAByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPCByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_logMetrics.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 12.883332 , (float)cm.getOverallCPCByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetOverallCPMByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_logMetrics.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 12883.332 , (float)cm.getOverallCPMByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void testGetOverallBounceRateByInterval() {
        try{
            CampaignModelName cm = new CampaignModelName("Fanta",new File(path + "TestRuns/Backend/Model/TestSamples/click_logMetrics.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/impression_log.csv"),new File(path+"TestRuns/Backend/Model/TestSamples/server_log.csv"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = dateFormat.parse("2015-01-01 12:01:21");
            Date end = dateFormat.parse("2015-01-01 12:30:00");
            Assert.assertEquals((float) 1.0 , (float)cm.getOverallBounceRateByInterval(start,end));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}