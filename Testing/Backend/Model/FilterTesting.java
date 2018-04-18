package Backend.Model;

import Backend.Model.Interfaces.Filter;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Backend.Model.Interfaces.Step.DAY;

public class FilterTesting extends TestCase {

    CampaignModelDB cm;

    {
        try {

            cm = new CampaignModelDB("JUnit");
            cm.setFilter(new Filter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsNumberNoMale() {
        try {
            cm.getFilter().genderMale = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(8, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNoFemale() {
        try {
            cm.getFilter().genderMale = true;
            cm.getFilter().genderFemale = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(2, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNoOther() {
        try {
            cm.getFilter().genderMale = true;
            cm.getFilter().genderFemale = true;
            cm.getFilter().genderOther = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsNumberNoBelow25() {
        try {
            cm.getFilter().genderOther = true;
            cm.getFilter().ageBelow25 = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(7, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNo25To34() {
        try {

            cm.getFilter().ageBelow25 = true;
            cm.getFilter().age25to34  = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(8, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNo35To44() {
        try {

            cm.getFilter().age25to34  = true;
            cm.getFilter().age35to44 = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(9, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNo45To54() {
        try {
            cm.getFilter().age35to44  = true;
            cm.getFilter().age45to54  = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(9, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetImpressionsNumberAbove54() {
        try {
            cm.getFilter().age45to54  = true;
            cm.getFilter().ageAbove54  = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(7, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetImpressionsNumberNoLow() {
        try {
            cm.getFilter().ageAbove54  = true;
            cm.getFilter().incomeLow = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(7, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetImpressionsNumberNoMedium() {
        try {
            cm.getFilter().incomeLow = true;
            cm.getFilter().incomeMedium = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(4, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetImpressionsNumberNoHigh() {
        try {
            cm.getFilter().incomeMedium = true;
            cm.getFilter().incomeHigh = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(9, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void testGetNoTravel() {
        try {
            cm.getFilter().contextTravel = false;
            cm.getFilter().incomeHigh = true;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetNoNews() {
        try {
            cm.getFilter().contextTravel = true;
            cm.getFilter().contextNews = false;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(6, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetNoHobbies() {
        try {
            cm.getFilter().contextHobbies = false;
            cm.getFilter().contextNews = true;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetNoMedia() {
        try {
            cm.getFilter().contextMedia = false;
            cm.getFilter().contextHobbies = true;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetNoBlog() {
        try {
            cm.getFilter().contextBlog = false;
            cm.getFilter().contextMedia = true;
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(6, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void testGetNoShopping() {
        try {
            cm.getFilter().contextBlog = true;
            cm.getFilter().contextShopping = false;
            cm.setFilter(cm.getFilter());
            for(Date d:cm.getFullImpressions(DAY).keySet())
            System.out.println(d);
            Assert.assertEquals(8, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // It does not take the hours
    public void testDateFilterWithinRange(){
        try {
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            Date start = dateFormat.parse("2015-01-01 00:00:00");

            Date end = dateFormat.parse("2015-01-04 00:00:00");

            cm.getFilter().contextShopping = true;
            cm.getFilter().setStartDate(start);
            cm.getFilter().setEndDate(end);
            cm.setFilter(cm.getFilter());
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testDateFilterNotWithinRange(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            Date start = dateFormat.parse("2015-01-03 12:00:00");

            Date end = dateFormat.parse("2015-01-04 13:00:00");

            cm.getFilter().contextShopping = true;
            cm.getFilter().setStartDate(start);
            cm.getFilter().setEndDate(end);
            cm.setFilter(cm.getFilter());

                Assert.assertEquals(0, cm.getImpressionsNumber());}
                catch (SQLException e) {
                e.printStackTrace();
            }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
