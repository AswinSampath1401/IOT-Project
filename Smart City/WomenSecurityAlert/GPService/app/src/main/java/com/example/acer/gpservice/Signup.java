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

public class Signup extends AppCompatActivity {

    EditText fname,uname,password,cpassword,phone,ephone1,ephone2;
    Button login;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname = findViewById(R.id.name);
        uname = findViewById(R.id.usname);
        password = findViewById(R.id.pwd);
        cpassword = findViewById(R.id.cpwd);
        phone = findViewById(R.id.phone);
        ephone1 = findViewById(R.id.ep1);
        ephone2 = findViewById(R.id.ep2);
        login = findViewById(R.id.lb);
        db = openOrCreateDatabase("NaariDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS details( name VARCHAR,usrname VARCHAR,password VARCHAR,phone VARCHAR,Ephone1 VARCHAR,Ephone2 VARCHAR,userID INTEGER);");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aname = fname.getText().toString().trim();
                String buname = uname.getText().toString().trim();
                String cpwd = password.getText().toString().trim();
                String ccpwd = cpassword.getText().toString().trim();
                String ph = phone.getText().toString().trim();
                String e1ph = ephone1.getText().toString().trim();
                String e2ph = ephone2.getText().toString().trim();
                String regex = "^[0-9]*$";
                if (aname.length() == 0 || buname.length() == 0 || cpwd.length() == 0 || ccpwd.length() == 0 || ph.length() == 0 || e1ph.length() == 0 || e2ph.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all details.", Toast.LENGTH_LONG).show();
                    return;
                } else if (!ccpwd.equals(cpwd)) {
                    Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_LONG).show();
                    return;
                } else if ((!ph.matches(regex) && ph.length() != 10) || (!e1ph.matches(regex) && e1ph.length() != 10) || (!e2ph.matches(regex) && e2ph.length() != 10) ||
                        (ph.matches(regex) && ph.length() != 10) || (e1ph.matches(regex) && e1ph.length() != 10) || (e2ph.matches(regex) && e2ph.length() != 10)) {
                    Toast.makeText(getApplicationContext(), "Number format is wrong", Toast.LENGTH_LONG).show();
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM details WHERE usrname='" + buname + "'", null);
                if (c.moveToFirst()) {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                } else {
                    db.execSQL("INSERT INTO details VALUES('" + aname + "','" + buname + "','" + cpwd + "','" + ph + "','" + e1ph + "','" + e2ph + "',1);");
                    Toast.makeText(getApplicationContext(), "User Inserted", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Signup.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
