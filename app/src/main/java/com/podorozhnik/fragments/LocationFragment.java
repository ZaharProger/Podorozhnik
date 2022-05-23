package com.podorozhnik.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.R;
import com.podorozhnik.adapters.LocationListAdapter;
import com.podorozhnik.entities.Location;
import com.podorozhnik.interfaces.CollectionReceivedListener;
import com.podorozhnik.managers.ConnectionChecker;
import com.podorozhnik.managers.Geocoder2GIS;

import java.util.List;

public class LocationFragment extends DialogFragment implements CollectionReceivedListener, View.OnClickListener, TextWatcher {
    private DataSendFragment fragmentReference;
    private Geocoder2GIS geocoder2GIS;
    private RecyclerView locationList;
    private EditText searchField;
    private ProgressBar locationProgressBar;
    private String selectedLocation;

    public LocationFragment(DataSendFragment fragmentReference){
        this.fragmentReference = fragmentReference;
        geocoder2GIS = new Geocoder2GIS(LocationFragment.this);
        selectedLocation = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_fragment_layout, container, false);

        view.findViewById(R.id.cancelButton).setOnClickListener(this);

        locationProgressBar = view.findViewById(R.id.locationProgressBar);

        locationList = view.findViewById(R.id.locationList);
        locationList.setHasFixedSize(true);
        locationList.setLayoutManager(new LinearLayoutManager(getContext()));
        locationList.setAdapter(new LocationListAdapter(LocationFragment.this));

        searchField = view.findViewById(R.id.searchField);
        searchField.addTextChangedListener(this);
        searchField.setText("");

        return view;
    }

    @Override
    public void updateCollection(List<Location> newCollection) {
        locationProgressBar.setVisibility(View.INVISIBLE);
        locationList.setVisibility(View.VISIBLE);

        LocationListAdapter listAdapter = (LocationListAdapter) locationList.getAdapter();
        listAdapter.setData(newCollection);

        locationList.setAdapter(listAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        fragmentReference.onDataChanged(selectedLocation, getTag());

        dismiss();
    }

    @Override
    public void onClick(View view) {
        searchField.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (ConnectionChecker.checkConnection(getContext())){
            locationProgressBar.setVisibility(View.VISIBLE);
            locationList.setVisibility(View.INVISIBLE);

            geocoder2GIS.setSearchParams(searchField.getText().toString().trim());
            geocoder2GIS.getLocation();
        }
        else
            Snackbar.make(getView(), R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                    .setTextColor(getActivity().getColor(R.color.white))
                    .show();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void onLocationSelected(String selectedLocation){
        this.selectedLocation = selectedLocation;

        onDestroy();
    }
}
