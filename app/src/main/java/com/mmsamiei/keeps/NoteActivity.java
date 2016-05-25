package com.mmsamiei.keeps;

import android.app.Activity;
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

/**
 * Created by Win2 on 5/24/2016.
 */
public class NoteActivity extends Activity {
    private TextView title,note;
    private ImageView color;
    int noteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_note);
        title = (TextView)findViewById(R.id.title_txt);
        note = (TextView) findViewById(R.id.note_txt);
        color = (ImageView) findViewById(R.id.pallete);
        noteColor= Color.WHITE;
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,ColorDialog.class);
                startActivityForResult(intent,2);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                noteColor = data.getExtras().getInt("selectedColor");
            }
        }
        Log.d("color",Integer.toString(noteColor));
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("title",title.getText().toString());
        resultIntent.putExtra("note",note.getText().toString());
        resultIntent.putExtra("color",noteColor);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}
