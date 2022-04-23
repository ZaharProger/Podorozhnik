package com.podorozhnik;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.fragments.LoginFragment;

public class StartActivity extends AppCompatActivity {
    public static boolean hasAsyncTasks = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);

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
            Toast.makeText(getApplicationContext(), R.string.wait_text, Toast.LENGTH_LONG).show();
    }
}
