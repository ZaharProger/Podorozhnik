package com.podorozhnik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.podorozhnik.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    Button loginButton;
    Button registerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.login_fragment_layout, container, false);

        loginButton = fragmentView.findViewById(R.id.loginButton);
        registerButton = fragmentView.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton){
            //Тут просто входим
        }
        else{
            //Тут переход к регистрации
        }
    }
}
