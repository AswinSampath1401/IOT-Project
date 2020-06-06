package com.example.acer.gpservice;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";
    String PhoneNumber1="number1";
    String PhoneNumber2="number2";
    String PhoneNumber3="number3";

    public SessionManagement(Context context){
        sharedPreferences= context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();

            }
    public void saveSession(User user){
        //save session of user whenever s logged in
        int id= user.getId();
        String pN1=user.getpN1();
        String pN2=user.getpN2();
        String pN3=user.getpN3();


        editor.putInt(SESSION_KEY,id).commit();
        editor.putString(PhoneNumber1,pN1).commit();
        editor.putString(PhoneNumber2,pN2).commit();
        editor.putString(PhoneNumber3,pN3).commit();

    }

    public int getSession(){
        //return user whose session is saved

        return sharedPreferences.getInt(SESSION_KEY,-1);

    }
    public String getPhoneNumber1(){
        return sharedPreferences.getString(PhoneNumber1,"");
    }
    public String getPhoneNumber2(){
        return sharedPreferences.getString(PhoneNumber2,"");
    }
    public String getPhoneNumber3(){
        return sharedPreferences.getString(PhoneNumber3,"");
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        editor.putString(PhoneNumber1,"").commit();
        editor.putString(PhoneNumber2,"").commit();
        editor.putString(PhoneNumber3,"").commit();
    }
}
