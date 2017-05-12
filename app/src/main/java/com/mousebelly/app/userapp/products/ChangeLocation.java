package com.mousebelly.app.userapp.products;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Kamal Kant on 28-04-2017.
 */

public class ChangeLocation extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    Marker Marker;
    Circle mCircle;
    LatLng currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_location_dialog_box);

        /*Dialog locationChange = new Dialog(MainActivity.context);

        locationChange.setTitle("Change Location");
        locationChange.setContentView(R.layout.change_location_dialog_box);*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.change_locaton_map);
        mapFragment.getMapAsync(this);

        // locationChange.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a Marker in Sydney and move the camera
        Double Lat = Double.parseDouble(LoginActivity.user.getLat());
        Double Long = Double.parseDouble(LoginActivity.user.getLongitude());
        currentLoc = new LatLng(Lat, Long);
        mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(Lat, Long)).radius(10000).strokeColor(ContextCompat.getColor(ChangeLocation.this, R.color.Amulet)).fillColor((Color.parseColor("#4DD50000")))); //#4DD50000 //#1B5E20  // (Color.parseColor("#f24848")));
        Marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title("Drag to change Location").draggable(true).snippet("MouseBelly"));
        Marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10.0f));


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                mCircle.remove();
                currentLoc = marker.getPosition();
                mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)).radius(10000).strokeColor(ContextCompat.getColor(ChangeLocation.this, R.color.Roman)).fillColor((Color.parseColor("#4DD50000"))));

                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10.0f));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng ll = marker.getPosition();

                LoginActivity.user.setLongitude(String.valueOf(ll.longitude));
                LoginActivity.user.setLat(String.valueOf(ll.latitude));

                Marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title("MouseBelly").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                marker.remove();

                Intent returnToMainActivity = new Intent(ChangeLocation.this, MainActivity.class);
                startActivity(returnToMainActivity);
            }
        });
    }
}