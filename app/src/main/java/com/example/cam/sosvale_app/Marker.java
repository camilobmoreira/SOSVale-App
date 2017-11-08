package com.example.cam.sosvale_app;

/**
 * Created by Aluno on 08/11/2017.
 */


public class Marker {

    private Double lat;
    private Double lon;
    private String description;

    public Marker(Double l, Double lo, String desc){
        lat = l;
        lon = lo;
        description = desc;
    }

    public Double getLat(){
        return lat;
    }

    public Double getLon(){
        return lon;
    }

    public String getDesc() { return description; }
}