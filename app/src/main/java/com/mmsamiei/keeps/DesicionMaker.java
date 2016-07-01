package com.mmsamiei.keeps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

/**
 * Created by Win2 on 5/26/2016.
 */
public class DesicionMaker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File(Environment.getExternalStorageDirectory(),"myback");
        if(file.exists()) {
            LinearLayout rootLayout = new LinearLayout(this);
            ImageView image = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootLayout.setLayoutParams(params);
            image.setLayoutParams(params);
            Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            image.setImageBitmap(imageBitmap);
        }
        SharedPreferences prefs =
                getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isLogedIn =
                prefs.getBoolean(Constants.KEY_LOGIN, false);

        if(isLogedIn){
            startActivity(new Intent(DesicionMaker.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(DesicionMaker.this, LoginActivity.class));
            finish();
        }

    }
}
