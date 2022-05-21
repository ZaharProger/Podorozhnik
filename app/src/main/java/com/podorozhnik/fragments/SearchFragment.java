package com.podorozhnik.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.podorozhnik.R;
import com.podorozhnik.adapters.SearchAdapter;
import com.podorozhnik.adapters.TabMenuScrollerAdapter;
import com.podorozhnik.entities.Request;
import com.podorozhnik.entities.User;
import com.podorozhnik.final_values.DatabaseValues;
import com.podorozhnik.managers.RequestsManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {
    private ListView list_data;
    private ProgressBar circular_progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private  String REQUESTS_TABLE = "requests";
    private RequestsManager requestsManager;
    private List<Request> list_requests = new ArrayList<>();
   // private User selectedUser; //hold selected user

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.search_fragment_layout, container, false);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.search_fragment_layout);

        //Add toolbar
      //  Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
       // toolbar.setTitle("Firebase demo");
        //setSupportActionBar(toolbar);

        //Control
        circular_progress = (ProgressBar) fragmentView.findViewById(R.id.circular_progress);
       // input_name = (EditText) fragmentView.findViewById(R.id.name);
       // input_email = (EditText) fragmentView.findViewById(R.id.email);
        list_data = (ListView) fragmentView.findViewById(R.id.list_data);



        //Firebase
        initFirebase();
        addEventFirebaseListener();
        return fragmentView;

    }

    private void addEventFirebaseListener() {
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("requests")                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (list_requests.size() > 0) {
                            list_requests.clear();
                        }
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Request request = postSnapshot.getValue(Request.class);
                            list_requests.add(request);
                        }
                        SearchAdapter adapter = new SearchAdapter(SearchFragment.this, list_requests);
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/

/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.search_fragment_layout, container, false);



        TabLayout tabMenu = fragmentView.findViewById(R.id.tabMenu);
        ViewPager2 menuScroller = fragmentView.findViewById(R.id.menuScroller);

        //Это для временной демонстрации - потом само собой уберем :)
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RequestsFragment("Здесь будут отображаться все запросы"));
        fragments.add(new RequestsFragment("А тут только запросы пассажиров"));
        fragments.add(new RequestsFragment("И наконец здесь - созданные поездки :)"));

        menuScroller.setAdapter(new TabMenuScrollerAdapter(getActivity(), fragments));

        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_find);
        icons.add(R.drawable.ic_create);

        new TabLayoutMediator(tabMenu, menuScroller, (tab, position) -> {
            if (position != 0)
                tab.setIcon(icons.get(position - 1));
            else
                tab.setText(R.string.all_item_title);
        }).attach();

        return fragmentView;
    }*/
}
