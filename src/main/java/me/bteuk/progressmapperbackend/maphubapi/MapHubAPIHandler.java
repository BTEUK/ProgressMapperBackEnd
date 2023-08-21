package me.bteuk.progressmapperbackend.maphubapi;

import com.google.gson.Gson;
import me.bteuk.progressmapperbackend.maphubapi.actions.GetMap;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.io.CloseMode;

import java.io.InputStream;

public class MapHubAPIHandler
{
    //MapHub API uses HTTP POST requests with JSON arguments and JSON responses.
    //Request authentication is via API keys using the Authorization: Token <api_key> request header.

    public static MapHubAPIResponse request(String szApiKey, String szEndpointUrl, String szRequestArgs, String[][] headers, boolean bPrintJson)
    {
        //Creates the client object
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //Creates the POST request with the correct endpoint
        ClassicRequestBuilder builder = ClassicRequestBuilder.post(szEndpointUrl)
                //Adds the authorisation header
                .addHeader("Authorization", "Token "+szApiKey)
                //Adds the request json
                .setEntity(szRequestArgs);

        for (int i = 0 ; i < headers.length ; i++)
        {
            builder = builder.addHeader(headers[i][0], headers[i][1]);
        }

        ClassicHttpRequest httpPost = builder.build();

        //Creates the response object
        MapHubAPIResponse mapHubResponse = null;

        //Prints out the request headers
        System.out.println("\nPOST Request Headers:");
        for (int i = 0 ; i < httpPost.getHeaders().length ; i++)
        {
            System.out.println(i +" - " +httpPost.getHeaders()[i]);
        }
        System.out.println("End of headers");

        //Prints out the request json
        if (bPrintJson)
        {
            System.out.println("\nPOST Request entity (json):");
            System.out.println(szRequestArgs);
        }

        try
        {
            //Executes the request
            mapHubResponse = httpClient.execute(httpPost, response ->
            {
                //Declares the response object
                MapHubAPIResponse mapHubResponse2;

                //Prints the status of the request - should always be 200 for MapHub
                System.out.println("\nMapHubAPIResponse.request() status of the request: " +response.getCode() + " " + response.getReasonPhrase());
                System.out.println("\nResponse Headers:");
                for (int i = 0 ; i < response.getHeaders().length ; i++)
                {
                    System.out.println(i +" - " +response.getHeaders()[i]);
                }
                System.out.println("End of headers\n");

                //Extracts the content from the response
                final HttpEntity entity2 = response.getEntity();
                InputStream content2 = entity2.getContent();

                //Puts the response values into a MapHubAPIResponse class
                mapHubResponse2 = new MapHubAPIResponse(response.getReasonPhrase(), content2);
                mapHubResponse2.extractContentInputStreamToString();

                // Ensure it is fully consumed
                EntityUtils.consume(entity2);
                return mapHubResponse2;
            });
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            httpClient.close(CloseMode.GRACEFUL);
        }
        return mapHubResponse;
    }
}
