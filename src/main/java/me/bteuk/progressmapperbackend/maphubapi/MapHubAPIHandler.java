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

    public static MapHubAPIResponse request(String szApiKey, String szEndpointUrl, String szRequestArgs)
    {
        //Creates the client object
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //Creates the POST request with the correct endpoint
        ClassicHttpRequest httpPost = ClassicRequestBuilder.post(szEndpointUrl)
                //Adds the authorisation header
                .addHeader("Authorization", "Token "+szApiKey)
                //Adds the request json
                .setEntity(szRequestArgs)
                //Builds it
                .build();

        //Creates the response object
        MapHubAPIResponse mapHubResponse = null;

        //Prints out the request headers
        System.out.println("POST Request Headers:");
        for (int i = 0 ; i < httpPost.getHeaders().length ; i++)
        {
            System.out.println(i +" - " +httpPost.getHeaders()[i]);
        }
        System.out.println("End of headers");

        //Prints out the request json
        System.out.println("POST Request entity (json):");
        System.out.println(szEndpointUrl);

        try
        {
            //Executes the request
            mapHubResponse = httpClient.execute(httpPost, response ->
            {
                //Declares the response object
                MapHubAPIResponse mapHubResponse2;

                //Prints the status of the request - should always be 200 for MapHub
                System.out.println("MapHubAPIResponse.request() status of the request: " +response.getCode() + " " + response.getReasonPhrase());
                System.out.println("Response Headers:");
                for (int i = 0 ; i < response.getHeaders().length ; i++)
                {
                    System.out.println(i +" - " +response.getHeaders()[i]);
                }
                System.out.println("End of headers");

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
