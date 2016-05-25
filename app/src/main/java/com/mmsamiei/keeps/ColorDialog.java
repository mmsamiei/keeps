package com.mmsamiei.keeps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Win2 on 5/25/2016.
 */
public class ColorDialog extends Activity {
    Button red,green,blue,purple,yellow,gray;
    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.di_color_picker);
        super.onCreate(savedInstanceState);
        red = (Button) findViewById(R.id.btn_redbtn);
        green = (Button) findViewById(R.id.btn_greenbtn);
        blue = (Button) findViewById(R.id.btn_bluebtn);
        purple = (Button) findViewById(R.id.btn_purplebtn);
        yellow = (Button) findViewById(R.id.btn_yellowbtn);
        gray = (Button) findViewById(R.id.btn_graybtn);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.RED;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.GREEN;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.BLUE;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.parseColor("#800080");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.parseColor("#FFFF00");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.parseColor("#808080");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedColor",color);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
    }
}
