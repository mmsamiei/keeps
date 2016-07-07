package com.mmsamiei.keeps;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Win2 on 7/6/2016.
 */
public class SearchActivity extends Activity {
    private NoteListAdapter adapter;
    private SQLiteDatabase mydb;
    private Button goButton;
    private EditText searchEdt;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = openOrCreateDatabase(Constants.DATABASE_NAME,MODE_PRIVATE,null);
        setContentView(R.layout.ac_search);
        ListView list = (ListView) findViewById(R.id.SearchlistView);
        adapter = new NoteListAdapter(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        goButton = (Button) findViewById(R.id.GO_button);
        searchEdt = (EditText) findViewById(R.id.Search_edt);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(searchEdt.getText().toString());
            }
        });
        back = (Button) findViewById(R.id.back_searchAc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void update(String word){

        Cursor resultSet = mydb.rawQuery("Select * from notes where title like '%"+word+"%' or " +
                "description like '%"+word+"%' ",null);
        resultSet.moveToFirst();

        adapter.clean();

        if(resultSet.getCount()==0)
            Toast.makeText(getApplicationContext(), "No Match found", Toast.LENGTH_LONG).show();


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
        adapter.notifyDataSetChanged();
    }
}