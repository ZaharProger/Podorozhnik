package com.podorozhnik.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.podorozhnik.R;
import com.podorozhnik.adapters.DriverSearchAdapter;
import com.podorozhnik.entities.Request;
import com.podorozhnik.managers.PodorozhnikMessagingService;

import java.util.ArrayList;
import java.util.List;

public class DriverSearchFragment extends Fragment {
    private ListView list_data;
    private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private final List<Request> list_requests = new ArrayList<>();
    private PodorozhnikMessagingService messagingService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.all_search_fragment_layout, container, false);
        super.onCreate(savedInstanceState);

        circular_progress = fragmentView.findViewById(R.id.circular_progress);
        list_data = fragmentView.findViewById(R.id.list_data);
        list_data.setOnItemClickListener((parent, view, position, id) -> {
            DriverSearchAdapter adapter = (DriverSearchAdapter) list_data.getAdapter();
            Request selectedRequest = (Request) adapter.getItem(position);
            String messageToSend = String.format("С вами хочет поехать %s", selectedRequest.getUserLogin());

            messagingService.sendNotification(getContext(), messageToSend);
        });

        messagingService = new PodorozhnikMessagingService();

        initFirebase();
        addEventFirebaseListener();
        return fragmentView;
    }

    private void addEventFirebaseListener() {
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_requests.size() > 0) {
                    list_requests.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Request request = postSnapshot.getValue(Request.class);
                    list_requests.add(request);
                }
                DriverSearchAdapter adapter = new DriverSearchAdapter(DriverSearchFragment.this, list_requests);
                list_data.setAdapter(adapter);

                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(getActivity());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }
}