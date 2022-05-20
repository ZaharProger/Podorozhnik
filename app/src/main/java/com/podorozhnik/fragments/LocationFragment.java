package com.podorozhnik.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.podorozhnik.R;
import com.podorozhnik.entities.Location;
import com.podorozhnik.interfaces.CollectionReceivedListener;
import com.podorozhnik.managers.Geocoder2GIS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LocationFragment extends DialogFragment implements CollectionReceivedListener {
    private DataSendFragment fragmentReference;
    private String userData;
    private Geocoder2GIS geocoder2GIS;
    private Spinner locationList;

    public LocationFragment(DataSendFragment fragmentReference){
        this.fragmentReference = fragmentReference;
        geocoder2GIS = new Geocoder2GIS(LocationFragment.this);
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_fragment_layout, container, false);

        locationList = view.findViewById(R.id.locationList);

        String waitText = getString(R.string.wait_text);

        ArrayList<String> initAdapterValues = new ArrayList<>(Collections.singletonList(waitText));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, initAdapterValues);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        locationList.setAdapter(adapter);

        geocoder2GIS.setSearchParams(userData);
        geocoder2GIS.getLocation();

        return view;
    }

    @Override
    public void updateCollection(List<Location> newCollection) {
        ArrayAdapter<String> listAdapter = (ArrayAdapter<String>) locationList.getAdapter();
        listAdapter.clear();

        if (!newCollection.isEmpty()) {
            List<String> mapCollection = newCollection.stream()
                    .map(location -> location.getName())
                    .distinct()
                    .collect(Collectors.toList());

            listAdapter.addAll(mapCollection);
        }
        else
            listAdapter.add(getString(R.string.not_found_text));

        listAdapter.notifyDataSetChanged();
        locationList.setAdapter(listAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        String selectedData = locationList.getSelectedItem().toString();
        if (selectedData.equals(getString(R.string.wait_text)) ||
            selectedData.equals(getString(R.string.not_found_text))) {

            selectedData = "";
        }
        fragmentReference.onDataChanged(selectedData, getTag());

        dismiss();
    }
}
