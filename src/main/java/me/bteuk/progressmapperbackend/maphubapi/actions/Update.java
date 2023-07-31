package me.bteuk.progressmapperbackend.maphubapi.actions;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIHandler;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIResponse;
import me.bteuk.progressmapperbackend.maphubapi.maphubobjects.Geojson;

public class Update
{
    private static final String szEndpointURL = "https://maphub.net/api/1/map/update";

    public static String sendMapUpdate(String szApiKey, int iMapID, Geojson mapData)
    {
        //Creates the requestArgs object
        UpdateMapRequestArgs requestArgs = new UpdateMapRequestArgs(iMapID, mapData);

        //Converts the request arguments into a json string
        Gson gson = new Gson();
        String szRequestArgs = gson.toJson(requestArgs);

        String szArgsOld;
        String szArgsNew;
        String szCompilation = "";
        String szTextToEditOnThisRound;

        int iIndex;

        String szBeforeOpening;
        String szOpening;
        String szAfterOpening;

        szArgsNew = szRequestArgs;

        do
        {
            //We don't want to edit openings that we have already edited. We store those in szCompilation and then add on the new edited openings on to this
            szTextToEditOnThisRound = szArgsNew.substring(szCompilation.length());

            szArgsOld = szTextToEditOnThisRound;

            iIndex = szTextToEditOnThisRound.indexOf("\"type\":\"Polygon\",\"coordinates\":[");

            if (iIndex == -1)
                break;

            //Represents the stuff before the opening of a new shape (essentially the end of the last shape)
            szBeforeOpening = szArgsOld.substring(0, iIndex);

            //Represents the opening itself
            szOpening = szArgsOld.substring(iIndex, iIndex+32);

            //Represents everything after the opening until the end of the request json
            szAfterOpening = szArgsOld.substring(iIndex+32);

            //Replaces the opening array brackets
            szOpening = szOpening.replace("\"type\":\"Polygon\",\"coordinates\":[", "\"type\":\"Polygon\",\"coordinates\":[[");

            //Replaces the closing array brackets associated with the above opening (aka the NEXT "]]")
            szAfterOpening = szAfterOpening.replaceFirst("]]", "]]]");

            //Compiles the new full string
            szArgsNew = szCompilation + szBeforeOpening + szOpening + szAfterOpening;

            //Compiles the new "done" string
            szCompilation = szCompilation + szBeforeOpening + szOpening;
        } while (iIndex != -1); //Keeps going until no change happened

        szRequestArgs = szArgsNew;

        //Makes the request
        MapHubAPIResponse listMapsResponse = MapHubAPIHandler.request(szApiKey, szEndpointURL, szRequestArgs);
        assert listMapsResponse != null;
        return listMapsResponse.getStringContent();
    }
}

class UpdateMapRequestArgs
{
    String map_id;
    Geojson geojson;

    public UpdateMapRequestArgs(int iMapID, Geojson mapData)
    {
        this.map_id = iMapID +"";
        this.geojson = mapData;
    }
}