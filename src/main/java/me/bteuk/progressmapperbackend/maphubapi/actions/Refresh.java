package me.bteuk.progressmapperbackend.maphubapi.actions;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIHandler;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIResponse;

public class Refresh
{
    private static final String szEndpointURL = "https://maphub.net/api/1/map/refresh_image";

    public static String refreshMapImage(String szApiKey, int iMapID)
    {
        //Creates the requestArgs object
        RefreshMapRequestArgs requestArgs = new RefreshMapRequestArgs(iMapID);

        //Converts the request arguments into a json string
        Gson gson = new Gson();
        String szRequestArgs = gson.toJson(requestArgs);

        //Makes the request
        MapHubAPIResponse listMapsResponse = MapHubAPIHandler.request(szApiKey, szEndpointURL, szRequestArgs);
        assert listMapsResponse != null;
        return listMapsResponse.getStringContent();
    }
}

//Request args specific to a GetMap request
class RefreshMapRequestArgs
{
    String map_id;

    public RefreshMapRequestArgs(int iMapID)
    {
        map_id = iMapID +"";
    }
}
