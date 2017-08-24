package com.example.cam.sosvale_app;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

            // Transforma o JSON em uma lista de posts
            allApprovedPosts = Post.findAllItens(new JSONArray(result.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Se a lista não for nula ou o tamanho não for 0, itera sobre a lista adicionando a tela
        if (allApprovedPosts != null && allApprovedPosts.size() > 0) {

            LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

            ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);


            for (Post p : allApprovedPosts) {

                // Criando novo layout para cada post
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(LLParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                // Definindo atributos do post
                TextView titleTextView = new TextView(this);
                titleTextView.setText("Titulo: " +  p.getTitle());
                titleTextView.setLayoutParams(LLParams);
                linearLayout.addView(titleTextView);

                TextView descriptionTextView = new TextView(this);
                descriptionTextView.setText("Descricao: " + p.getDescription());
                descriptionTextView.setLayoutParams(LLParams);
                linearLayout.addView(descriptionTextView);

                TextView postTypeTextView = new TextView(this);
                postTypeTextView.setText("Categoria: " + p.getPostType());
                postTypeTextView.setLayoutParams(LLParams);
                linearLayout.addView(postTypeTextView);


                LinearLayout line = new LinearLayout(this);
                line.setMinimumHeight(1);
                line.setBackgroundColor(Color.BLACK);

                mainLinearLayout.addView(linearLayout);
                mainLinearLayout.addView(line);
            }
        }

    }


}
