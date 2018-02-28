package Backend.Model;

import Backend.Model.Interfaces.ClickLog;

import java.util.Date;

/**
 * Created by Benedict on 28/02/2018.
 * Basic holder for ClickData
 */
public class ClickData implements ClickLog
{

    private Date date;
    private String id;
    private float cost;

    @Override
    public Date getDate()
    {
        return null;
    }

    @Override
    public String getID()
    {
        return null;
    }

    @Override
    public float getCost()
    {
        return 0;
    }
}
