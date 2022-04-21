package com.podorozhnik.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.podorozhnik.R;
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.managers.Authorizer;

public class LoginFragment extends Fragment implements View.OnClickListener {
    Button loginButton;
    Button registerButton;
    EditText loginField;
    EditText passwordField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.login_fragment_layout, container, false);

        loginButton = fragmentView.findViewById(R.id.loginButton);
        registerButton = fragmentView.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        loginField = fragmentView.findViewById(R.id.loginField);
        passwordField = fragmentView.findViewById(R.id.passwordField);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton){
            String enteredLogin = loginField.getText().toString().trim();
            String enteredPassword = passwordField.getText().toString().trim();

            User userData = new User(-1, enteredLogin, enteredPassword);

            if (!(enteredLogin.isEmpty() || enteredPassword.isEmpty()) && checkConnection()){
                Authorizer authorizer = new Authorizer(userData, LoginFragment.this);

                authorizer.doAuthorization();
                loginButton.setText(R.string.loading_text);
            }
            else if (enteredLogin.isEmpty() || enteredPassword.isEmpty())
                Toast.makeText(getContext(), R.string.no_entered_data_text, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), R.string.lost_connection_text, Toast.LENGTH_LONG).show();
        }
        else{
            //Тут переход к регистрации
        }
    }

    private boolean checkConnection(){
        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET || activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                isConnected = true;
        }

        return isConnected;
    }

    public void onResultReceived(OperationResults result){
        loginButton.setText(R.string.login_button_text);

        switch(result){
            case SUCCESS:
                //Тут входим в приложение
                break;
            case WRONG_LOGIN:
                Toast.makeText(getContext(), R.string.wrong_login_text, Toast.LENGTH_LONG).show();
                break;
            case WRONG_PASSWORD:
                Toast.makeText(getContext(), R.string.wrong_password_text, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
