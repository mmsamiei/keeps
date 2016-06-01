package com.mmsamiei.keeps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Win2 on 5/26/2016.
 */
public class DesicionMaker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
