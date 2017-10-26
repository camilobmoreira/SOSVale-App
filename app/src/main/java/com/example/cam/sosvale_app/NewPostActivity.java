package com.example.cam.sosvale_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cam.sosvale_app.model.Location;
import com.example.cam.sosvale_app.model.Model;
import com.example.cam.sosvale_app.model.Post;
import com.example.cam.sosvale_app.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    private User loggedUser;
    private Connection connection = new Connection();
    private Model model = new Model(connection);

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private EditText imageEditText;
    private Spinner postTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        try {
            loggedUser = connection.convertJSONToUser(new JSONObject(getIntent().getExtras().get("user").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Populate the postTypeSpinner
        List<String> postTypeArray = new ArrayList<>();
        postTypeArray.add("Incendio");
        postTypeArray.add("Deslizamento");
        postTypeArray.add("Alagamento");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, postTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner postTypeSpinner = (Spinner) findViewById(R.id.postTypeSpinner);
        postTypeSpinner.setAdapter(adapter);

        //Set current location to latitude and longitude EditText
        //// FIXME: 25/10/17

        Button mNewPostButton = (Button) findViewById(R.id.new_post_button);
        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPost();
            }
        });

        Button mOpenMapButton = (Button) findViewById(R.id.openMapButton);
        mOpenMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });
    }

    private void openMapActivity() {
        Intent openMapActivityIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(openMapActivityIntent, 0);
        //// FIXME: 25/10/17 PEGAR RESULTADO E DEFINIR NOS EDIT TEXT
    }

    private void newPost() {
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        latitudeEditText = (EditText) findViewById(R.id.latitudeEditText);
        longitudeEditText = (EditText) findViewById(R.id.longitudeEditText);
        imageEditText = (EditText) findViewById(R.id.imageUrlEditText);
        postTypeSpinner = (Spinner) findViewById(R.id.postTypeSpinner);

        Post post = new Post();

        post.setTitle(titleEditText.getText().toString());
        post.setDescription(descriptionEditText.getText().toString());
        post.setLocation(new Location(
                    Double.parseDouble(latitudeEditText.getText().toString()),
                    Double.parseDouble(longitudeEditText.getText().toString())));
        post.setImage(imageEditText.getText().toString());
        post.setPostType(postTypeSpinner.getSelectedItem().toString());
        post.setUsername(loggedUser.getUsername());
        post.setPostingDate(new Date());

        model.addPost(post);
    }
}
