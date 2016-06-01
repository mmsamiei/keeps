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
import android.widget.Toast;

/**
 * Created by Win2 on 5/24/2016.
 */
public class NoteActivity extends Activity {
    private TextView title,note;
    private ImageView color;
    private ImageView backArrow;
    int noteColor;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_note);
        root=findViewById(R.id.note_root_view);
        title = (TextView)findViewById(R.id.title_txt);
        note = (TextView) findViewById(R.id.note_txt);
        color = (ImageView) findViewById(R.id.pallete);
        backArrow = (ImageView) findViewById(R.id.back_arrow);
        noteColor= Color.WHITE;

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,ColorDialog.class);
                startActivityForResult(intent,2);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title",title.getText().toString());
                resultIntent.putExtra("note",note.getText().toString());
                resultIntent.putExtra("color",noteColor);
                if(title.getText().toString().equals("") && note.getText().toString().equals("") )
                    setResult(RESULT_CANCELED,resultIntent);
                else
                    setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                noteColor = data.getExtras().getInt("selectedColor");
            }
            root.setBackgroundColor(noteColor);
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
        if(title.getText().toString().equals("") && note.getText().toString().equals("") )
            setResult(RESULT_CANCELED,resultIntent);
        else
            setResult(RESULT_OK,resultIntent);
        finish();
    }
}
