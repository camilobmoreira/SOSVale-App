package com.example.cam.sosvale_app;

import android.content.ContentValues;
import android.icu.util.Output;

import com.example.cam.sosvale_app.config.WebService;
import com.example.cam.sosvale_app.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by cam on 28/08/17.
 */

public class Connection {

    public JSONArray sendRequest(String routeUrl) {

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Sending \'GET\' request to URL : " + url);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONArray sendLoginRequest(String routeUrl, String username, String password) {
        //https://www.studytutorial.in/android-httpurlconnection-post-and-get-request-tutorial

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);


            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);

            OutputStream outputStream = con.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("");
            bufferedWriter.flush();

            int responseCode = con.getResponseCode();
            System.out.println("Sending \'POST\' request to URL : " + url);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public List<Post> convertJSONToPostList (JSONArray jsonArray) {
        Gson gson = new Gson();

        //Type collectionType = new TypeToken<Collection<Post>>(){}.getType();
        //Collection<Post>  posts = gson.fromJson(jsonArray.toString(), collectionType);

        List<Post> posts = gson.fromJson(jsonArray.toString(), new TypeToken<List<Post>>(){}.getType());

        return posts;
    }
}
