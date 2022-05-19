package com.podorozhnik.fragments;

import androidx.fragment.app.Fragment;

public abstract class DatetimeFragment extends Fragment {
    abstract void onDateChanged(String newData, String dialogFragmentTag);
}
