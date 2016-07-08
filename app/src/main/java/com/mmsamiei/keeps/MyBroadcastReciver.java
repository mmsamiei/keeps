package com.mmsamiei.keeps;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Win2 on 7/5/2016.
 */
public class MyBroadcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id;
        id=intent.getExtras().getInt("ID");
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra("Notification");
        notificationManager.notify(id, notification);




        /*TODO

            add notificationID

          */
    }
}
