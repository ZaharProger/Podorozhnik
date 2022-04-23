package com.podorozhnik.fragments;

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
import com.podorozhnik.entities.User;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.interfaces.DatabaseEventListener;
import com.podorozhnik.managers.AccountCreator;
import com.podorozhnik.managers.ConnectionChecker;

public class RegisterFragment extends Fragment implements View.OnClickListener, DatabaseEventListener {
    Button createAccountButton;
    EditText loginField;
    EditText passwordField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.registration_fragment_layout, container, false);

        createAccountButton = fragmentView.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this);

        loginField = fragmentView.findViewById(R.id.createLoginField);
        passwordField = fragmentView.findViewById(R.id.createPasswordField);

        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        String enteredLogin = loginField.getText().toString().trim();
        String enteredPassword = passwordField.getText().toString().trim();

        if (!(enteredLogin.isEmpty() || enteredPassword.isEmpty()) && ConnectionChecker.checkConnection(getContext())){
            User userData = new User(-1, enteredLogin, enteredPassword);

            AccountCreator accountCreator = new AccountCreator(userData, RegisterFragment.this);
            accountCreator.createAccount();

            createAccountButton.setText(R.string.loading_text);
            StartActivity.hasAsyncTasks = true;
        }
        else if (enteredLogin.isEmpty() || enteredPassword.isEmpty())
            Toast.makeText(getContext(), R.string.no_entered_data_text, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), R.string.lost_connection_text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResultReceived(OperationResults result){
        createAccountButton.setText(R.string.register_button_text);
        StartActivity.hasAsyncTasks = false;

        switch(result){
            case SUCCESS:
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .remove(fragmentManager.findFragmentByTag(FragmentTags.REGISTER_TAG))
                        .add(R.id.authContainer, new LoginFragment(), FragmentTags.LOGIN_TAG)
                        .commit();

                Toast.makeText(getContext(), R.string.success_registration_text, Toast.LENGTH_LONG).show();
                break;
            case EXISTING_LOGIN:
                Toast.makeText(getContext(), R.string.existing_login_text, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
