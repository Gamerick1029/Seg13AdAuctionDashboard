package Backend.Model;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.File;
import java.util.List;

public class CampaignModelTest extends TestCase {
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
}