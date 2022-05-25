package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.podorozhnik.R;
import com.podorozhnik.entities.Request;

import java.util.ArrayList;
import java.util.List;

//Тут пока временная демонстрация, потом класс адаптируем под показ запросов
public class RequestsFragment extends Fragment {
    private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ListView list_data;
    ListView announceText;
    private List<Request> list_requests = new ArrayList<>();


    public RequestsFragment(ListView announceText){
        this.announceText = announceText;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.requests_fragment_layout, container, false);
       // list_data = (ListView) fragmentView.findViewById(R.id.list_data);
        TextView announceLabel = fragmentView.findViewById(R.id.announceLabel);
        announceLabel.setText((CharSequence) announceText);
        return fragmentView;
    }

}
