package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.podorozhnik.R;
import com.podorozhnik.StartActivity;
import com.podorozhnik.adapters.SearchAdapter;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.interfaces.DatabaseEventListener;

public class SearchFragment extends Fragment implements  DatabaseEventListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.search_fragment_layout, container, false);
        super.onCreate(savedInstanceState);

        tabLayout = fragmentView.findViewById(R.id.tabLayout);
        viewPager2 = fragmentView.findViewById(R.id.viewPager2);


        tabLayout.addTab(tabLayout.newTab().setText("Всё"));
        tabLayout.addTab(tabLayout.newTab().setText("Водитель"));
        tabLayout.addTab(tabLayout.newTab().setText("Пассажир"));

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new SearchAdapter(fragmentManager , getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });viewPager2.setSaveEnabled(false);

        return fragmentView;

    }

    public void onResultReceived(OperationResults result){
        StartActivity.hasAsyncTasks = true;

        switch(result){
            case SUCCESS:
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .remove(fragmentManager.findFragmentByTag(FragmentTags.REGISTER_TAG))
                        .add(R.id.authContainer, new LoginFragment(), FragmentTags.LOGIN_TAG)
                        .commit();

                Snackbar.make(getView(), R.string.success_registration_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
            case EXISTING_LOGIN:
                Snackbar.make(getView(), R.string.existing_login_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
            case DATABASE_ERROR:
                Snackbar.make(getView(), R.string.database_error_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
        }

/*
    private void initFirebase() {
        FirebaseApp.initializeApp(getActivity());
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }
*/
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
}}