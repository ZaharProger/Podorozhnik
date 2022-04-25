package com.podorozhnik.fragments;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;

import com.podorozhnik.R;
import com.podorozhnik.StartActivity;
import com.podorozhnik.StartMenuActivity;
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.interfaces.DatabaseEventListener;
import com.podorozhnik.managers.Authorizer;
import com.podorozhnik.managers.ConnectionChecker;

public class LoginFragment extends Fragment implements View.OnClickListener, DatabaseEventListener {
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

            if (!(enteredLogin.isEmpty() || enteredPassword.isEmpty()) && ConnectionChecker.checkConnection(getContext())){
                User userData = new User(-1, enteredLogin, enteredPassword);

                Authorizer authorizer = new Authorizer(userData, LoginFragment.this);
                authorizer.doAuthorization();

                loginButton.setText(R.string.loading_text);
                registerButton.setVisibility(View.INVISIBLE);
                StartActivity.hasAsyncTasks = true;
            }
            else if (enteredLogin.isEmpty() || enteredPassword.isEmpty())
                Toast.makeText(getContext(), R.string.no_entered_data_text, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), R.string.lost_connection_text, Toast.LENGTH_LONG).show();
        }
        else{
            FragmentManager fragmentManager = getParentFragmentManager();

            fragmentManager.beginTransaction()
                                    .remove(fragmentManager.findFragmentByTag(FragmentTags.LOGIN_TAG))
                                    .add(R.id.authContainer, new RegisterFragment(), FragmentTags.REGISTER_TAG)
                                    .commit();
        }
    }

    @Override
    public void onResultReceived(OperationResults result){
        loginButton.setText(R.string.login_button_text);
        registerButton.setVisibility(View.VISIBLE);
        StartActivity.hasAsyncTasks = false;

        switch(result){
            case SUCCESS:
                Intent intent = new Intent(getContext(), StartMenuActivity.class);
                startActivity(intent);
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
