package com.example.cam.sosvale_app;

import android.graphics.Color;
import android.os.StrictMode;
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

public class ApprovePostsActivity extends AppCompatActivity {

    private User loggedUser;
    private Connection connection = new Connection();
    private Model model = new Model(connection);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_posts);

        try {
            loggedUser = connection.convertJSONToUser(new JSONObject(getIntent().getExtras().get("user").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = new Connection();

        // Pega todos os posts do webservice
        JSONArray jsonArrayPosts = connection.sendGetRequest("/search/post/NonApprovedPosts");

        Model model = new Model(connection, jsonArrayPosts/*, jsonArrayUsers*/);
        List<Post> allNonApprovedPosts = model.getPosts();

        fillInPosts(allNonApprovedPosts);
    }

    public void fillInPosts(List<Post> posts) {

        // Se a lista não for nula ou o tamanho não for 0, itera sobre a lista adicionando à tela
        if (posts != null && posts.size() > 0) {

            LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);

            ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);


            for (final Post p : posts) {

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

                Button approvePost = new Button(this);
                approvePost.setText("Aprovar post");
                //approvePost.setLayoutParams(LLParams); //FIXME SE DER ERRO, RETIRAR
                //approvePost.setWidth(ViewGroup.LayoutParams.MATCH_PARENT); //FIXME SE DER ERRO, RETIRAR
                //approvePost.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); //FIXME SE DER ERRO, RETIRAR
                approvePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.approvePost(p);
                    }
                });
                linearLayout.addView(approvePost);

                LinearLayout line = new LinearLayout(this);
                line.setMinimumHeight(1);
                line.setBackgroundColor(Color.BLACK);

                mainLinearLayout.addView(linearLayout);
                mainLinearLayout.addView(line);
            }
        }
    }
}
