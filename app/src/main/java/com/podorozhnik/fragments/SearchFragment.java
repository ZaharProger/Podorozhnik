package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.podorozhnik.R;
import com.podorozhnik.adapters.TabMenuScrollerAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

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
    }
}
