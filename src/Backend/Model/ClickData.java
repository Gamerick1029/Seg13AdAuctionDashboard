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

    public ClickData(Date date, String id, float cost)
    {
        this.date = date;
        this.id = id;
        this.cost = cost;
    }

    @Override
    public Date getDate()
    {
        return date;
    }

    @Override
    public String getID()
    {
        return id;
    }

    @Override
    public float getCost()
    {
        return cost;
    }
}
