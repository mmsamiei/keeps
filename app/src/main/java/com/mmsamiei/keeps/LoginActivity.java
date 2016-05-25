package com.mmsamiei.keeps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Win2 on 5/24/2016.
 */
public class LoginActivity extends Activity {
    Button btn_login;
    EditText username_edt,password_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        username_edt= (EditText) findViewById(R.id.username_edit_text);
        password_edt=(EditText)findViewById(R.id.password_edit_text);
        btn_login = (Button) findViewById(R.id.login_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username_edt.getText().toString().equalsIgnoreCase("admin") && password_edt.getText().toString().equalsIgnoreCase("1376")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("USER_NAME",username_edt.getText().toString());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"ورود با موفقیت",Toast.LENGTH_LONG);
                }
                else {

                }
            }
        });
    }
}
