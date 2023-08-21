package me.bteuk.progressmapperbackend.maphubapi.actions;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIHandler;
import me.bteuk.progressmapperbackend.maphubapi.MapHubAPIResponse;
import me.bteuk.progressmapperbackend.maphubapi.maphubobjects.Geojson;

public class Append
{
    private static final String szEndpointURL = "https://maphub.net/api/1/map/append";

    public static String sendMapAppend(String szApiKey, int iMapID, Geojson mapData, boolean bPrintJson)
    {
        //The request args are just a geojson

        //Converts the request arguments into a json string
        Gson gson = new Gson();
        String szRequestArgs = gson.toJson(mapData);

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

        szRequestArgs = szRequestArgs.replace("\"id\":0,", "");
        szRequestArgs = szRequestArgs.replace("\"group\":0,", "");

        //Header fields:
        /*
            String map_id; // (required)
            FileType file_type; // (required): One of kml, gpx, geojson. For ".kmz" files, use kml
            String new_group // (optional): A string, specifying the title of a new group. If specified, a new group will be created and all new items will be placed into this group.
        */

        //Sets up the header
        HeaderArgs MapHubAPIArg = new HeaderArgs(iMapID, FileType.geojson);
        String szValueMapHubAPIArg = gson.toJson(MapHubAPIArg);
        String[][] headers = new String[1][2];
        headers[0][0] = "MapHub-API-Arg";
        headers[0][1] = szValueMapHubAPIArg;

        //Makes the request
        MapHubAPIResponse appendMapResponse = MapHubAPIHandler.request(szApiKey, szEndpointURL, szRequestArgs, headers, bPrintJson);
        assert appendMapResponse != null;
        return appendMapResponse.getStringContent();
    }
}

enum FileType
{
    geojson//, kml, gpx, Not to be implemented yet
}

class HeaderArgs
{
    String map_id;
    String file_type;

  //  String new_group;

    public HeaderArgs(int iMapId, FileType fileType)
    {
        map_id = iMapId +"";
        file_type = fileType.toString();
    //    new_group = "New group";
    }
}