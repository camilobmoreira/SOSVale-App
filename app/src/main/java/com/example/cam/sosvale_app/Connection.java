package com.example.cam.sosvale_app;

import com.example.cam.sosvale_app.config.WebService;
import com.example.cam.sosvale_app.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Created by cam on 28/08/17.
 */

public class Connection {

    public JSONArray sendRequest(String requestMethod, String routeUrl) {

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();

            //Define the request method, eg: GET || POST
            con.setRequestMethod(requestMethod.toUpperCase());

            int responseCode = con.getResponseCode();
            System.out.println("Sending \'" + requestMethod + "\' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );

            String inputLine;

            // Reading data
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }

            // Closing BufferedReader
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public List<Object> convertJSON (JSONArray jsonArray) {
        Gson gson = new Gson();

        //FIXME ARRUMAR UM PRA CADA TIPO DE LISTA
        if(jsonArray.toString().contains("title")) {
            List<Post> posts = gson.fromJson(jsonArray.toString(), new TypeToken<List<Post>>(){}.getType());
            return  posts;
        }
    }
}
