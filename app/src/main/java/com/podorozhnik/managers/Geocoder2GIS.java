package com.podorozhnik.managers;

import android.content.Context;
import android.location.LocationManager;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.podorozhnik.entities.Location;
import com.podorozhnik.final_values.APIValues;
import com.podorozhnik.fragments.LocationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Geocoder2GIS {
    private static final String TARGET_URL = "https://catalog.api.2gis.com/3.0/items/geocode?" +
            "type=adm_div.city,adm_div.district,adm_div.division,adm_div.living_area,adm_div.place," +
            "adm_div.settlement,building,street&fields=items.full_adress_name,items.point&" +
            "sort=distance&key=ruxckr9415&q=";
    private LocationFragment fragmentReference;
    private String searchParams;
    private ArrayList<Location> receivedData;

    public Geocoder2GIS(LocationFragment fragmentReference){
        this.fragmentReference = fragmentReference;
        receivedData = new ArrayList<>();
    }
    public static boolean checkGPSConnection(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setSearchParams(String searchParams) {
        this.searchParams = searchParams;
    }

    public void getLocation(){
        JsonObjectRequest request= new JsonObjectRequest(TARGET_URL + searchParams, response -> {
            try {
                receivedData.clear();

                JSONObject receivedResult = response.getJSONObject(APIValues.TARGET_OBJECT);
                JSONArray extractedResult = receivedResult.getJSONArray(APIValues.TARGET_ARRAY);

                for (int i = 0; i < extractedResult.length(); ++i){
                    Location location = new Location();

                    location.setName(extractedResult.getJSONObject(i).getString(APIValues.LOCATION_NAME));
                    JSONObject extractedPoint = extractedResult.getJSONObject(i).getJSONObject(APIValues.LOCATION_POINT);
                    location.setLat(extractedPoint.getDouble(APIValues.LOCATION_LAT));
                    location.setLon(extractedPoint.getDouble(APIValues.LOCATION_LON));

                    receivedData.add(location);
                }

                fragmentReference.updateCollection(receivedData);
            }
            catch (JSONException e) {
                fragmentReference.updateCollection(receivedData);
            }
        }, null);

        Volley.newRequestQueue(fragmentReference.getContext()).add(request);
    }
}
