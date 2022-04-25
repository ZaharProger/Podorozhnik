package com.podorozhnik;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.podorozhnik.fragments.ChooseFragment;

public class StartMenuActivity extends AppCompatActivity {
    private ChooseFragment chooseFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);

        chooseFragment = new ChooseFragment();

        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.menuItemContainer, chooseFragment)
                                    .commit();
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}
