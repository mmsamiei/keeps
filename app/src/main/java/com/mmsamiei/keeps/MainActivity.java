package com.mmsamiei.keeps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Win2 on 5/24/2016.
 */
public class MainActivity extends Activity{
    private NoteListAdapter adapter;
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = openOrCreateDatabase(Constants.DATABASE_NAME,MODE_PRIVATE,null);
        mydb.execSQL("Create table if not exists notes(title varchar(64) ,color int,description varchar(128),date varchar(16),time varchar(16) )");
        setContentView(R.layout.ac_main);
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        String username ;
        try {
            username= getIntent().getExtras().getString("USER_NAME");
        }catch (Exception e){
            username = prefs.getString(Constants.KEY_USER, "Default");
        }
        TextView wcText = (TextView) findViewById(R.id.wc_text);
        wcText.setText("خوش آمدید " + username);
        Button newNote = (Button) findViewById(R.id.new_note);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        ListView list = (ListView) findViewById(R.id.listView);
        adapter = new NoteListAdapter(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        updateAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode
            , Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String title = data.getExtras().getString("title");
                String description = data.getExtras().getString("note");
                int color = data.getExtras().getInt("color");
                String date = data.getExtras().getString("date");
                String time = data.getExtras().getString("time");
                createAlarm(date,time);
               mydb.execSQL("insert into notes  (title,color,description,date,time) values (' " + title + "','" + Integer.toString(color) +"','"+description+"','"+date+"','"+time +"');");
               updateAdapter();
               // adapter.setNewItem(title,description,color);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean createAlarm(String date,String time){

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



        Intent intent = new Intent(this, MyBroadcastReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        return true;
    }

    private void updateAdapter(){
        Cursor resultSet = mydb.rawQuery("Select * from notes",null);
        resultSet.moveToFirst();

        adapter.clean();
        resultSet.moveToPrevious();
        while (resultSet.moveToNext()){
            String title;
            String description;
            int color;
            title = resultSet.getString(resultSet.getColumnIndex("title"));
            color = resultSet.getInt(resultSet.getColumnIndex("color"));
            description =resultSet.getString(resultSet.getColumnIndex("description"));
            adapter.setNewItem(title,description,color);
        }

    }
}
