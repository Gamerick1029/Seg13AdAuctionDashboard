package Backend.Model;

import Backend.Model.Interfaces.Filter;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Backend.Model.Interfaces.StepHolder.Step.*;

public class FilterTesting extends TestCase {
    int result;
    CampaignModelDB cm;

    {
        try {

            cm = new CampaignModelDB("JUnit");
            cm.setFilter(new Filter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testGetImpressionsNumberAllFilters() {

            cm.getFilter().genderMale = true;
            cm.getFilter().genderFemale = true;
            cm.getFilter().genderOther = true;
            cm.getFilter().contextShopping = true;
            cm.getFilter().contextHobbies = true;
            cm.getFilter().contextTravel = true;
            cm.getFilter().incomeLow = true;
            cm.getFilter().incomeMedium = true;
            cm.getFilter().incomeHigh = true;
            cm.getFilter().ageBelow25 = true;
            cm.getFilter().age25to34 = true;
            cm.getFilter().age35to44 = true;
            cm.getFilter().age45to54 = true;
            cm.getFilter().ageAbove54 = true;
            cm.getFilter().contextNews = true;
            cm.getFilter().contextMedia = true;
            cm.getFilter().contextBlog = true;
            cm.setFilter(cm.getFilter());
        try {
            result = cm.getImpressionsNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Testing all filters set to true:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
            Assert.assertEquals(10, result);


    }

    public void testGetImpressionsNumberNoFilter() {
        try {
            cm.getFilter().genderMale = false;
            cm.getFilter().genderFemale = false;
            cm.getFilter().genderOther = false;
            cm.getFilter().contextShopping = false;
            cm.getFilter().contextHobbies = false;
            cm.getFilter().contextTravel = false;
            cm.getFilter().incomeLow = false;
            cm.getFilter().incomeMedium = false;
            cm.getFilter().incomeHigh = false;
            cm.getFilter().ageBelow25 = false;
            cm.getFilter().age25to34 = false;
            cm.getFilter().age35to44 = false;
            cm.getFilter().age45to54 = false;
            cm.getFilter().ageAbove54 = false;
            cm.getFilter().contextNews = false;
            cm.getFilter().contextMedia = false;
            cm.getFilter().contextBlog = false;
            cm.getFilter().setStartDate(null);
            cm.getFilter().setEndDate(null);
            cm.setFilter(cm.getFilter());
            result = cm.getImpressionsNumber();

            System.out.println("Testing all filters set to false:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
            Assert.assertEquals(10, cm.getImpressionsNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void testGetImpressionsNumberNoMale() {
        try {
            cm.getFilter().genderMale = false;
            cm.setFilter(cm.getFilter());
            result = cm.getImpressionsNumber();

            System.out.println("Testing male filter set to false:");
            System.out.println("    Expected value is: 8, " + "Actual Value is: "+ result);
            System.out.println();
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

            result = cm.getImpressionsNumber();

            System.out.println("Testing female filter set to false:");
            System.out.println("    Expected value is: 2, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing other filter set to false:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing noBelow25 filter set to false:");
            System.out.println("    Expected value is: 7, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing age25to34 filter set to false:");
            System.out.println("    Expected value is: 8, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing age34to44 filter set to false:");
            System.out.println("    Expected value is: 9, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing age45to54 filter set to false:");
            System.out.println("    Expected value is: 9, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing above54 filter set to false:");
            System.out.println("    Expected value is: 7, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing incomeLow filter set to false:");
            System.out.println("    Expected value is: 7, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing incomeMedium filter set to false:");
            System.out.println("    Expected value is: 4, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing incomeHigh filter set to false:");
            System.out.println("    Expected value is: 9, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing travel filter set to false:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing news filter set to false:");
            System.out.println("    Expected value is: 6, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing hobbies filter set to false:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing media filter set to false:");
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing blog filter set to false:");
            System.out.println("    Expected value is: 6, " + "Actual Value is: "+ result);
            System.out.println();
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
            for(Date d:cm.getFullImpressions(DAY).keySet()) ;
            result = cm.getImpressionsNumber();

            System.out.println("Testing shopping filter set to false:");
            System.out.println("    Expected value is: 8, " + "Actual Value is: "+ result);
            System.out.println();
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
            result = cm.getImpressionsNumber();

            System.out.println("Testing Start-End Date filter with values within the range: "); System.out.println( " Start Date: " + start ); System.out.println(" End Date: " + end);
            System.out.println("    Expected value is: 10, " + "Actual Value is: "+ result);
            System.out.println();
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

            System.out.println("Testing Start-End Date filter with values within the range: "); System.out.println( " Start Date: " + start ); System.out.println(" End Date: " + end);
            System.out.println("    Expected value is: 0, " + "Actual Value is: "+ result);
            System.out.println();
            Assert.assertEquals(0, cm.getImpressionsNumber());}
                catch (SQLException e) {
                e.printStackTrace();
            }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
