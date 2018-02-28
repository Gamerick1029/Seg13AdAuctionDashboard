package Backend.Model;

import Backend.Model.Interfaces.ClickLog;
import Backend.Model.Interfaces.ImpressionLog;
import Backend.Model.Interfaces.ServerLog;

import java.util.List;

/**
 * Created by Benedict on 28/02/2018.
 */
public class CampaignModel
{
    private List<ClickLog> ClickData;
    private List<ImpressionLog> ImpressionData;
    private List<ServerLog> ServerData;

    //Population commands
    public void addClick(ClickLog cl) {ClickData.add(cl);}
    public void addImpression(ImpressionLog il) {ImpressionData.add(il);}
    public void addServer(ServerLog sl) {ServerData.add(sl);}



}
