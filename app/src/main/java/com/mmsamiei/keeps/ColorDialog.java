package com.mmsamiei.keeps;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Win2 on 5/25/2016.
 */
public class ColorDialog extends Dialog {
    Button red,blue,green;
    int colorCode;
    public ColorDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        red = (Button) findViewById(R.id.btn_blue);
        blue = (Button) findViewById(R.id.btn_blue);
        green = (Button) findViewById(R.id.btn_green);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCode = Color.RED;
            }
        });
    }
}
