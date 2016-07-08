package com.mmsamiei.keeps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Win2 on 5/24/2016.
 */
public class MainActivity extends Activity {
    private NoteListAdapter adapter;
    private SQLiteDatabase mydb;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = openOrCreateDatabase(Constants.DATABASE_NAME,MODE_PRIVATE,null);
        mydb.execSQL("Create table if not exists notes( id INTEGER PRIMARY KEY  , title varchar(64) ,color int,description varchar(128),date varchar(16),time varchar(16) )");
        setContentView(R.layout.ac_main);
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        String username ;
        try {
            username= getIntent().getExtras().getString("USER_NAME");
        }catch (Exception e){
            username = prefs.getString(Constants.KEY_USER, "Default");
        }
        TextView wcText = (TextView) findViewById(R.id.wc_text);
        //wcText.setText("خوش آمدید " + username);
        Button newNote = (Button) findViewById(R.id.new_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        final ListView list = (ListView) findViewById(R.id.listView);
        adapter = new NoteListAdapter(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        //
        updateAdapter();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(MainActivity.this,ModifyActivity.class);
                intent.putExtra("OLD_TITLE",adapter.data.get(position).title);
                intent.putExtra("OLD_DESCRIPTION",adapter.data.get(position).description);
                intent.putExtra("OLD_COLOR",adapter.data.get(position).color);
                intent.putExtra("ID",adapter.data.get(position).id);



                startActivityForResult(intent,2);
                // mydb.execSQL("delete from notes where id="+adapter.data.get(position).id);
                // mydb.execSQL("update notes set title='ali',description='iranii'  where id="+adapter.data.get(position).id);
                updateAdapter();
                adapter.notifyDataSetChanged();
            }
        });

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode
            , Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("note");
                int color = data.getExtras().getInt("color");
                int id;
                String date = data.getExtras().getString("date");
                String time = data.getExtras().getString("time");
                mydb.execSQL("insert into notes  (title,color,description,date,time) values (' " + title + "','" + Integer.toString(color) +"','"+description+"','"+date+"','"+time +"');");
                Cursor c = mydb.rawQuery("select last_insert_rowid() as x ;",null);
                c.moveToFirst();
                id =c.getInt(c.getColumnIndex("x"));
                updateAdapter();
                if(date != null && time != null) {
                    Log.d("STAR","XXX");
                    createAlarm(id, date, time, title);
                }
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("note");
                int color = data.getExtras().getInt("color");
                int id = data.getExtras().getInt("ID");
                String date = data.getExtras().getString("date");
                String time = data.getExtras().getString("time");
                mydb.execSQL("update notes set title='"+title+"',description='"+description+"',color="+color+",time='"+time+"',date='"+date+"'  where id="+id);                updateAdapter();
                deleteAlarm(id);
                createAlarm(id,date,time,title);
                updateAdapter();
                adapter.notifyDataSetChanged();
            }
            if(resultCode==99){
                int id = data.getExtras().getInt("ID");
                mydb.execSQL("delete from notes  where id="+id);
                deleteAlarm(id);
                updateAdapter();
                adapter.notifyDataSetChanged();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean createAlarm(int id,String date,String time,String title){

        String[] s1 = date.split("/");
        String[] s2 = time.split(":");
        int year = Integer.parseInt(s1[0]);
        int month = Integer.parseInt(s1[1]);
        int day = Integer.parseInt(s1[2]);
        int hour = Integer.parseInt(s2[0]);
        int minute = Integer.parseInt(s2[1]);



        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();
        calendar.set(year,month,day,hour,minute);


        Notification notification = getNotification(title);

        Intent intent = new Intent(this, MyBroadcastReciver.class);
        intent.putExtra("Notification",notification);
        intent.putExtra("ID",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        return true;
    }


    public boolean deleteAlarm(int id){
        try{
            Intent intent = new Intent(this, MyBroadcastReciver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), id, intent,0);
            AlarmManager am=(AlarmManager)this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
            Log.d("TAG","true");
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("KEEPS NOTIFICATION");
        builder.setAutoCancel(true);
        builder.setContentText(content);
        builder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setSound(alarmSound);

        /* TODO
        icon Picture
         */

        builder.setSmallIcon(R.drawable.notification_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return builder.build();
        }
        return null;
    }

    private void updateAdapter(){
        Cursor resultSet = mydb.rawQuery("Select * from notes",null);
        resultSet.moveToFirst();

        adapter.clean();
        resultSet.moveToPrevious();
        while (resultSet.moveToNext()){
            int id;
            String title;
            String description;
            int color;
            id = resultSet.getInt(resultSet.getColumnIndex("id"));
            title = resultSet.getString(resultSet.getColumnIndex("title"));
            color = resultSet.getInt(resultSet.getColumnIndex("color"));
            description =resultSet.getString(resultSet.getColumnIndex("description"));
            adapter.setNewItem(id,title,description,color);
        }
    }


}