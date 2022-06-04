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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.podorozhnik.R;
import com.podorozhnik.StartMenuActivity;
import com.podorozhnik.entities.Location;
import com.podorozhnik.entities.Request;
import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.final_values.FragmentTags;
import com.podorozhnik.final_values.PrefsValues;
import com.podorozhnik.managers.ConnectionChecker;
import com.podorozhnik.managers.RequestsManager;

public class FindFragment extends DataSendFragment implements View.OnClickListener, View.OnTouchListener{
    private EditText findFromField;
    private EditText findToField;
    private EditText findDateField;
    private Button findConfirmButton;
    private DatePickerFragment datePickerFragment;
    private LocationFragment locationFragment;
    private Location fromLocation;
    private Location toLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.find_fragment_layout, container, false);

        fromLocation = new Location();
        toLocation = new Location();

        findConfirmButton = fragmentView.findViewById(R.id.findConfirmButton);
        findConfirmButton. setOnClickListener(this);

        findFromField = fragmentView.findViewById(R.id.findFromField);
        findToField = fragmentView.findViewById(R.id.findToField);
        findDateField = fragmentView.findViewById(R.id.findDateField);

        datePickerFragment = new DatePickerFragment(FindFragment.this);
        locationFragment = new LocationFragment(FindFragment.this);

        findDateField.setOnTouchListener(this);
        findFromField.setOnTouchListener(this);
        findToField.setOnTouchListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.findConfirmButton){
            String[] dateFieldData = findDateField.getText().toString().split("[\\s]+");
            String findFromText = findFromField.getText().toString();
            String findToText = findToField.getText().toString();

            if (!(findFromText.equals("") || findToText.equals("") || dateFieldData.length != 2)){
                if (ConnectionChecker.checkConnection(getContext())){
                    SharedPreferences prefs = getContext().getSharedPreferences(PrefsValues.PREFS_NAME, Context.MODE_PRIVATE);

                    Request driverRequest = new Request(prefs.getString(PrefsValues.USER_LOGIN, ""), fromLocation,
                            toLocation, dateFieldData[0], dateFieldData[1], false,
                            prefs.getString(PrefsValues.DEVICE_TOKEN, ""));

                    RequestsManager requestsManager = new RequestsManager(driverRequest, FindFragment.this);
                    requestsManager.publishRequest();

                    findFromField.setText("");
                    findToField.setText("");
                    findDateField.setText("");

                    findConfirmButton.setText(R.string.loading_text);
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
    }

    @Override
    public void onDataChanged(Location newLocationData, String newTimeData, String dialogFragmentTag) {
        switch (dialogFragmentTag){
            case FragmentTags.DATE_PICKER_TAG:
                findDateField.setText(newTimeData);
                break;
            case FragmentTags.TIME_PICKER_TAG:
                findDateField.setText(String.format("%s %s", findDateField.getText().toString(), newTimeData));
                break;
            case FragmentTags.LOCATION_FROM_TAG:
                if (newLocationData != null){
                    fromLocation = newLocationData;
                    findFromField.setText(fromLocation.getName());
                }
                else
                    findFromField.setText("");
                break;
            case FragmentTags.LOCATION_TO_TAG:
                if (newLocationData != null){
                    toLocation = newLocationData;
                    findToField.setText(toLocation.getName());
                }
                else
                    findToField.setText("");
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean isTouched = true;

        if (view.getId() == R.id.findDateField){
            if (getParentFragmentManager().findFragmentByTag(FragmentTags.DATE_PICKER_TAG) == null)
                datePickerFragment.show(getParentFragmentManager(), FragmentTags.DATE_PICKER_TAG);
        }
        else if (view.getId() == R.id.findFromField){
            if (ConnectionChecker.checkConnection(getContext())) {
                if (getParentFragmentManager().findFragmentByTag(FragmentTags.LOCATION_FROM_TAG) == null)
                    locationFragment.show(getParentFragmentManager(), FragmentTags.LOCATION_FROM_TAG);
            }
            else
                Snackbar.make(getView(), R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
        }
        else if (view.getId() == R.id.findToField){
            if (ConnectionChecker.checkConnection(getContext())){
                if (getParentFragmentManager().findFragmentByTag(FragmentTags.LOCATION_TO_TAG) == null)
                    locationFragment.show(getParentFragmentManager(), FragmentTags.LOCATION_TO_TAG);
            }
            else
                Snackbar.make(getView(), R.string.lost_connection_text, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getActivity().getColor(R.color.pure_green))
                        .setTextColor(getActivity().getColor(R.color.white))
                        .show();
        }
        else
            isTouched = false;

        return isTouched;
    }

    @Override
    public void onResultReceived(OperationResults result) {
        findConfirmButton.setText(R.string.create_confirm_text);
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