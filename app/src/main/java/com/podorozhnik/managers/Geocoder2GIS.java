package com.podorozhnik.managers;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.podorozhnik.entities.Location;
import com.podorozhnik.final_values.APIValues;
import com.podorozhnik.fragments.LocationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Geocoder2GIS {
    private LocationFragment fragmentReference;
    private String searchParams;
    private ArrayList<Location> receivedData;

    public Geocoder2GIS(LocationFragment fragmentReference){
        this.fragmentReference = fragmentReference;
        receivedData = new ArrayList<>();
    }

    public void setSearchParams(String searchParams) {
        this.searchParams = searchParams;
    }

    public void getLocation(){
        JsonObjectRequest request= new JsonObjectRequest(APIValues.LOCATION_URL + searchParams, response -> {
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

                    if (receivedData.stream()
                            .filter(existingLocation -> location.getName().equals(existingLocation.getName()))
                            .collect(Collectors.toList()).isEmpty()){

                        receivedData.add(location);
                    }
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
