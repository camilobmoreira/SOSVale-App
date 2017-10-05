package com.example.cam.sosvale_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        //Populate the postTypeSpinner
        List<String> postTypeArray = new ArrayList<>();
        postTypeArray.add("Incendio");
        postTypeArray.add("Deslizamento");
        postTypeArray.add("Alagamento");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, postTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner postTypeSpinner = (Spinner) findViewById(R.id.postTypeSpinner);
        postTypeSpinner.setAdapter(adapter);
    }
}
