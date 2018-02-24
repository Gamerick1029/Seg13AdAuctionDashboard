package Backend.Model.Interfaces;
import java.text.SimpleDateFormat;

/**
 * Created by Benedict on 24/02/2018.
 * The Interface for a class that accesses ClickLog data
 */


public interface ClickLog
{
    SimpleDateFormat getDate(); //May need to adjust this date format
    String getID();    //Confirm if this is actually a UUID.
    float getCost();     //May need a long if we need massive accuracy.
}
