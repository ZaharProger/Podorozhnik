package com.podorozhnik;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.fragments.LoginFragment;

public class StartActivity extends AppCompatActivity {
    /*
        Для блокировки перехода по окнам во время выполнения запросов к серверу,
        чтобы избежать конфликтов между потоками
     */
    public static boolean hasAsyncTasks = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportFragmentManager().beginTransaction()
                                    .add(R.id.authContainer, new LoginFragment(), FragmentTags.LOGIN_TAG)
                                    .commit();
    }

    @Override
    public void onBackPressed(){
        if (!hasAsyncTasks){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment actualFragment = fragmentManager.findFragmentByTag(FragmentTags.REGISTER_TAG);

            if (actualFragment != null){
                fragmentManager.beginTransaction()
                        .remove(actualFragment)
                        .add(R.id.authContainer, new LoginFragment(), FragmentTags.LOGIN_TAG)
                        .commit();
            }
            else
                finishAffinity();
        }
        else
            Snackbar.make(findViewById(android.R.id.content).getRootView(), R.string.wait_text, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.pure_green))
                    .setTextColor(getColor(R.color.white))
                    .show();
    }
}
