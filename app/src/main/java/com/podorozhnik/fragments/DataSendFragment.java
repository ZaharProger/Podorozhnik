package com.podorozhnik.fragments;

import androidx.fragment.app.Fragment;

import com.podorozhnik.interfaces.DatabaseEventListener;

public abstract class DataSendFragment extends Fragment implements DatabaseEventListener {
    abstract void onDataChanged(String newData, String dialogFragmentTag);
}
