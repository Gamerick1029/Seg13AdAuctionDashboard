package Backend.Model.Interfaces;

import java.text.SimpleDateFormat;

/**
 * Created by Benedict on 24/02/2018.
 * A method to model data that may or may not contain a date
 * Basically trying to replicate Haskell's 'Maybe' type
 */
public class MaybeDate
{
    private SimpleDateFormat date;
    private boolean populated;

    public MaybeDate()
    {
        populated = false;
    }

    public MaybeDate(SimpleDateFormat date)
    {
        this.date = date;
        populated = true;
    }

    //I feel sure there is a better way to do this using exceptions,
    //But I just want to put this down quickly.
    public boolean hasDate() {return populated;}
    public SimpleDateFormat getDate() {return date;}
}
