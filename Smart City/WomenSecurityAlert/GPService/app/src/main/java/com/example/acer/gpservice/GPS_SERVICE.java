package com.example.acer.gpservice;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

public class GPS_SERVICE extends Service {
    private LocationListener listener;
    private LocationManager locationmanager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("MissingPermission")
    public void onCreate(){
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i =new Intent("location_update");
                i.putExtra("coordinates",location.getLatitude()+","+location.getLongitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Intent i= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationmanager =(LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,listener);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationmanager!=null){
            locationmanager.removeUpdates(listener);
        }
    }
}


