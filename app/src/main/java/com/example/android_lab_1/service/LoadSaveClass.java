package com.example.android_lab_1.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android_lab_1.BuildConfig;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoadSaveClass extends IntentService implements Observable{

    public static final String TAG =
            LoadSaveClass.class.getSimpleName();
    public static final String ACTION_SAVE_HISTORY =
            BuildConfig.APPLICATION_ID + ".ACTION_SAVE_HISTORY";
    public static final String ACTION_LOAD_HISTORY =
            BuildConfig.APPLICATION_ID + ".ACTION_LOAD_HISTORY";

    private static final String TIME = "time";
    private static final String SPEED = "speed";
    private static final String SCORE = "score";
    final String SAVED_TEXT = "saved_text";

    private Observer observer;
    SharedPreferences sPref;

    public LoadSaveClass() {
        super("LoadSaveService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags,
                              int startId) {
        assert intent != null;
        Serializable observer=intent.getSerializableExtra("observer");
        registerObserver((Observer) observer);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;
        try {
            handleAction(action, intent);
        } catch (InterruptedException e) {
            Log.e(TAG, "Job has been interrupted");
        }
    }
    private void handleAction(String action, Intent intent)
            throws InterruptedException {
        switch (action) {
            case ACTION_SAVE_HISTORY:
                    saveHistory(intent);
                        Log.d(TAG, "Save History!");
                break;
            case ACTION_LOAD_HISTORY:
                        readFile();
                break;
            default:
                throw new RuntimeException("Unknown action!");
        }
    }


    protected void saveHistory(Intent intent){
        Bundle arg=intent.getExtras();
        String newHistory=createHistory(arg);
        writeFile(newHistory);

    }

    void writeFile(String history) {

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedHistory=sPref.getString(SAVED_TEXT,"");
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, savedHistory+history);
        if(ed.commit()){
            notifyObservers("Write in File");
        }

    }

    void readFile() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        notifyObservers(savedText);
    }

    private String createHistory(Bundle arg){
        @SuppressLint("SimpleDateFormat")
        String date = makeDate();
        int time=arg.getInt(TIME);
        String speed = arg.getString(SPEED);
        int score = arg.getInt(SCORE);

        return "\t"+date +"\n"+
                "Час: " + time +" с\n"+
                "Швидкість: " + speed +" с\n"+
                "Загальний рахунок: " + score +"\n"+"\n";
    }

    @SuppressLint("SimpleDateFormat")
    private String makeDate(){
        Date date = Calendar.getInstance().getTime();
        String Date=date.getDay()+" ";
        switch (date.getMonth()){
            case 1:
                Date+="січня";
                break;
            case 2:
                Date+="лютого";
                break;
            case 3:
                Date+="березня";
                break;
            case 4:
                Date+="квітня";
                break;
            case 5:
                Date+="травня";
                break;
            case 6:
                Date+="червня";
                break;
            case 7:
                Date+="липня";
                break;
            case 8:
                Date+="серпня";
                break;
            case 9:
                Date+="вересня";
                break;
            case 10:
                Date+="жовтня";
                break;
            case 11:
                Date+="листопада";
                break;
            case 12:
                Date+="грудня";
                break;
        }
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(" yyy");
        Date += dateFormat.format(Calendar.getInstance().getTime());
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date +="\n\t"+dateFormat.format(Calendar.getInstance().getTime());
        return Date;
    }

    @Override
    public void registerObserver(Observer o) {
        observer=o;
    }

    @Override
    public void removeObserver() {
        observer=null;
    }

    @Override
    public void notifyObservers(String message) {
        if(observer!=null){
            observer.update(message);
        }
    }



}
