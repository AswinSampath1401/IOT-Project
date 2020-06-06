package com.example.acer.gpservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;

public class RecordingService extends Service {

    MediaRecorder mediaRecorder;
    long mStratingTimeMillis=0;
    long mElapseMillis=0;
    File file;
    String fileName;
    DBHelper dbHelper;


    @Override
    public void onCreate() {
        Toast.makeText(this, "In on Create", Toast.LENGTH_LONG).show();
        super.onCreate();
        dbHelper= new DBHelper((getApplicationContext()));

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "In OnStartCommand", Toast.LENGTH_LONG).show();
        startRecording();
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    private void startRecording(){
        try{
        long taLong =System.currentTimeMillis()/1000;
        String ts = Long.toString(taLong);
        Toast.makeText(this, "In startRecording", Toast.LENGTH_LONG).show();

        fileName="audio_"+ts;
        file= new File(Environment.getExternalStorageDirectory()+"/MySoundRec/"+fileName+".mp3");

        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);
        Toast.makeText(getApplicationContext(),"Recording started",Toast.LENGTH_LONG).show();


            mediaRecorder.prepare();
            mediaRecorder.start();

            mStratingTimeMillis = System.currentTimeMillis();


        }catch(Exception e){
            String str=e.getMessage();

               Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
        }
    }
    private void StopRecording(){
        mediaRecorder.stop();
        mElapseMillis=(System.currentTimeMillis() - mStratingTimeMillis);
        mediaRecorder.release();
        Toast.makeText(getApplicationContext(),"Recording saved to "+file.getAbsolutePath(),Toast.LENGTH_LONG).show();

    // add to database
        RecordingItem recordingItem=new RecordingItem(fileName,file.getAbsolutePath(),mElapseMillis,System.currentTimeMillis());
        dbHelper.addRecording(recordingItem);



    }


    @Override
    public void onDestroy() {
        if(mediaRecorder!=null){
            StopRecording();
        }
        super.onDestroy();
    }
}

