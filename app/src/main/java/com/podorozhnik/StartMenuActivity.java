package com.podorozhnik;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.fragments.CreateFragment;
import com.podorozhnik.fragments.FindFragment;
import com.podorozhnik.fragments.SearchFragment;

public class StartMenuActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FindFragment findFragment;
    private CreateFragment createFragment;
    private SearchFragment searchFragment;
    public static boolean hasAsyncTasks = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);

        findFragment = new FindFragment();
        createFragment = new CreateFragment();
        searchFragment = new SearchFragment();

        BottomNavigationView appMenu = findViewById(R.id.appMenu);
        appMenu.setOnItemSelectedListener(this);
        appMenu.setSelectedItemId(R.id.searchItem);
    }

    @Override
    public void onBackPressed(){
        if (!hasAsyncTasks)
            finishAffinity();
        else
            Snackbar.make(findViewById(android.R.id.content).getRootView(), R.string.wait_text, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.pure_green))
                    .setTextColor(getColor(R.color.white))
                    .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean isChosen = false;

        if (!hasAsyncTasks){
            if (item.getItemId() == R.id.searchItem){
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, searchFragment)
                        .commit();
            }
            else if (item.getItemId() == R.id.findItem){
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, findFragment)
                        .commit();
            }
            else if (item.getItemId() == R.id.createItem){
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, createFragment)
                        .commit();
            }
        }
        else
            Snackbar.make(findViewById(android.R.id.content).getRootView(), R.string.wait_text, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.pure_green))
                    .setTextColor(getColor(R.color.white))
                    .show();

        return isChosen;
    }
}
