package com.example.froukje.countingbirds.map;

import com.google.android.gms.maps.model.LatLng;

public class MapMarker
{
    private LatLng location;
    private String title;

    public MapMarker(String title, double latitude, double longitude)
    {
        this.title = title;
        location = new LatLng(latitude, longitude);
    }

    public String getTitle()
    {
        return title;
    }

    public LatLng getLocation()
    {
        return location;
    }
}
