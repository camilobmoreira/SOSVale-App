package com.example.cam.sosvale_app.model;

/**
 * Created by cam on 21/08/17.
 */

public class Location {

    private double latitude;
    private double longitude;

    public Location() {
        super();
    }

    public Location (double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
