package com.podorozhnik.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.podorozhnik.final_values.FragmentTags;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TimePickerFragment timePickerFragment;
    private CreateFragment fragmentReference;

    public DatePickerFragment(CreateFragment fragmentReference){
        timePickerFragment = new TimePickerFragment(fragmentReference);
        this.fragmentReference = fragmentReference;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c = Calendar.getInstance();

        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, currentYear, currentMonth, currentDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String preparedMonth = (month < 10)? "0" : "";
        preparedMonth += month;

        timePickerFragment.show(getParentFragmentManager(), FragmentTags.TIME_PICKER_TAG);
        fragmentReference.onDateChanged(String.format("%d.%s.%d", dayOfMonth, preparedMonth, year), getTag());
        dismiss();
    }
}
