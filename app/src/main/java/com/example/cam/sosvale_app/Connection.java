package com.example.cam.sosvale_app;

import android.os.StrictMode;
import android.util.Log;

import com.example.cam.sosvale_app.config.WebService;
import com.example.cam.sosvale_app.model.Location;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cam on 28/08/17.
 */

public class Connection {

    public JSONArray sendGetRequest(String routeUrl) {

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();

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
                return new JSONArray("false : " + responseCode);
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

    /**
     * Send a simple post request that the only paramater is a post, eg:
     * /add/post
     * /approve/post
     *
     * @param routeUrl route of the request
     * @param post post to be sent
     * @return
     */
    public JSONArray sendSimplePostRequest(String routeUrl, Post post) {

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("title", post.getTitle());
            postDataParams.put("description", post.getDescription());
            postDataParams.put("image", post.getImage());
            postDataParams.put("latitude", post.getLocation().getLatitude());
            postDataParams.put("longitude", post.getLocation().getLongitude());
            postDataParams.put("postType", post.getPostType());
            postDataParams.put("username", post.getUsername());
            postDataParams.put("postingDate", post.getPostingDate());

            //JSONObject postDataParams = convertPostToJSONObject(post); //FIXME arrumar conversao e usar

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
                return new JSONArray("false : " + responseCode);
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

    public JSONArray sendSimplePostRequest(String routeUrl, String postTitle) {

        final StringBuilder result = new StringBuilder();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + routeUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("title", postTitle);

            //JSONObject postDataParams = convertPostToJSONObject(post); //FIXME arrumar conversao e usar

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
                return new JSONArray("false : " + responseCode);
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

        List<Post> posts = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Post>>(){}.getType());
        /*List<Post> posts = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Post p = new Post();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                p.setImage(jsonObject.getString("image"));
                Location location = new Location();
                location.setLatitude(jsonObject.getDouble("latitude"));
                location.setLongitude(jsonObject.getDouble("longitude"));
                p.setLocation(location);
                p.setUsername(jsonObject.getString("username"));
                p.setDescription(jsonObject.getString("description"));
                p.setPostType(jsonObject.getString("postType"));
                p.setPostingDate(new Date(jsonObject.getString("postingDate")));

                posts.add(p);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        return posts;
    }

    public List<User> convertJSONToUserList (JSONArray jsonArray) {

        //Type collectionType = new TypeToken<Collection<Post>>(){}.getType();
        //Collection<Post>  posts = gson.fromJson(jsonArray.toString(), collectionType);

        List<User> users = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<User>>(){}.getType());

        return users;
    }

    public User convertJSONToUser(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), User.class);
    }

    public JSONObject convertUserToJSONObject(User user) throws  JSONException {
        return new JSONObject(new Gson().toJson(user));
    }

    public JSONObject convertPostToJSONObject(Post post) throws JSONException {
        return  new JSONObject(new Gson().toJson(post));
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

    public List<Marker> getData() throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection = null;
        try {

            url = new URL("/search/post/ApprovedPosts");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);


            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                result.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(result);
        List<Marker> finalResult = generateJSON(new JSONArray(result.toString()));
        return finalResult;
    }


    public List<Marker> generateJSON(JSONArray json){

        List<Marker> found = new LinkedList<Marker>();
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                found.add(new Marker(obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0),obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1), obj.getJSONObject("properties").getString("name")));
            }
        } catch (JSONException e) {
            // handle exception
        }
        return found;

    }
}
