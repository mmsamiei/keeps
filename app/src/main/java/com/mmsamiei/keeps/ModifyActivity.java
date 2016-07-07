package com.mmsamiei.keeps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
public class ModifyActivity extends Activity implements TimePickerFragment.onTimePass,DatePickerFragment.onDatePass {
    private TextView title,note;
    private ImageView color;
    private ImageView backArrow;
    private TextView select_date;
    private TextView select_time;
    private ImageView delete_img;
    int noteColor;
    String time;
    String date;
    int id;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_modify);
        root=findViewById(R.id.note_root_view);
        title = (TextView)findViewById(R.id.title_txt);
        note = (TextView) findViewById(R.id.note_txt);
        color = (ImageView) findViewById(R.id.pallete);
        backArrow = (ImageView) findViewById(R.id.back_arrow);
        select_date = (TextView) findViewById(R.id.select_date);
        select_time = (TextView) findViewById(R.id.select_time);
        delete_img = (ImageView) findViewById(R.id.deleteIcon);
        noteColor= Color.WHITE;


        title.setText(getIntent().getExtras().getString("OLD_TITLE"));
        note.setText(getIntent().getExtras().getString("OLD_DESCRIPTION"));
        noteColor=getIntent().getExtras().getInt("OLD_COLOR");
        root.setBackgroundColor(noteColor);
        id=getIntent().getExtras().getInt("ID");



        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this,ColorDialog.class);
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

        delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("ID",id);
                                setResult(99,resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
        resultIntent.putExtra("ID",id);
        if(title.getText().toString().equals("") && note.getText().toString().equals("") )
            setResult(RESULT_CANCELED,resultIntent);
        else {
            setResult(RESULT_OK, resultIntent);
        }
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