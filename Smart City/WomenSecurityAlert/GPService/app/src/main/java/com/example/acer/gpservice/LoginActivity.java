package com.example.acer.gpservice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText uname,password;
    Button login,signup;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname=findViewById(R.id.uname);
        password=findViewById(R.id.paswrd);
        login=findViewById(R.id.btn_login);
        signup=findViewById(R.id.sbt2);
        db=openOrCreateDatabase("NaariDB", Context.MODE_PRIVATE,null);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,Signup.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        checkSession();

    }
    private void checkSession(){
        SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();
        if(userID != -1){
            //user logged in so move to mainactivity
            moveToMainActivity();
        }
        else{
            //do nothing
        }
    }
    public void login(View view){
        //1.login to app and save session of user
        //2 . Move to mainActivity
        String buname = uname.getText().toString().trim();
        String cpwd = password.getText().toString().trim();
        if (buname.length() == 0 || cpwd.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter username/password", Toast.LENGTH_LONG).show();
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM details WHERE usrname='" + buname + "'", null);
        if (c.moveToFirst()) {
            if (cpwd.equals(c.getString(2))) {
                Toast.makeText(getApplicationContext(), "User login successful", Toast.LENGTH_LONG).show();
                User user=new User(c.getInt(6),c.getString(1),c.getString(3),c.getString(4),c.getString(5));
                SessionManagement sessionManagement= new SessionManagement(LoginActivity.this);
                sessionManagement.saveSession(user);

                //move to mainActivity
                moveToMainActivity();
            }
            Toast.makeText(getApplicationContext(), "User name exists", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username/password.Try Again!!   "+buname, Toast.LENGTH_LONG).show();
        }

    }

    private void moveToMainActivity(){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
