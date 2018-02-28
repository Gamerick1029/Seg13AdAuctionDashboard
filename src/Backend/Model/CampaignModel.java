package Backend.Model;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.io.File;
import java.util.List;

/**
 * Created by Benedict on 28/02/2018.
 * This models stores all the data of the campaign as
 * internal lists
 */
public class CampaignModel implements DataModel
{
    private List<ClickLog> ClickData;
    private List<ImpressionLog> ImpressionData;
    private List<ServerLog> ServerData;

    public CampaignModel(File ClickFile, File ImpressionFile, File ServerFile)
    {

    }


    /*
    TODO: Fill these in with proper mathematical functions
     */
    @Override
    public List<ClickLog> getClickData()
    {
        return null;
    }

    @Override
    public List<ImpressionLog> getImpressionData()
    {
        return null;
    }

    @Override
    public List<ServerLog> getServerData()
    {
        return null;
    }

    @Override
    public int getImpressionsNumber()
    {
        return 0;
    }

    @Override
    public int getClicksNumber()
    {
        return 0;
    }

    @Override
    public int getUniquesNumber()
    {
        return 0;
    }

    @Override
    public int getBouncesNumber()
    {
        return 0;
    }

    @Override
    public int getConversionsNumber()
    {
        return 0;
    }

    @Override
    public float getTotalCost()
    {
        return 0;
    }

    @Override
    public float getCTR()
    {
        return 0;
    }

    @Override
    public float getCPA()
    {
        return 0;
    }

    @Override
    public float getCPC()
    {
        return 0;
    }

    @Override
    public float getCPM()
    {
        return 0;
    }

    @Override
    public float getBounceRate()
    {
        return 0;
    }
}
