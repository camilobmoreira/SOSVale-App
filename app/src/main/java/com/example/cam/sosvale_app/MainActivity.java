package com.example.cam.sosvale_app;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cam.sosvale_app.config.WebService;
import com.example.cam.sosvale_app.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView postTitle = (TextView) findViewById(R.id.post_title);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final StringBuffer result = new StringBuffer();

        HttpURLConnection con = null;

        try {
            URL url = new URL(WebService.DOMAIN_URL + "/search/post/ApprovedPosts");
            con = (HttpURLConnection) url.openConnection();

            // Optional. Default is GET
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Log.d("test", "\nSending 'GET' request to URL : " + url);
            Log.d("test", "Response Code : " + responseCode);

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

        List<Post> allApprovedPosts = null;
        try {
            allApprovedPosts = Post.findAllItens(new JSONArray(result.toString()));
            Log.d("test", String.valueOf(allApprovedPosts.size()));

            //postTitle.setText(allApprovedPosts.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*ScrollView scrollView = new ScrollView(this);

        for (Post p : allApprovedPosts) {


            TextView titleTextView = new TextView(this);

        }*/










    }


}
