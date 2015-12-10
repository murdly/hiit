package com.bucket.akarbowy.hiit.dialogs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by arkadiuszkarbowy on 10/12/15.
 */
public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar mCurrentCalendar, mMinDate;

    public DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener onDateSetListener, Calendar calendar, Calendar minDate) {
        DatePickerFragment pickerFragment = new DatePickerFragment();
        pickerFragment.setOnDateSetListener(onDateSetListener);
        pickerFragment.setCurrentCalendar(calendar);
        pickerFragment.setMinDate(minDate);
        return pickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = mCurrentCalendar.get(Calendar.YEAR);
        int month = mCurrentCalendar.get(Calendar.MONTH);
        int day = mCurrentCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        dialog.getDatePicker().setMinDate(mMinDate.getTimeInMillis());
        return dialog;
    }

    private void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.onDateSetListener = listener;
    }

    private void setCurrentCalendar(Calendar calendar){
        mCurrentCalendar = calendar;
    }

    public void setMinDate(Calendar minDate) {
        this.mMinDate = minDate;
    }
}

