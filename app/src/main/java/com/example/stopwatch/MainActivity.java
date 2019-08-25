package com.example.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private int seconds=0;
    private boolean running=false;
    private boolean wasrunning=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasrunning = savedInstanceState.getBoolean("wasrunning");
        }
        runTime();
    }

    public void onClickStartWatch(View view){
        running = true;
    }

    public void onClickStopWatch(View view){
        running = false;
    }

    public void onClickResetWatch(View view){
        running = false;
        seconds=0;
    }

    public void runTime(){
        final TextView timeView = (TextView) findViewById(R.id.time);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int second=seconds%60;
                String time = String.format("%d:%02d:%02d",hours,minutes,second);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasrunning",wasrunning);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(running){
            wasrunning=true;
        }
        running=false;
    }

    @Override
    public void onStart(){
        super.onStart();
        if(wasrunning){
            running=true;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(running){
            wasrunning=true;
        }
        running=false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(wasrunning){
            running=wasrunning;
        }
    }
}
