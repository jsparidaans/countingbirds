package com.example.froukje.countingbirds;

import android.os.Bundle;

import com.example.froukje.countingbirds.map.MapMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class GPS extends NavigationDrawer implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<MapMarker> markers = new ArrayList<MapMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildMenu();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(51.83196,5.1713) , 12.0f) );

        MapMarker marker1 = new MapMarker("Kievit (4 eieren)", 51.83196, 5.1713);
        markers.add(marker1);
        MapMarker marker2 = new MapMarker("Grutto (3 eieren)", 51.841466, 5.173489);
        markers.add(marker2);
        MapMarker marker3 = new MapMarker("Ijsvogel (2 eieren)", 51.829818, 5.178072);
        markers.add(marker3);
        MapMarker marker4 = new MapMarker("Scholekster (5 eieren)", 51.829950, 5.143010);
        markers.add(marker4);
        MapMarker marker5 = new MapMarker("Tureluur (3 eieren)", 51.833370, 5.131423);
        markers.add(marker5);

        for (MapMarker marker : markers)
        {
            mMap.addMarker(new MarkerOptions().position(marker.getLocation()).title(marker.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getLocation()));
        }
    }
}
