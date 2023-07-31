package me.bteuk.progressmapperbackend.maphubapi.actions;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIHandler;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIResponse;
import me.bteuk.progressmapperbackend.maphubapi.maphubobjects.Geojson;
import me.bteuk.progressmapperbackend.maphubapi.maphubobjects.User;

import java.util.HashMap;

public class GetMap
{
    public String toString()
    {
        return "Student [ id: "+id+", url: "+ url+ " ]";
    }

    private static final String szEndpointURL = "https://maphub.net/api/1/map/get";
    private int id;
    private String url;
    private String owner;
    private String short_name;
    private String title;
    private String visibility; //One of public, unlisted, private, select
    private Geojson geojson;
    private String[] markers;
    private HashMap properties;
    private String description;
    private String basemap;
    private boolean collaborate;
    private String[] viewer_users;
    private User[] editor_users; //Each user has a username
    private String created_date;
    private String modified_date;
    private String[] images; //A list of IDs

    public Geojson getGeojson()
    {
        return geojson;
    }

    public static GetMap getMap(String szApiKey, int iMapID)
    {
        Gson gson = new Gson();

        String szJson = getMapRawResponse(szApiKey, iMapID);

        //Changes the 3D arrays for coordinates into 2D arrays
        String szEditedToChange3DArraysTo2D = szJson.replace("[[[", "[[").replace("]]]", "]]");

        GetMap getMap = gson.fromJson(szEditedToChange3DArraysTo2D, GetMap.class);
        return getMap;
    }

    private static String getMapRawResponse(String szApiKey, int iMapID)
    {
        //Creates the requestArgs object
        GetMapRequestArgs requestArgs = new GetMapRequestArgs(iMapID);

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
class GetMapRequestArgs
{
    String map_id;

    public GetMapRequestArgs(int iMapID)
    {
        map_id = iMapID +"";
    }
}
