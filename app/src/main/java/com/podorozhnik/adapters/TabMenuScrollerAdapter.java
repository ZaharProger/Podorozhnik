package com.podorozhnik.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class TabMenuScrollerAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> menuItems;

    public TabMenuScrollerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> menuItems){
        super(fragmentActivity);

        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return menuItems.get(position);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
