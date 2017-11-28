package com.example.cam.sosvale_app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.cam.sosvale_app.model.Model;
import com.example.cam.sosvale_app.model.Post;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Connection con = new Connection();
    Model model = new Model(con);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //obter o mapa da view
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //set o callback
        mapFragment.getMapAsync(this);

    }

    //metodo callback
    @Override
    public void onMapReady(GoogleMap map) {

        // Pega todos os posts do webservice
        List<Marker> mark = null;
        for(Post p : model.getPosts()) {
            Marker m = new Marker(p.getLocation().getLatitude(),p.getLocation().getLongitude(),p.getTitle());
            mark.add(m);
        }
        for (Marker marker : mark) {
            map.addMarker(new MarkerOptions().position(new LatLng(marker.getLat(), marker.getLon())).title(marker.getDesc()));
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener gps = new GPS(map, mark);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gps);


    }

}