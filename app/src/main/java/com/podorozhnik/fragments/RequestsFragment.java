package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.podorozhnik.R;

//Тут пока временная демонстрация, потом класс адаптируем под показ запросов
public class RequestsFragment extends Fragment {
    String announceText;

    public RequestsFragment(String announceText){
        this.announceText = announceText;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.requests_fragment_layout, container, false);

        TextView announceLabel = fragmentView.findViewById(R.id.announceLabel);
        announceLabel.setText(announceText);

        return fragmentView;
    }
}
