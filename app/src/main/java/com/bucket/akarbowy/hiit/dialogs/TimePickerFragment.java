package com.bucket.akarbowy.hiit.dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by arkadiuszkarbowy on 10/12/15.
 */

public  class TimePickerFragment extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private Calendar mCurrentCalendar;

    public TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener onTimeSetListener, Calendar
            calendar) {
        TimePickerFragment pickerFragment = new TimePickerFragment();
        pickerFragment.setCurrentCalendar(calendar);
        pickerFragment.setOnTimeSetListener(onTimeSetListener);
        return pickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = mCurrentCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentCalendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    private void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }

    private void setCurrentCalendar(Calendar calendar){
        mCurrentCalendar = calendar;
    }
}