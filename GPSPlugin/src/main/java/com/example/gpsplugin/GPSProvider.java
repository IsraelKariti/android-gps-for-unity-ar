package com.example.gpsplugin;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;//qsdff

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GPSProvider {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Activity activity;
    public double lat;
    public double lon;
    public long time = 0;
    public boolean availability = true;
    public float accuracy;

    public GPSProvider(Activity ac) {
        activity = ac;
        setGPS();
    }

    public void setGPS() {

        // get the location manager manager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        // define the location request
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // set the location callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                lat = location.getLatitude();
                lon = location.getLongitude();
                time = location.getTime();
                accuracy = location.getAccuracy();
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                availability = locationAvailability.isLocationAvailable();
            }
        };

        // check permissions
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // send the request
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}
