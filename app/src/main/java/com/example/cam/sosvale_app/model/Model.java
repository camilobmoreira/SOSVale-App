package com.example.cam.sosvale_app.model;

import com.example.cam.sosvale_app.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cam on 28/08/17.
 */

public class Model {

    Connection connection = null;
    //FIXME CRIAR LISTA PARA CADA TIPO DE VARIAVEL (POST, USER)

    // FIXME USAR GSON PARA CONVERTER OS JSON
    public List<Post> findAllPosts(JSONArray response) {
        List<Post> found = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                found.add(new Post(
                        obj.getString("title"),
                        obj.getString("description"),
                        null /*obj.getString("location")*/,
                        null /*obj.getString("imagem")*/,
                        obj.getString("username"),
                        obj.getString("postType"),
                        null /*obj.getString("data")*/
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return found;
    }
}
