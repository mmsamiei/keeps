package com.mmsamiei.keeps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Win2 on 5/24/2016.
 */
public class MainActivity extends Activity{
    private NoteListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode
            , Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String title = data.getExtras().getString("title");
                String note = data.getExtras().getString("note");
                int color = data.getExtras().getInt("color");
                adapter.setNewItem(title,note,color);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
