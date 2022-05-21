package com.podorozhnik.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.podorozhnik.R;
import com.podorozhnik.entities.Location;
import com.podorozhnik.interfaces.CollectionReceivedListener;
import com.podorozhnik.managers.Geocoder2GIS;

import java.util.List;

public class LocationFragment extends DialogFragment implements CollectionReceivedListener {
    private DataSendFragment fragmentReference;
    private Geocoder2GIS geocoder2GIS;

    public LocationFragment(DataSendFragment fragmentReference){
        this.fragmentReference = fragmentReference;
        geocoder2GIS = new Geocoder2GIS(LocationFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_fragment_layout, container, false);

        return view;
    }

    @Override
    public void updateCollection(List<Location> newCollection) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        dismiss();
    }
}
