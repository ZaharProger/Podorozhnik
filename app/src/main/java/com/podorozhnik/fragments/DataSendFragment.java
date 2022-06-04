package com.podorozhnik.fragments;

import androidx.fragment.app.Fragment;

import com.podorozhnik.entities.Location;
import com.podorozhnik.interfaces.EventListener;

public abstract class DataSendFragment extends Fragment implements EventListener {
    abstract void onDataChanged(Location newLocationData, String newTimeData, String dialogFragmentTag);
}
