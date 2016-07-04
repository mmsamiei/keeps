package com.mmsamiei.keeps;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Win2 on 7/4/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface onTimePass {
        public void onTimePass(String data);
    }
    onTimePass dataPasser;
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (onTimePass) a;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Intent intent = new Intent();
        String s= Integer.toString(hourOfDay)+":"+Integer.toString(minute);
        dataPasser.onTimePass(s);
    }
}
