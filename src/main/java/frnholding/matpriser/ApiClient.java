package frnholding.matpriser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiClient {
    private String apiKey;
    private String baseURL;

    public ApiClient(String apiKey, String baseURL)  {
        this.apiKey = apiKey;
        this.baseURL = baseURL;
    }

    public JSONObject getData(String endPoint) throws Exception {
        String urlString = baseURL + endPoint;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JSONObject(response.toString());
            //return response.toString();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }
}


