package com.podorozhnik.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private CreateFragment fragmentReference;

    public TimePickerFragment(CreateFragment fragmentReference){
        this.fragmentReference = fragmentReference;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                                    DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String preparedHours = (hourOfDay < 10)? "0" : "";
        String preparedMinutes = (minute < 10)? "0" : "";

        preparedHours += hourOfDay;
        preparedMinutes += minute;

        fragmentReference.onDateChanged(String.format("%s:%s", preparedHours, preparedMinutes), getTag());
        dismiss();
    }
}
