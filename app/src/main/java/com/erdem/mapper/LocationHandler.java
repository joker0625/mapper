package com.erdem.mapper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class LocationHandler {
    private FusedLocationProviderClient client;
    private LocationRequest request;
    static double GEO[]={0,0,0};//lan,lon,altitude
    static DataHandler dataHandler;

    public LocationHandler(Activity activity){
        dataHandler = new DataHandler();
        client= new FusedLocationProviderClient(activity);
        request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setFastestInterval(2000);
        request.setInterval(2100);

        client.requestLocationUpdates(request,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                GEO[0]=locationResult.getLastLocation().getLatitude();
                GEO[1]=locationResult.getLastLocation().getLongitude();
                GEO[2]=locationResult.getLastLocation().getAltitude();
                Log.d("main","lat:"+LocationHandler.GEO[0]+" lon:"+LocationHandler.GEO[1]+" alt:"+LocationHandler.GEO[2]);
               //  Log.d("main","lat:"+GeoCalculation.dLat_to_pix(10,100,	0.0001));

                GeoCalculation.set_DISTANCE_BETWEEN_LONGITUDE_METER(GEO[0]);
                dataHandler.addNewData(GEO[0],GEO[1],GEO[2],UI.plant_state,UI.path_state,UI.object_state);
                MainActivity.myCanvas.redrawCanvas();
            }
        }, Looper.getMainLooper());
    }
}
