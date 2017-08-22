package com.example.cam.sosvale_app.model;

/**
 * Created by cam on 21/08/17.
 */

public class Image {

    private String description;
    private String url;
    private String icon;

    public Image() {
        super();
    }

    public Image(String description, String url, String icon)
    {
        this.description = description;
        this.url = url;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
