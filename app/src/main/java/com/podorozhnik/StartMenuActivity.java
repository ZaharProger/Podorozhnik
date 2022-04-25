package com.podorozhnik;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StartMenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_start_layout);
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}
