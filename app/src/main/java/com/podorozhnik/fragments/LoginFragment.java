package com.podorozhnik.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.R;
import com.podorozhnik.StartActivity;
import com.podorozhnik.StartMenuActivity;
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.interfaces.EventListener;
import com.podorozhnik.managers.Authorizer;
import com.podorozhnik.managers.ConnectionChecker;

public class LoginFragment extends Fragment implements View.OnClickListener, EventListener {
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
                User userData = new User(enteredLogin, enteredPassword, null);

                Authorizer authorizer = new Authorizer(userData, LoginFragment.this);
                authorizer.doAuthorization();

                loginButton.setText(R.string.loading_text);
                registerButton.setVisibility(View.INVISIBLE);
                StartActivity.hasAsyncTasks = true;
            }
            else if (enteredLogin.isEmpty() || enteredPassword.isEmpty())
                Snackbar.make(view, R.string.no_entered_data_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
            else
                Snackbar.make(view, R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
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
                Snackbar.make(getView(), R.string.wrong_login_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
            case WRONG_PASSWORD:
                Snackbar.make(getView(), R.string.wrong_password_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
            case DATABASE_ERROR:
                Snackbar.make(getView(), R.string.database_error_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
                break;
        }
    }
}
