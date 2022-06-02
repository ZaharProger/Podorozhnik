package com.podorozhnik.adapters;

import com.podorozhnik.fragments.PassengerSearchFragment;
import com.podorozhnik.fragments.AllSearchFragment;
import com.podorozhnik.fragments.DriverSearchFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SearchAdapter extends FragmentStateAdapter {

    public SearchAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AllSearchFragment();
                break;
            case 1:
                fragment = new PassengerSearchFragment();
                break;
            case 2:
                fragment = new DriverSearchFragment();
                break;
        }
        return fragment;}


    @Override
    public int getItemCount() {
        return 3;
    }
}