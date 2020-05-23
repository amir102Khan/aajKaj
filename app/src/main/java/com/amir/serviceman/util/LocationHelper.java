package com.amir.serviceman.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amir.serviceman.R;
import com.amir.serviceman.interfaces.GetLocationListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class LocationHelper implements LocationListener {
    private Activity context;
    private GetLocationListener getLocationListener;
    private AlertDialog alertDialog;
    final LocationManager mLocationManager;

    public LocationHelper(Activity context, GetLocationListener getLocationListener) {
        this.context = context;
        this.getLocationListener = getLocationListener;
        mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            if (getLocationListener != null) {
                                getLocationListener.onLocationFound(location.getLatitude() + "",
                                        location.getLongitude() + "",
                                        getAddressFromLatLng(location.getLatitude(), location.getLongitude()));
                            }
                        } else {
                            Log.e("error: ", "location in fused is null");
                            Location location1 = getLocationAnyWay();
                            if (location1 != null) {
                                if (getLocationListener != null) {
                                    getLocationListener.onLocationFound(location1.getLatitude() + "",
                                            location1.getLongitude() + "",
                                            getAddressFromLatLng(location1.getLatitude(), location1.getLongitude()));
                                }
                            }
                            getCurrentLocation();
                        }
                    }
                });
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, context.getString(R.string.location_permission_error), Toast.LENGTH_LONG).show();
                return;
            }
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                1000.0f,  this);
    }

    private String getAddressFromLatLng(double latitude, double longitude) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            return address ;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (getLocationListener != null) {
                getLocationListener.onLocationFound(location.getLatitude() + "",
                        location.getLongitude() + "",
                        getAddressFromLatLng(location.getLatitude(), location.getLongitude()));
                mLocationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (alertDialog != null)
            return;
        alertDialog = Dialogs.alertDialogGPS(context.getString(R.string.enable_gps), context);
    }

    @SuppressLint("MissingPermission")
    private Location getLocationAnyWay() {
        Location location;
        try {
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(context, context.getString(R.string.bo_location_available), Toast.LENGTH_SHORT).show();
            } else {
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000,
                            1000.0f, this);

                    location = mLocationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        return location;
                    }
                }

                if (isGPSEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            1000,
                            1000.0f, this);
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        return location;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
