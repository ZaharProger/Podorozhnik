package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.podorozhnik.R;

public class ChooseFragment extends Fragment implements View.OnClickListener {
    Button passengerButton;
    Button driverButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.choose_fragment_layout, container, false);

        passengerButton = fragmentView.findViewById(R.id.passengerButton);
        driverButton = fragmentView.findViewById(R.id.driverButton);

        passengerButton.setOnClickListener(this);
        driverButton.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View v) {

    }
}
