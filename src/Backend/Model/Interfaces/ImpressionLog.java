package Backend.Model.Interfaces;

import java.util.Date;

/**
 * Created by Benedict on 24/02/2018.
 * Used by classes that store data from the Impression Log
 */
public interface ImpressionLog
{
    Date getDate();
    String getID();

    /*
    Just the basic idea of having a range as an element of this
     */
    String getAgeRange();

    /*
    External enum declared for gender, just in case
     */
    Gender getGender();

    /*
    Income - Enum or String?
     */
    String getIncome();

    /*
    Another discussion topic
    Would we rather have context as an Enum or String?
     */
    String getContext();

    float getImpressionCost();


}
