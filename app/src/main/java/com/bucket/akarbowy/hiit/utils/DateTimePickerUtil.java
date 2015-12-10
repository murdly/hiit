package com.bucket.akarbowy.hiit.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.bucket.akarbowy.hiit.dialogs.DatePickerFragment;
import com.bucket.akarbowy.hiit.dialogs.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by akarbowy on 10.12.2015.
 */
public class DateTimePickerUtil {

    private OnDateTimeSet mListener;

    public interface OnDateTimeSet {
        void onDateSet(String date);

        void onTimeSet(String time);
    }

    private Calendar mCalendar, mMinDate;
    private SimpleDateFormat mDateFormat, mTimeFormat;
    private FragmentManager mFm;

    public DateTimePickerUtil(FragmentManager fm) {
        this.mFm = fm;
        mDateFormat = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault());
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mCalendar = Calendar.getInstance();
    }

    public void setOnDateTimeSetListener(OnDateTimeSet listener){
        this.mListener = listener;
    }

    public void setMinDate(Calendar minDate){
        this.mMinDate = minDate;
    }

    public void showDatePicker() {
        new DatePickerFragment().newInstance(mDateListener, mCalendar, mMinDate).show(mFm, "calendar");
    }

    public void showTimePicker() {
        new TimePickerFragment().newInstance(mTimeListener, mCalendar).show(mFm, "time");
    }

    public Calendar getCalendar(){
        return mCalendar;
    }

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(year, monthOfYear, dayOfMonth);
            mListener.onDateSet(mDateFormat.format(mCalendar.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            mListener.onTimeSet(mTimeFormat.format(mCalendar.getTime()));
        }
    };
}
