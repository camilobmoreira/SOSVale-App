package com.example.cam.sosvale_app.model;

import com.example.cam.sosvale_app.Connection;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by cam on 28/08/17.
 */

public class Model {

    private Connection connection = null;
    private List<Post> posts = null;
    private List<User> allUsers = null;

    public Model(Connection connection) {
        this.connection = connection;
    }

    public Model(Connection connection, JSONArray jsonArrayPosts/*, JSONArray jsonArrayUsers*/) {
        this.connection = connection;
        this.posts = this.connection.convertJSONToPostList(jsonArrayPosts);
        /*this.allUsers = this.connection.convertJSONToPostList(jsonArrayUsers);*/
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public User login(String username, String password) {
        JSONArray jsonArray = null;
        if (username.contains("@")) {
            jsonArray = connection.sendLoginRequest("/login/email", username, password);
        } else if (username.matches("[0-9]+")) {
            jsonArray = connection.sendLoginRequest("/login/cpf", username, password);
        } else {
            jsonArray = connection.sendLoginRequest("/login/username", username, password);
        }

        User user;
        try {
            user = connection.convertJSONToUser(jsonArray.getJSONObject(0));

            if (jsonArray.getJSONObject(0).getString("status").equalsIgnoreCase("0")) {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;

    }

    public JSONArray addPost(Post post) {
        return connection.sendSimplePostRequest("/add/post", post);
    }

    public JSONArray approvePost(Post post) {
        return connection.sendSimplePostRequest("/approve/post", post);
    }
}
