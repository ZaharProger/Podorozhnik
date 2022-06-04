package com.podorozhnik;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.final_values.APIValues;
import com.podorozhnik.final_values.PrefsValues;
import com.podorozhnik.fragments.CreateFragment;
import com.podorozhnik.fragments.FindFragment;
import com.podorozhnik.fragments.MapFragment;
import com.podorozhnik.fragments.SearchFragment;

import me.pushy.sdk.Pushy;

public class StartMenuActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FindFragment findFragment;
    private CreateFragment createFragment;
    private SearchFragment searchFragment;
    private MapFragment mapFragment;
    public static boolean hasAsyncTasks = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pushy.listen(getApplicationContext());
        setContentView(R.layout.menu_activity_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        checkPermissions();

        findFragment = new FindFragment();
        createFragment = new CreateFragment();
        searchFragment = new SearchFragment();
        mapFragment = new MapFragment();

        BottomNavigationView appMenu = findViewById(R.id.appMenu);
        appMenu.setOnItemSelectedListener(this);

        Intent notificationIntent = getIntent();
        if (notificationIntent.hasExtra(APIValues.REQUEST_FROM) && notificationIntent.hasExtra(APIValues.REQUEST_TO)) {
            SharedPreferences prefs = getSharedPreferences(PrefsValues.PREFS_NAME, MODE_PRIVATE);

            if (prefs.contains(PrefsValues.DEPARTURE_POINT))
                prefs.edit().remove(PrefsValues.DEPARTURE_POINT).apply();
            if (prefs.contains(PrefsValues.DESTINATION_POINT))
                prefs.edit().remove(PrefsValues.DESTINATION_POINT).apply();

            prefs.edit().putString(PrefsValues.DEPARTURE_POINT, notificationIntent
                    .getStringExtra(APIValues.REQUEST_FROM)).apply();
            prefs.edit().putString(PrefsValues.DESTINATION_POINT, notificationIntent
                    .getStringExtra(APIValues.REQUEST_TO)).apply();

            appMenu.setSelectedItemId(R.id.mapItem);
        } else
            appMenu.setSelectedItemId(R.id.searchItem);
    }

    @Override
    public void onBackPressed() {
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

        if (!hasAsyncTasks) {
            if (item.getItemId() == R.id.searchItem) {
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, searchFragment)
                        .commit();
            } else if (item.getItemId() == R.id.findItem) {
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, findFragment)
                        .commit();
            } else if (item.getItemId() == R.id.createItem) {
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, createFragment)
                        .commit();
            } else if (item.getItemId() == R.id.mapItem) {
                isChosen = true;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuItemContainer, mapFragment)
                        .commit();
            }
        } else
            Snackbar.make(findViewById(android.R.id.content).getRootView(), R.string.wait_text, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.pure_green))
                    .setTextColor(getColor(R.color.white))
                    .show();

        return isChosen;
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                                0, location -> {
                                    SharedPreferences prefs = getSharedPreferences(PrefsValues.PREFS_NAME, MODE_PRIVATE);
                                    prefs.edit().putString(PrefsValues.CURRENT_LAT, location.getLatitude() + "").apply();
                                    prefs.edit().putString(PrefsValues.CURRENT_LON, location.getLongitude() + "").apply();
                                });
                    } catch (SecurityException exception) {
                        checkPermissions();
                    }
                }
            }
        }
    }
}

