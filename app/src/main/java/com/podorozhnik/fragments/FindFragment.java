package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.podorozhnik.R;

public class FindFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.find_fragment_layout, container, false);

        fragmentView.findViewById(R.id.findConfirmButton).setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {

    }
}
