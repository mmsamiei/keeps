package com.mmsamiei.keeps;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Win2 on 5/24/2016.
 */
public class NoteActivity extends Activity implements TimePickerFragment.onTimePass,DatePickerFragment.onDatePass {
    private TextView title,note;
    private ImageView color;
    private ImageView backArrow;
    private TextView select_date;
    private TextView select_time;
    int noteColor;
    String time;
    String date;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_note);
        root=findViewById(R.id.note_root_view);
        title = (TextView)findViewById(R.id.title_txt);
        note = (TextView) findViewById(R.id.note_txt);
        color = (ImageView) findViewById(R.id.pallete);
        backArrow = (ImageView) findViewById(R.id.back_arrow);
        select_date = (TextView) findViewById(R.id.select_date);
        select_time = (TextView) findViewById(R.id.select_time);
        noteColor= Color.WHITE;

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,ColorDialog.class);
                startActivityForResult(intent,2);
            }
        });

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"DatePicker");
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                noteColor = data.getExtras().getInt("selectedColor");
            }
            root.setBackgroundColor(noteColor);
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("title",title.getText().toString());
        resultIntent.putExtra("note",note.getText().toString());
        resultIntent.putExtra("color",noteColor);
        resultIntent.putExtra("time",time);
        resultIntent.putExtra("date",date);
        if(title.getText().toString().equals("") && note.getText().toString().equals("") )
            setResult(RESULT_CANCELED,resultIntent);
        else
            setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    public void onTimePass(String data) {
        time=data;
        select_time.setText(time);
    }

    @Override
    public void onDatePass(String data) {
        date=data;
        select_date.setText(date);
    }
}
