package com.example.cam.sosvale_app.model;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by cam on 21/08/17.
 */

public class Post implements Comparator<Date> {

    private String title;
    private String description;
    private Location location;
    private String image;
    private String username;
    private String postType;
    private Calendar postingDate;
    private boolean approved = false;

    public Post () {
        super();
        this.approved = false;
    }

    public Post(String title, String description, Location location, String image, String username, String postType) {
        super();
        this.title = title;
        this.description = description;
        this.location = location;
        this.image = image;
        this.username = username;
        this.postType = postType;
        this.postingDate = Calendar.getInstance();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Calendar getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Calendar postingDate) {
        this.postingDate = postingDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public int compare(Date d1, Date d2) {
        // TODO Auto-generated method stub
        return d1.compareTo(d2);
    }
}
