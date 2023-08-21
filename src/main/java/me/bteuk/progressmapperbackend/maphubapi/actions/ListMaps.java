package me.bteuk.progressmapperbackend.maphubapi.actions;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIHandler;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIResponse;

public class ListMaps
{
    private static final String szEndpointURL = "https://maphub.net/api/1/map/list";

    private static ListMaps listMaps(String szApiKey, boolean bPrintJson)
    {
        Gson gson = new Gson();
        String jsonResponse = listMapsRawResponse(szApiKey, bPrintJson);

        //ListMaps currently does not have any objects defined in it so this will not produce any data in maps
        ListMaps maps = gson.fromJson(jsonResponse, ListMaps.class);
        return maps;
    }

    public static String listMapsRawResponse(String szApiKey, boolean bPrintJson)
    {
        MapHubAPIResponse listMapsResponse = MapHubAPIHandler.request(szApiKey, szEndpointURL, "", new String[0][2], bPrintJson);
        assert listMapsResponse != null;
        return listMapsResponse.getStringContent();
    }
}
