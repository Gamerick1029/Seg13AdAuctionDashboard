package Backend.Model;

import Backend.Model.Interfaces.Gender;
import Backend.Model.Interfaces.ImpressionLog;

import java.util.Date;

/**
 * Created by Benedict on 28/02/2018.
 * Basic holder for impression data
 */
public class ImpressionData implements ImpressionLog
{
    private Date date;
    private String id;
    private String ageRange;
    private Gender gender;
    private String income;
    private String context;
    private float cost;


    public ImpressionData(Date date, String id, String ageRange,
                          Gender gender, String income,
                          String context, float cost)
    {
        this.date = date;
        this.id = id;
        this.gender = gender;
        this.ageRange = ageRange;
        this.income = income;
        this.context = context;
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
    public String getAgeRange()
    {
        return ageRange;
    }

    @Override
    public Gender getGender()
    {
        return gender;
    }

    @Override
    public String getIncome()
    {
        return income;
    }

    @Override
    public String getContext()
    {
        return context;
    }

    @Override
    public float getImpressionCost()
    {
        return cost;
    }
}
