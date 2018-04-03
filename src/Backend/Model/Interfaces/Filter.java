package Backend.Model.Interfaces;

import java.util.Date;

public class Filter
{
    /*
    No type safety issues with booleans, so these can be publicly accessable
     */
    public boolean genderMale;
    public boolean genderFemale;
    public boolean genderOther;

    public boolean ageBelow20;
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
        ageBelow20 = true;
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

        startDate = new Date(Long.MIN_VALUE);
        endDate = new Date(Long.MAX_VALUE);
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        startDate = this.startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        endDate = this.endDate;
    }

}
