package com.podorozhnik;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.podorozhnik.fragments.LoginFragment;

public class StartActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.authContainer, new LoginFragment()).commit();
    }
}
