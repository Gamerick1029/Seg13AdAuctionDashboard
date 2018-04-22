package Backend.Model.Interfaces;

import java.util.Date;

public class Filter
{
    /*
    No type safety issues with booleans, so these can be publicly accessible
     */
    public boolean genderMale;
    public boolean genderFemale;
    public boolean genderOther;

    public boolean ageBelow25;
    public boolean age25to34;
    public boolean age35to44;
    public boolean age45to54;
    public boolean ageAbove54;

    public boolean incomeLow;
    public boolean incomeMedium;
    public boolean incomeHigh;

    public boolean contextNews;
    public boolean contextShopping;
    public boolean contextMedia;
    public boolean contextBlog;
    public boolean contextHobbies;
    public boolean contextTravel;
    public Step step;

    public static int pagesViewedForBounce = 1;
    public static int timeOnSiteForBounce = 10;
    public static boolean bounceRateByPages = true;

    private Date startDate;
    private Date endDate;

    /*
    By default, filter allows everything;
     */
    public Filter()
    {
        age25to34 = true;
        age35to44 = true;
        age45to54 = true;
        ageBelow25 = true;
        ageAbove54 = true;

        genderFemale = true;
        genderMale = true;
        genderOther = true;

        incomeLow = true;
        incomeMedium = true;
        incomeHigh = true;

        contextNews = true;
        contextShopping = true;
        contextMedia = true;
        contextBlog = true;
        contextHobbies = true;
        contextTravel = true;

        step = Step.DAY;

        //If we set these to LONG.MIN and so on then many weird and wonderful issues arise. Just be aware of the
        //following initialisation and all will be well
        startDate = null;
        endDate = null;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void clearAll(){
        age25to34 = false;
        age35to44 = false;
        age45to54 = false;
        ageBelow25 = false;
        ageAbove54 = false;

        genderFemale = false;
        genderMale = false;
        genderOther = false;

        incomeLow = false;
        incomeMedium = false;
        incomeHigh = false;

        contextNews = false;
        contextShopping = false;
        contextMedia = false;
        contextBlog = false;
        contextHobbies = false;
        contextTravel = false;
    }

}
