package com.example.cam.sosvale_app.model;

import com.example.cam.sosvale_app.Connection;
import com.example.cam.sosvale_app.config.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cam on 28/08/17.
 */

public class Model {

    private Connection connection = null;
    private List<Post> allApprovedPosts = null;
    private List<User> allUsers = null;

    public Model(Connection connection) {
        this.connection = connection;
    }

    public Model(Connection connection, JSONArray jsonArrayPosts/*, JSONArray jsonArrayUsers*/) {
        this.connection = connection;
        this.allApprovedPosts = this.connection.convertJSONToPostList(jsonArrayPosts);
        /*this.allUsers = this.connection.convertJSONToPostList(jsonArrayUsers);*/
    }

    public List<Post> getAllApprovedPosts() {
        return this.allApprovedPosts;
    }

    public User login(String username, String password) {
        JSONArray jsonArray = null;
        if (username.contains("@")) {
            jsonArray = connection.sendLoginRequest("/login/email", username, password);
        } else if (username.contains("a")) { //FIXME
            jsonArray = connection.sendLoginRequest("/login/username", username, password);
        } else {
            jsonArray = connection.sendLoginRequest("/login/cpf", username, password);
        }

         return connection.convertJSONToUserList(jsonArray).get(0);
    }


}
