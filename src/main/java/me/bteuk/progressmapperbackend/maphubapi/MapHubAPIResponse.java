package me.bteuk.progressmapperbackend.maphubapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapHubAPIResponse
{
    private String responseReason;
    private InputStream content;
    private String szContent;

    public MapHubAPIResponse(String responseReason, InputStream content)
    {
        this.responseReason = responseReason;
        this.content = content;
    }

    public String getResponseReason()
    {
        return responseReason;
    }

    public InputStream getContent()
    {
        return content;
    }

    public String getStringContent()
    {
        return szContent;
    }

    public void extractContentInputStreamToString()
    {
        InputStreamReader reader = new InputStreamReader(content);
        BufferedReader br = new BufferedReader(reader);

        String szAllLinesOfContent = readAll(br);

        //Stores the output from reading all lines of the InputStream in a global string variable
        this.szContent = szAllLinesOfContent;
    }

    //Reads all lines from an InputStream into a String
    private static String readAll(BufferedReader br)
    {
        StringBuilder sb = new StringBuilder("");
        try
        {
            String line;
            line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            sb = new StringBuilder();
        }
        return sb.toString();
    }
}
