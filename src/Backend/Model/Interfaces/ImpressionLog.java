package Backend.Model.Interfaces;

import java.text.SimpleDateFormat;

/**
 * Created by Benedict on 24/02/2018.
 * Used by classes that store data from the Impression Log
 */
public interface ImpressionLog
{
    SimpleDateFormat getDate();
    String getID();

    /*
    Just the basic idea of having a range as an element of this
     */
    CustomRange getAgeRange();

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
