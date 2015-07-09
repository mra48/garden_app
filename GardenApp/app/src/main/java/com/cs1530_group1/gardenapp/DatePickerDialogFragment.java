package com.cs1530_group1.gardenapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;


import java.util.Calendar;

/**
 * This DialogFragment can be used for giving the user an easy way to pick dates
 *
 * @author Charles Smith <cas275@pitt.edu>
 */
public class DatePickerDialogFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

    @Override
    /**
     * called when the dialog is created
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialogFragment and return it
        new DatePickerDialogFragment();
        return new DatePickerDialog(getActivity(), this , year, month, day);

    }

    @Override
    /**
     * called when the date is set
     */
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //todo update textview with info
    }
}
