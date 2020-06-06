package com.example.acer.gpservice;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private Button btn_start, btn_stop;
    private TextView textview;
    private BroadcastReceiver broadcastReceiver;
    private String phoneNo,phoneNo1,phoneNo2;
    private EditText phone;
    private SessionManagement sessionManagement;


    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String message="Help! I am in danger! My location is \nhttp://maps.google.com/?q=";
                    SmsManager smsManager = SmsManager.getDefault();
                    String str = (String) intent.getExtras().get("coordinates");
                   // String str1 =(String) intent.getExtras().get("longitude");
                    SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
                    phoneNo=sessionManagement.getPhoneNumber1();
                    phoneNo1=sessionManagement.getPhoneNumber2();
                    phoneNo2=sessionManagement.getPhoneNumber3();
                    //Toast.makeText(getApplicationContext(), str,   Toast.LENGTH_SHORT).show();
                    if(phoneNo!="") {
                        smsManager.sendTextMessage(phoneNo, null, message + str, null, null);
                    }
                    if(phoneNo!="") {
                        smsManager.sendTextMessage(phoneNo1, null, message + str, null, null);
                    }
                    if(phoneNo!="") {
                        smsManager.sendTextMessage(phoneNo2, null, message + str, null, null);
                    }
                   // Toast.makeText(getApplicationContext(), "SMS sent.",
                     //       Toast.LENGTH_LONG).show();
                    textview.append("\n" + str);

                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.StartService);
        btn_stop = findViewById(R.id.StopService);
        textview = findViewById(R.id.coordinate);
        phone=findViewById(R.id.phoneNo);


        if (!runtime_permissions()) {

            enable_buttons();
        }

    }

    private void enable_buttons() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement =new SessionManagement(MainActivity.this);
                phoneNo= sessionManagement.getPhoneNumber1();
                if( phoneNo.length()>0) {
                    Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
                    startService(i);
                    Intent j= new Intent(getApplicationContext(),RecordingService.class);
                    File folder=new File(Environment.getExternalStorageDirectory()+"/MySoundRec");
                    if(!folder.exists()){
                        //Toast.makeText(MainActivity.this, "In make folder.", Toast.LENGTH_LONG).show();
                        folder.mkdir();
                    }
                    startService(j);
                //Toast.makeText(getApplicationContext(), "Making Phone call", Toast.LENGTH_LONG).show();
                    makePhoneCall();
              }
               else{
                    Toast.makeText(getApplicationContext(), "Enter Phone Number.",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
                stopService(i);
                Intent j= new Intent(getApplicationContext(),RecordingService.class);
                stopService(j);
                String message="I am safe now.";
                SmsManager smsManager = SmsManager.getDefault();
                SessionManagement sessionManagement= new SessionManagement(MainActivity.this);
                phoneNo=sessionManagement.getPhoneNumber1();
                phoneNo1=sessionManagement.getPhoneNumber2();
                phoneNo2=sessionManagement.getPhoneNumber3();
                        // phone.getText().toString().trim();
                //Toast.makeText(getApplicationContext(), str,   Toast.LENGTH_SHORT).show();
                if(phoneNo!="") {
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                }
                if(phoneNo1!="") {
                    smsManager.sendTextMessage(phoneNo1, null, message, null, null);
                }
                if(phoneNo2!="") {
                    smsManager.sendTextMessage(phoneNo2, null, message, null, null);
                }
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();

            }
        });
    }

      //@RequiresApi(api = Build.VERSION_CODES.M)
      private boolean runtime_permissions(){
          if((Build.VERSION.SDK_INT >= 23) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED)
                  && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
          &&(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
          &&(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                  &&(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                  &&(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                  &&(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                      requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                      return true;

          }
         return false;
      }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            Toast.makeText(getApplicationContext(), "permissions granting",   Toast.LENGTH_LONG).show();
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[2]==PackageManager.PERMISSION_GRANTED && grantResults[3]==PackageManager.PERMISSION_GRANTED && grantResults[4]==PackageManager.PERMISSION_GRANTED && grantResults[5]==PackageManager.PERMISSION_GRANTED && grantResults[6]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "All permissions granted",   Toast.LENGTH_LONG).show();
                enable_buttons();
                return;

            } else {
                   Toast.makeText(getApplicationContext(),"All permissions failed, please try again.", Toast.LENGTH_LONG).show();
                boolean flag = runtime_permissions();
                return;
            }
        }
        String str=" "+requestCode;
        Toast.makeText(getApplicationContext(), str,   Toast.LENGTH_LONG).show();
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
    private void makePhoneCall () {
        SessionManagement sessionManagement= new SessionManagement(MainActivity.this);
        String number = sessionManagement.getPhoneNumber1();
        //phone.getText().toString().trim();
        if (number.trim().length() > 0) {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }


    public void logOut(View view){
        //this method will remove session and open login screen
        SessionManagement sessionManagement= new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        moveToLogin();

    }
    private void moveToLogin(){
        Intent intent= new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


