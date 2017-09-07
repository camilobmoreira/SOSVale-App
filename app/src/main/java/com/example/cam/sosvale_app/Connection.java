package com.example.cam.sosvale_app;

import android.content.ContentValues;
import android.icu.util.Output;
import android.util.Log;

import com.example.cam.sosvale_app.config.WebService;
import com.example.cam.sosvale_app.model.Post;
import com.example.cam.sosvale_app.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
            Log.d("http-request", "Sending \'GET\' request to URL : " + url);
            Log.d("http-request", "Response Code : " + responseCode);

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

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", username);
            postDataParams.put("password", password);
            Log.d("params", postDataParams.toString());

            OutputStream outputStream = con.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(getPostDataString(postDataParams));

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = con.getResponseCode();
            Log.d("http-request", "Sending \'POST\' request to URL : " + url);
            Log.d("http-request", "Response Code : " + responseCode);


            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );

                result.append(in.readLine());

                // Closing BufferedReader
                in.close();
            } else {
                return new JSONArray("false : "+responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
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

    public List<User> convertJSONToUserList (JSONArray jsonArray) {
        Gson gson = new Gson();

        //Type collectionType = new TypeToken<Collection<Post>>(){}.getType();
        //Collection<Post>  posts = gson.fromJson(jsonArray.toString(), collectionType);

        List<User> users = gson.fromJson(jsonArray.toString(), new TypeToken<List<User>>(){}.getType());

        return users;
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
