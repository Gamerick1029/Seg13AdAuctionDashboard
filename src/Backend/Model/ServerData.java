package Backend.Model;

import Backend.Model.Interfaces.ServerLog;

import java.util.Date;

/**
 * Created by Benedict on 28/02/2018.
 * Basic container for server data
 */
public class ServerData implements ServerLog
{

    private Date entryDate;
    private String id;
    private Date exitDate;
    private int pagesViewed;
    private boolean converted;

    public ServerData(Date entryDate, String id,
                      Date exitDate, int pagesViewed,
                      boolean converted)
    {
        this.entryDate = entryDate;
        this.id = id;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.converted = converted;
    }

    @Override
    public Date getEntryDate()
    {
        return entryDate;
    }

    @Override
    public String getID()
    {
        return id;
    }

    @Override
    public Date getExitDate()
    {
        return exitDate;
    }

    @Override
    public int getPagesViewed()
    {
        return pagesViewed;
    }

    @Override
    public boolean getConverted()
    {
        return converted;
    }
}
