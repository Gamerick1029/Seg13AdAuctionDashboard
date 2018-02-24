package Backend.Model.Interfaces;

import java.text.SimpleDateFormat;

/**
 * Created by Benedict on 24/02/2018.
 * Used by classes that store data from the server log.
 */
public interface ServerLog
{
    /*
    This is interesting because time and date are encapsulated here, and in some cases the Exit date is NA.
    So how do we represent that?
    Entry date is always populated, exit date is a Maybe
     */
    SimpleDateFormat getEntryDate();
    String getID();

    //A sop to the fact that the date may not exist
    MaybeDate getExitDate();

    /*
    In the short campaign the number of pages viewed is never more than 20.
    So I feel this can safely be an int
     */

    int getPagesViewed();


    /*
    Conversion is a yes/no field. Boolean.
     */
    boolean getConverted();


}
