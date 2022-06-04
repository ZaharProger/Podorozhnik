package com.podorozhnik.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.podorozhnik.R;
import com.podorozhnik.entities.Location;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.APIValues;
import com.podorozhnik.final_values.PrefsValues;
import com.podorozhnik.interfaces.EventListener;
import com.podorozhnik.managers.Map2GIS;

public class MapFragment extends Fragment implements EventListener {
    private ImageView mapImage;
    private Map2GIS map2GIS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.map_fragment_layout, container, false);

        map2GIS = new Map2GIS(MapFragment.this);

        TextView mapFromText = fragmentView.findViewById(R.id.mapFromText);
        TextView mapToText = fragmentView.findViewById(R.id.mapToText);

        mapImage = fragmentView.findViewById(R.id.mapImage);

        SharedPreferences prefs = getContext().getSharedPreferences(PrefsValues.PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains(PrefsValues.DEPARTURE_POINT) && prefs.contains(PrefsValues.DESTINATION_POINT)) {
            Gson gsonConverter = new Gson();
            Location departurePoint = gsonConverter.fromJson(prefs.getString(PrefsValues.DEPARTURE_POINT, ""),
                    Location.class);
            Location destinationPoint = gsonConverter.fromJson(prefs.getString(PrefsValues.DESTINATION_POINT, ""),
                    Location.class);

            mapFromText.setText(departurePoint.getName());
            mapToText.setText(destinationPoint.getName());

            getMap(departurePoint, destinationPoint);
        }
        else {
            mapFromText.setText("");
            mapToText.setText("");
        }

        return fragmentView;
    }

    private void getMap(Location departurePoint, Location destinationPoint) {
        SharedPreferences prefs = getContext().getSharedPreferences(PrefsValues.PREFS_NAME, Context.MODE_PRIVATE);

        String currentLat = prefs.getString(PrefsValues.CURRENT_LAT, "");
        String currentLon =  prefs.getString(PrefsValues.CURRENT_LON, "");

        String finalMapUrl;
        if (!(currentLat.equals("") && currentLon.equals(""))){
            String startPoint = APIValues.POINTER_PARAM + departurePoint.getLat() + "," +
                    departurePoint.getLon() + APIValues.POINTER_NUMS[0];
            String currentPoint = APIValues.POINTER_PARAM + currentLat + "," +
                    currentLon + APIValues.POINTER_COLOR;
            String endPoint = APIValues.POINTER_PARAM + destinationPoint.getLat() + "," +
                    destinationPoint.getLon() + APIValues.POINTER_NUMS[1];

            finalMapUrl = APIValues.MAP_URL + startPoint + currentPoint + endPoint;
        }
        else
            finalMapUrl = "";

        map2GIS.execute(finalMapUrl);
    }

    public void updateMap(Drawable newMap){
        mapImage.setImageDrawable(newMap);
    }

    @Override
    public void onResultReceived(OperationResults result) {
        switch (result){
            case MAP_ERROR:
                Snackbar.make(getView(), R.string.map_error, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
            case GPS_ERROR:
                Snackbar.make(getView(), R.string.gps_error, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
        }
    }
}
