package com.example.cam.sosvale_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cam.sosvale_app.model.Model;
import com.example.cam.sosvale_app.model.Post;
import com.example.cam.sosvale_app.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecentPostsActivity extends AppCompatActivity {

    private User loggedUser;
    private Connection connection = new Connection();
    private Model model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            loggedUser = connection.convertJSONToUser(new JSONObject(getIntent().getExtras().get("user").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_posts);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton mOpenMapButton = (FloatingActionButton) findViewById(R.id.open_all_on_map_floating_action_button);
        mOpenMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

        FloatingActionButton mNewPostButton = (FloatingActionButton) findViewById(R.id.new_post_floating_action_button);
        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewPostActivity();
            }
        });

        Connection connection = new Connection();

        // Pega todos os posts do webservice
        JSONArray jsonArrayPosts = connection.sendGetRequest("/search/post/ApprovedPosts");

        model = new Model(connection, jsonArrayPosts/*, jsonArrayUsers*/);
        List<Post> allApprovedPosts = model.getPosts();

        fillInPosts(allApprovedPosts);
    }

    private void openMapActivity() {
        Intent openMapActivityIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(openMapActivityIntent, 0);
        //// FIXME: 25/10/17 PEGAR RESULTADO E DEFINIR NOS EDIT TEXT
    }

    private void openNewPostActivity() {
        Intent newPostActivityIntent = new Intent(this, NewPostActivity.class);
        try {
            newPostActivityIntent.putExtra("user", connection.convertUserToJSONObject(loggedUser).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(newPostActivityIntent);
    }

    public void fillInPosts(List<Post> posts) {

        // Se a lista não for nula ou o tamanho não for 0, itera sobre a lista adicionando à tela
        if (posts != null && posts.size() > 0) {

            LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

            ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);


            for (Post p : posts) {

                // Criando novo layout para cada post
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(LLParams);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                // Definindo atributos do post
                TextView titleTextView = new TextView(this);
                titleTextView.setText("Titulo: " +  p.getTitle());
                linearLayout.addView(titleTextView);

                TextView descriptionTextView = new TextView(this);
                descriptionTextView.setText("Descricao: " + p.getDescription());
                linearLayout.addView(descriptionTextView);

                TextView postTypeTextView = new TextView(this);
                postTypeTextView.setText("Categoria: " + p.getPostType());
                linearLayout.addView(postTypeTextView);

                TextView usernameTextView = new TextView(this);
                usernameTextView.setText("Usuário: " + p.getUsername());
                linearLayout.addView(usernameTextView);

                LinearLayout line = new LinearLayout(this);
                line.setMinimumHeight(1);
                line.setBackgroundColor(Color.BLACK);

                mainLinearLayout.addView(linearLayout);
                mainLinearLayout.addView(line);
            }
        }
    }


}
