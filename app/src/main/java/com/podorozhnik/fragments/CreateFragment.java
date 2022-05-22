package com.podorozhnik.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.R;
import com.podorozhnik.StartMenuActivity;
import com.podorozhnik.entities.Request;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.final_values.PrefsValues;
import com.podorozhnik.managers.ConnectionChecker;
import com.podorozhnik.managers.RequestsManager;

public class CreateFragment extends DataSendFragment implements View.OnClickListener, View.OnTouchListener{
    private EditText createFromField;
    private EditText createToField;
    private EditText createDateField;
    private Button createConfirmButton;
    private ImageButton createFromButton;
    private ImageButton createToButton;
    private DatePickerFragment datePickerFragment;
    private LocationFragment locationFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.create_fragment_layout, container, false);

        createConfirmButton = fragmentView.findViewById(R.id.createConfirmButton);
        createConfirmButton.setOnClickListener(this);
        createFromButton = fragmentView.findViewById(R.id.createFromButton);
        createFromButton.setOnClickListener(this);
        createToButton = fragmentView.findViewById(R.id.createToButton);
        createToButton.setOnClickListener(this);

        createFromField = fragmentView.findViewById(R.id.createFromField);
        createToField = fragmentView.findViewById(R.id.createToField);
        createDateField = fragmentView.findViewById(R.id.createDateField);

        createDateField.setOnTouchListener(this);

        datePickerFragment = new DatePickerFragment(CreateFragment.this);
        locationFragment = new LocationFragment(CreateFragment.this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createConfirmButton){
            String fromFieldData = createFromField.getText().toString().trim();
            String toFieldData = createToField.getText().toString().trim();
            String[] dateFieldData = createDateField.getText().toString().split("[\\s]+");

            if (!(fromFieldData.equals("") || toFieldData.equals("") || dateFieldData.length != 2)){
                if (ConnectionChecker.checkConnection(getContext())){
                    String userLogin = "";
                    SharedPreferences prefs = getContext().getSharedPreferences(PrefsValues.PREFS_NAME, Context.MODE_PRIVATE);
                    if (prefs.contains(PrefsValues.USER_LOGIN))
                        userLogin = prefs.getString(PrefsValues.USER_LOGIN, "");

                    Request driverRequest = new Request(userLogin, fromFieldData, toFieldData, dateFieldData[0],
                                                        dateFieldData[1], true);

                    RequestsManager requestsManager = new RequestsManager(driverRequest, CreateFragment.this);
                    requestsManager.publishRequest();

                    createFromField.setText("");
                    createToField.setText("");
                    createDateField.setText("");

                    createConfirmButton.setText(R.string.loading_text);
                    StartMenuActivity.hasAsyncTasks = true;
                }
                else
                    Snackbar.make(view, R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                            .setTextColor(getActivity().getColor(R.color.white))
                            .show();
            }
            else
                Snackbar.make(view, R.string.no_entered_data_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
        }
        else if (view.getId() == R.id.createFromButton){
            if (ConnectionChecker.checkConnection(getContext())){
                locationFragment.setUserData(createFromField.getText().toString());
                locationFragment.show(getParentFragmentManager(), "locationFrom");
            }
            else
                Snackbar.make(getView(), R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
        }
        else if (view.getId() == R.id.createToButton){
            if (ConnectionChecker.checkConnection(getContext())){
                locationFragment.setUserData(createToField.getText().toString());
                locationFragment.show(getParentFragmentManager(), "locationTo");
            }
            else
                Snackbar.make(getView(), R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
        }
    }

    @Override
    public void onDataChanged(String newData, String dialogFragmentTag) {
        switch (dialogFragmentTag) {
            case FragmentTags.DATE_PICKER_TAG:
                createDateField.setText(newData);
                break;
            case FragmentTags.TIME_PICKER_TAG:
                createDateField.setText(String.format("%s %s", createDateField.getText().toString(), newData));
                break;
            case FragmentTags.LOCATION_FROM_TAG:
                createFromField.setText(newData);
                break;
            case FragmentTags.LOCATION_TO_TAG:
                createToField.setText(newData);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean isTouched;
        if (view.getId() == R.id.createDateField){
            if (getParentFragmentManager().findFragmentByTag(FragmentTags.DATE_PICKER_TAG) == null)
                datePickerFragment.show(getParentFragmentManager(), FragmentTags.DATE_PICKER_TAG);
            isTouched = true;
        }
        else
            isTouched = false;

        return isTouched;
    }

    @Override
    public void onResultReceived(OperationResults result) {
        createConfirmButton.setText(R.string.create_confirm_text);
        StartMenuActivity.hasAsyncTasks = false;

        switch (result){
            case SUCCESS:
                Snackbar.make(getView(), R.string.success_insertion_text, Snackbar.LENGTH_LONG)
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
