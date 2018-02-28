package Backend.Model.Stubs;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.DataModel;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.util.List;

/**
 * Created by Benedict on 28/02/2018.
 * Stub for the data model
 */
public class DataModelStub implements DataModel
{

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
        return 1;
    }

    @Override
    public int getClicksNumber()
    {
        return 2;
    }

    @Override
    public int getUniquesNumber()
    {
        return 3;
    }

    @Override
    public int getBouncesNumber()
    {
        return 4;
    }

    @Override
    public int getConversionsNumber()
    {
        return 5;
    }

    @Override
    public float getTotalCost()
    {
        return 6.0f;
    }

    @Override
    public float getCTR()
    {
        return 7.0f;
    }

    @Override
    public float getCPA()
    {
        return 8.0f;
    }

    @Override
    public float getCPC()
    {
        return 9.0f;
    }

    @Override
    public float getCPM()
    {
        return 10.0f;
    }

    @Override
    public float getBounceRate()
    {
        return 11.0f;
    }
}
