package com.podorozhnik;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.podorozhnik.fragments.CreateFragment;
import com.podorozhnik.fragments.FindFragment;

public class StartMenuActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FindFragment findFragment;
    private CreateFragment createFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);

        findFragment = new FindFragment();
        createFragment = new CreateFragment();

        BottomNavigationView appMenu = findViewById(R.id.appMenu);
        appMenu.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean isChosen = false;

        if (item.getItemId() == R.id.findItem){
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

        return isChosen;
    }
}
