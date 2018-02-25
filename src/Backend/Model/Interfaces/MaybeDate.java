package Backend.Model.Interfaces;

import java.util.Date;

/**
 * Created by Benedict on 24/02/2018.
 * A method to model data that may or may not contain a date
 * Basically trying to replicate Haskell's 'Maybe' type
 */
public class MaybeDate
{
    private Date date;
    private boolean populated;

    public MaybeDate()
    {
        populated = false;
    }

    public MaybeDate(Date date)
    {
        this.date = date;
        populated = true;
    }

    //I feel sure there is a better way to do this using exceptions,
    //But I just want to put this down quickly.
    public boolean hasDate() {return populated;}
    public Date getDate() {return date;}
}
